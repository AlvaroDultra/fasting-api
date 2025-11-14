package com.fastingapp.api.service;

import com.fastingapp.api.dto.JejumRequestDTO;
import com.fastingapp.api.dto.JejumResponseDTO;
import com.fastingapp.api.model.entity.Jejum;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.model.enums.StatusJejum;
import com.fastingapp.api.repository.JejumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JejumService {

    private final JejumRepository jejumRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public JejumResponseDTO iniciarJejum(Long usuarioId, JejumRequestDTO dto) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);

        // Verificar se já existe jejum ativo
        jejumRepository.findJejumAtivoByUsuario(usuario).ifPresent(j -> {
            throw new RuntimeException("Já existe um jejum ativo. Finalize-o antes de iniciar outro.");
        });

        Jejum jejum = new Jejum();
        jejum.setUsuario(usuario);
        jejum.setInicio(dto.getInicio() != null ? dto.getInicio() : LocalDateTime.now());
        jejum.setStatus(StatusJejum.ATIVO);
        jejum.setMetaHoras(dto.getMetaHoras());
        jejum.setProtocolo(dto.getProtocolo() != null ? dto.getProtocolo() : usuario.getProtocoloPadrao());
        jejum.setObservacoes(dto.getObservacoes());

        if (jejum.getMetaHoras() == null && jejum.getProtocolo() != null) {
            jejum.setMetaHoras(jejum.getProtocolo().getHorasMinimas());
        }

        Jejum salvo = jejumRepository.save(jejum);
        return converterParaDTO(salvo);
    }

    @Transactional
    public JejumResponseDTO finalizarJejum(Long jejumId) {
        Jejum jejum = jejumRepository.findById(jejumId)
                .orElseThrow(() -> new RuntimeException("Jejum não encontrado"));

        if (jejum.getStatus() != StatusJejum.ATIVO) {
            throw new RuntimeException("Jejum não está ativo");
        }

        jejum.setFim(LocalDateTime.now());
        jejum.setStatus(StatusJejum.CONCLUIDO);

        // Calcular duração manualmente
        Duration duration = Duration.between(jejum.getInicio(), jejum.getFim());
        jejum.setDuracaoHoras(duration.toMinutes() / 60.0);

        if (jejum.getMetaHoras() != null) {
            jejum.setMetaAtingida(jejum.getDuracaoHoras() >= jejum.getMetaHoras());
        }

        Jejum atualizado = jejumRepository.save(jejum);

        // Adicionar XP ao usuário
        int xpGanho = calcularXP(atualizado);
        usuarioService.adicionarXP(atualizado.getUsuario().getId(), xpGanho);

        return converterParaDTO(atualizado);
    }

    @Transactional
    public void cancelarJejum(Long jejumId) {
        Jejum jejum = jejumRepository.findById(jejumId)
                .orElseThrow(() -> new RuntimeException("Jejum não encontrado"));

        jejum.setStatus(StatusJejum.CANCELADO);
        jejumRepository.save(jejum);
    }

    public JejumResponseDTO buscarJejumAtivo(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        Jejum jejum = jejumRepository.findJejumAtivoByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Nenhum jejum ativo encontrado"));

        return converterParaDTOComCalculos(jejum);
    }

    public List<JejumResponseDTO> listarJejunsDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return jejumRepository.findByUsuarioOrderByInicioDesc(usuario).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<JejumResponseDTO> listarJejunsPorPeriodo(Long usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return jejumRepository.findByUsuarioAndPeriodo(usuario, inicio, fim).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private int calcularXP(Jejum jejum) {
        int xp = 10; // XP base

        if (jejum.getMetaAtingida()) {
            xp += 20;
        }

        if (jejum.getDuracaoHoras() != null && jejum.getDuracaoHoras() >= 18) {
            xp += 15; // Jejum longo
        }

        return xp;
    }

    private JejumResponseDTO converterParaDTO(Jejum jejum) {
        JejumResponseDTO dto = new JejumResponseDTO();
        dto.setId(jejum.getId());
        dto.setInicio(jejum.getInicio());
        dto.setFim(jejum.getFim());
        dto.setStatus(jejum.getStatus());
        dto.setDuracaoHoras(jejum.getDuracaoHoras());
        dto.setMetaHoras(jejum.getMetaHoras());
        dto.setProtocolo(jejum.getProtocolo());
        dto.setMetaAtingida(jejum.getMetaAtingida());
        dto.setObservacoes(jejum.getObservacoes());
        return dto;
    }

    private JejumResponseDTO converterParaDTOComCalculos(Jejum jejum) {
        JejumResponseDTO dto = converterParaDTO(jejum);

        if (jejum.getStatus() == StatusJejum.ATIVO) {
            LocalDateTime agora = LocalDateTime.now();
            Duration tempoDecorrido = Duration.between(jejum.getInicio(), agora);

            dto.setTempoDecorridoMinutos(tempoDecorrido.toMinutes());

            if (jejum.getMetaHoras() != null) {
                long metaMinutos = jejum.getMetaHoras() * 60L;
                long restante = metaMinutos - tempoDecorrido.toMinutes();
                dto.setTempoRestanteMinutos(Math.max(0, restante));

                double porcentagem = (tempoDecorrido.toMinutes() * 100.0) / metaMinutos;
                dto.setPorcentagemConcluida(Math.min(100.0, porcentagem));

                dto.setPrevisaoTermino(jejum.getInicio().plusHours(jejum.getMetaHoras()));
            }
        }

        return dto;
    }
}