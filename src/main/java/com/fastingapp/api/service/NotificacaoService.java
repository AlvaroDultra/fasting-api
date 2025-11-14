package com.fastingapp.api.service;

import com.fastingapp.api.dto.NotificacaoResponseDTO;
import com.fastingapp.api.model.entity.Jejum;
import com.fastingapp.api.model.entity.Notificacao;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.model.enums.TipoNotificacao;
import com.fastingapp.api.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public NotificacaoResponseDTO criar(Long usuarioId, TipoNotificacao tipo, String mensagem, LocalDateTime dataDisparo) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setTipo(tipo);
        notificacao.setMensagem(mensagem);
        notificacao.setDataDisparo(dataDisparo);

        Notificacao salva = notificacaoRepository.save(notificacao);
        return converterParaDTO(salva);
    }

    @Transactional
    public void criarNotificacoesJejum(Jejum jejum) {
        Usuario usuario = jejum.getUsuario();
        LocalDateTime inicio = jejum.getInicio();
        Integer metaHoras = jejum.getMetaHoras();

        if (metaHoras == null) return;

        // Notificação: Metade do jejum
        LocalDateTime metade = inicio.plusHours(metaHoras / 2);
        criar(usuario.getId(), TipoNotificacao.METADE_CONCLUIDA,
                "Você já completou metade do seu jejum! Continue firme! 💪",
                metade);

        // Notificação: Falta 1 hora
        LocalDateTime faltaUmaHora = inicio.plusHours(metaHoras - 1);
        criar(usuario.getId(), TipoNotificacao.UMA_HORA_RESTANTE,
                "Falta apenas 1 hora para completar sua meta! 🎯",
                faltaUmaHora);

        // Notificação: Meta alcançada
        LocalDateTime metaAlcancada = inicio.plusHours(metaHoras);
        criar(usuario.getId(), TipoNotificacao.META_ALCANCADA,
                "Parabéns! Você alcançou sua meta de jejum! 🎉",
                metaAlcancada);
    }

    public List<NotificacaoResponseDTO> listarNotificacoesDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return notificacaoRepository.findByUsuarioOrderByDataDisparoDesc(usuario).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<NotificacaoResponseDTO> listarNotificacoesNaoLidas(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return notificacaoRepository.findByUsuarioAndLidaFalseOrderByDataDisparoDesc(usuario).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void marcarComoLida(Long notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }

    @Transactional
    public void marcarTodasComoLidas(Long usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        List<Notificacao> naoLidas = notificacaoRepository.findByUsuarioAndLidaFalseOrderByDataDisparoDesc(usuario);

        naoLidas.forEach(n -> n.setLida(true));
        notificacaoRepository.saveAll(naoLidas);
    }

    @Transactional
    public void processarNotificacoesPendentes() {
        LocalDateTime agora = LocalDateTime.now();
        List<Notificacao> pendentes = notificacaoRepository.findNotificacoesPendentes(agora);

        for (Notificacao notificacao : pendentes) {
            try {
                enviarNotificacao(notificacao);
                notificacao.setEnviada(true);
                notificacaoRepository.save(notificacao);
            } catch (Exception e) {
                log.error("Erro ao enviar notificação {}: {}", notificacao.getId(), e.getMessage());
            }
        }
    }

    private void enviarNotificacao(Notificacao notificacao) {
        // TODO: Implementar envio real (email, push, webhook)
        log.info("Enviando notificação para {}: {}",
                notificacao.getUsuario().getEmail(),
                notificacao.getMensagem());
    }

    private NotificacaoResponseDTO converterParaDTO(Notificacao notificacao) {
        NotificacaoResponseDTO dto = new NotificacaoResponseDTO();
        dto.setId(notificacao.getId());
        dto.setTipo(notificacao.getTipo());
        dto.setMensagem(notificacao.getMensagem());
        dto.setDataDisparo(notificacao.getDataDisparo());
        dto.setEnviada(notificacao.getEnviada());
        dto.setLida(notificacao.getLida());
        dto.setCanal(notificacao.getCanal());
        return dto;
    }
}