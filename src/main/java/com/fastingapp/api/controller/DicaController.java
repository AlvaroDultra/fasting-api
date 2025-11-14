package com.fastingapp.api.controller;

import com.fastingapp.api.dto.DicaResponseDTO;
import com.fastingapp.api.model.enums.CategoriaDica;
import com.fastingapp.api.service.DicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dicas")
@RequiredArgsConstructor
public class DicaController {

    private final DicaService dicaService;

    @PostMapping
    public ResponseEntity<DicaResponseDTO> criarDica(
            @RequestParam String texto,
            @RequestParam CategoriaDica categoria) {
        DicaResponseDTO criada = dicaService.criarDica(texto, categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/aleatoria/usuario/{usuarioId}")
    public ResponseEntity<DicaResponseDTO> obterDicaAleatoria(
            @PathVariable Long usuarioId,
            @RequestParam CategoriaDica categoria) {
        DicaResponseDTO dica = dicaService.obterDicaAleatoria(usuarioId, categoria);
        return ResponseEntity.ok(dica);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<DicaResponseDTO>> listarDicasPorCategoria(@PathVariable CategoriaDica categoria) {
        List<DicaResponseDTO> dicas = dicaService.listarDicasPorCategoria(categoria);
        return ResponseEntity.ok(dicas);
    }

    @GetMapping
    public ResponseEntity<List<DicaResponseDTO>> listarTodasDicas() {
        List<DicaResponseDTO> dicas = dicaService.listarTodasDicas();
        return ResponseEntity.ok(dicas);
    }

    @PostMapping("/historico/{historicoId}/reacao")
    public ResponseEntity<Void> registrarReacao(
            @PathVariable Long historicoId,
            @RequestParam String reacao,
            @RequestParam(required = false) String feedback) {
        dicaService.registrarReacao(historicoId, reacao, feedback);
        return ResponseEntity.ok().build();
    }
}