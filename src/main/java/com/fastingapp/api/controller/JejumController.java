package com.fastingapp.api.controller;

import com.fastingapp.api.dto.JejumRequestDTO;
import com.fastingapp.api.dto.JejumResponseDTO;
import com.fastingapp.api.service.JejumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/jejuns")
@RequiredArgsConstructor
public class JejumController {

    private final JejumService jejumService;

    @PostMapping("/iniciar/{usuarioId}")
    public ResponseEntity<JejumResponseDTO> iniciarJejum(
            @PathVariable Long usuarioId,
            @Valid @RequestBody JejumRequestDTO dto) {
        JejumResponseDTO iniciado = jejumService.iniciarJejum(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(iniciado);
    }

    @PutMapping("/finalizar/{jejumId}")
    public ResponseEntity<JejumResponseDTO> finalizarJejum(@PathVariable Long jejumId) {
        JejumResponseDTO finalizado = jejumService.finalizarJejum(jejumId);
        return ResponseEntity.ok(finalizado);
    }

    @PutMapping("/cancelar/{jejumId}")
    public ResponseEntity<Void> cancelarJejum(@PathVariable Long jejumId) {
        jejumService.cancelarJejum(jejumId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ativo/{usuarioId}")
    public ResponseEntity<JejumResponseDTO> buscarJejumAtivo(@PathVariable Long usuarioId) {
        JejumResponseDTO ativo = jejumService.buscarJejumAtivo(usuarioId);
        return ResponseEntity.ok(ativo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<JejumResponseDTO>> listarJejunsDoUsuario(@PathVariable Long usuarioId) {
        List<JejumResponseDTO> jejuns = jejumService.listarJejunsDoUsuario(usuarioId);
        return ResponseEntity.ok(jejuns);
    }

    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<JejumResponseDTO>> listarJejunsPorPeriodo(
            @PathVariable Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<JejumResponseDTO> jejuns = jejumService.listarJejunsPorPeriodo(usuarioId, inicio, fim);
        return ResponseEntity.ok(jejuns);
    }
}