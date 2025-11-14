package com.fastingapp.api.service;

import com.fastingapp.api.dto.MetaSemanalRequestDTO;
import com.fastingapp.api.dto.MetaSemanalResponseDTO;
import com.fastingapp.api.model.entity.MetaSemanal;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.repository.JejumRepository;
import com.fastingapp.api.repository.MetaSemanalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaSemanalService {

    private final MetaSemanalRepository metaSemanalRepository;
    private final JejumRepository jejumRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public MetaSemanalResponseDTO criar(Long usuarioId, MetaSemanalRequestDTO dto) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);

        MetaSemanal meta = new MetaSemanal();
        meta.setUsuario(usuario);
        meta.setSemanaInicio(dto.getSemanaInicio());
        meta.setSemanaFim(dto.getSemanaFim());
        meta.setMetaDias(dto.getMetaDias());
        meta.setMetaHorasTotal(dto.getMetaHorasTotal());
        meta.setProtocoloAlvo(dto.getProtocoloAlvo());

        MetaSemanal salva = metaSemanalRepository.save(meta);
        return converterParaDTO(salva);
    }

    @Transactional
    public MetaSemanalResponseDTO atualizarProgresso(Long metaId) {
        MetaSemanal meta = metaSemanalRepository.findById(metaId)
                .orElseThrow(() -> new RuntimeException("Meta semanal não encontrada"));

        LocalDateTime inicioSemana = meta.getSemanaInicio().atStartOfDay();
        LocalDateTime fimSemana = meta.getSemanaFim().atTime(23, 59, 59);

        // Contar jejuns concluídos
        Long jejunsConcluidos = jejumRepository.countJejunsConcluidos(
                meta.getUsuario(), inicioSemana);

        // Calcular horas totais
        Double horasTotais = jejumRepository.calcularMediaHoras(
                meta.getUsuario(), inicioSemana);

        meta.setJejunsConcluidos(jejunsConcluidos.intValue());
        meta.setHorasTotais(horasTotais != null ? horasTotais : 0.0);

        // Verificar se a meta foi cumprida
        boolean metaCumprida = meta.getJejunsConcluidos() >= meta.getMetaDias() &&
                meta.getHorasTotais() >= meta.getMetaHorasTotal();
        meta.setMetaCumprida(metaCumprida);

        // Atribuir selo
        meta.setSeloSemana(calcularSelo(meta));

        MetaSemanal atualizada = metaSemanalRepository.save(meta);
        return converterParaDTO(atualizada);
    }

    public MetaSemanalResponseDTO buscarMetaSemanaAtual(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        LocalDate hoje = LocalDate.now();

        MetaSemanal meta = metaSemanalRepository.findBySemanaAtual(usuario, hoje)
                .orElseGet(() -> criarMetaAutomatica(usuario, hoje));

        return converterParaDTO(meta);
    }

    public List<MetaSemanalResponseDTO> listarMetasDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return metaSemanalRepository.findByUsuarioOrderBySemanaInicioDesc(usuario).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<MetaSemanalResponseDTO> listarMetasRecentes(Long usuarioId, int semanas) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        LocalDate dataLimite = LocalDate.now().minusWeeks(semanas);

        return metaSemanalRepository.findMetasRecentes(usuario, dataLimite).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private MetaSemanal criarMetaAutomatica(Usuario usuario, LocalDate referencia) {
        // Criar meta automática para a semana atual
        LocalDate inicioSemana = referencia.with(java.time.DayOfWeek.MONDAY);
        LocalDate fimSemana = inicioSemana.plusDays(6);

        MetaSemanal meta = new MetaSemanal();
        meta.setUsuario(usuario);
        meta.setSemanaInicio(inicioSemana);
        meta.setSemanaFim(fimSemana);
        meta.setMetaDias(5);
        meta.setMetaHorasTotal(80);
        meta.setProtocoloAlvo(usuario.getProtocoloPadrao());

        return metaSemanalRepository.save(meta);
    }

    private String calcularSelo(MetaSemanal meta) {
        double porcentagem = (meta.getJejunsConcluidos() * 100.0) / meta.getMetaDias();

        if (porcentagem >= 100) return "🥇 Semana Perfeita";
        if (porcentagem >= 70) return "🥈 Semana Boa";
        if (porcentagem >= 40) return "🥉 Semana de Recomeço";
        if (meta.getHorasTotais() >= 24) return "🔥 Semana Longa";
        return "📊 Em Progresso";
    }

    private MetaSemanalResponseDTO converterParaDTO(MetaSemanal meta) {
        MetaSemanalResponseDTO dto = new MetaSemanalResponseDTO();
        dto.setId(meta.getId());
        dto.setSemanaInicio(meta.getSemanaInicio());
        dto.setSemanaFim(meta.getSemanaFim());
        dto.setMetaDias(meta.getMetaDias());
        dto.setMetaHorasTotal(meta.getMetaHorasTotal());
        dto.setProtocoloAlvo(meta.getProtocoloAlvo());
        dto.setJejunsConcluidos(meta.getJejunsConcluidos());
        dto.setHorasTotais(meta.getHorasTotais());
        dto.setMetaCumprida(meta.getMetaCumprida());
        dto.setSeloSemana(meta.getSeloSemana());

        // Calcular porcentagem
        if (meta.getMetaDias() > 0) {
            double porcentagem = (meta.getJejunsConcluidos() * 100.0) / meta.getMetaDias();
            dto.setPorcentagemConclusao(Math.min(100.0, porcentagem));
        }

        return dto;
    }
}