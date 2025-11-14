package com.fastingapp.api.controller;

import com.fastingapp.api.dto.NotificacaoResponseDTO;
import com.fastingapp.api.model.enums.TipoNotificacao;
import com.fastingapp.api.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<NotificacaoResponseDTO> criar(
            @PathVariable Long usuarioId,
            @RequestParam TipoNotificacao tipo,
            @RequestParam String mensagem,
            @RequestParam LocalDateTime dataDisparo) {
        NotificacaoResponseDTO criada = notificacaoService.criar(usuarioId, tipo, mensagem, dataDisparo);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNotificacoesDoUsuario(@PathVariable Long usuarioId) {
        List<NotificacaoResponseDTO> notificacoes = notificacaoService.listarNotificacoesDoUsuario(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNotificacoesNaoLidas(@PathVariable Long usuarioId) {
        List<NotificacaoResponseDTO> notificacoes = notificacaoService.listarNotificacoesNaoLidas(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @PutMapping("/{notificacaoId}/marcar-lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable Long notificacaoId) {
        notificacaoService.marcarComoLida(notificacaoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/usuario/{usuarioId}/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(@PathVariable Long usuarioId) {
        notificacaoService.marcarTodasComoLidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/processar-pendentes")
    public ResponseEntity<Void> processarNotificacoesPendentes() {
        notificacaoService.processarNotificacoesPendentes();
        return ResponseEntity.ok().build();
    }
}