package com.fastingapp.api.controller;

import com.fastingapp.api.dto.MetaSemanalRequestDTO;
import com.fastingapp.api.dto.MetaSemanalResponseDTO;
import com.fastingapp.api.service.MetaSemanalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas-semanais")
@RequiredArgsConstructor
public class MetaSemanalController {

    private final MetaSemanalService metaSemanalService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<MetaSemanalResponseDTO> criar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody MetaSemanalRequestDTO dto) {
        MetaSemanalResponseDTO criada = metaSemanalService.criar(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{metaId}/atualizar-progresso")
    public ResponseEntity<MetaSemanalResponseDTO> atualizarProgresso(@PathVariable Long metaId) {
        MetaSemanalResponseDTO atualizada = metaSemanalService.atualizarProgresso(metaId);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping("/usuario/{usuarioId}/atual")
    public ResponseEntity<MetaSemanalResponseDTO> buscarMetaSemanaAtual(@PathVariable Long usuarioId) {
        MetaSemanalResponseDTO meta = metaSemanalService.buscarMetaSemanaAtual(usuarioId);
        return ResponseEntity.ok(meta);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MetaSemanalResponseDTO>> listarMetasDoUsuario(@PathVariable Long usuarioId) {
        List<MetaSemanalResponseDTO> metas = metaSemanalService.listarMetasDoUsuario(usuarioId);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/usuario/{usuarioId}/recentes")
    public ResponseEntity<List<MetaSemanalResponseDTO>> listarMetasRecentes(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "4") int semanas) {
        List<MetaSemanalResponseDTO> metas = metaSemanalService.listarMetasRecentes(usuarioId, semanas);
        return ResponseEntity.ok(metas);
    }
}