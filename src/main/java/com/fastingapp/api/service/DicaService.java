package com.fastingapp.api.service;

import com.fastingapp.api.dto.DicaResponseDTO;
import com.fastingapp.api.model.entity.Dica;
import com.fastingapp.api.model.entity.HistoricoDica;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.model.enums.CategoriaDica;
import com.fastingapp.api.repository.DicaRepository;
import com.fastingapp.api.repository.HistoricoDicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DicaService {

    private final DicaRepository dicaRepository;
    private final HistoricoDicaRepository historicoDicaRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public DicaResponseDTO criarDica(String texto, CategoriaDica categoria) {
        Dica dica = new Dica();
        dica.setTexto(texto);
        dica.setCategoria(categoria);

        Dica salva = dicaRepository.save(dica);
        return converterParaDTO(salva);
    }

    public DicaResponseDTO obterDicaAleatoria(Long usuarioId, CategoriaDica categoria) {
        Dica dica = dicaRepository.findDicaAleatoriaPorCategoria(categoria);

        if (dica != null) {
            registrarEnvioDica(usuarioId, dica.getId());
            return converterParaDTO(dica);
        }

        throw new RuntimeException("Nenhuma dica disponível para a categoria: " + categoria);
    }

    public List<DicaResponseDTO> listarDicasPorCategoria(CategoriaDica categoria) {
        return dicaRepository.findByCategoria(categoria).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<DicaResponseDTO> listarTodasDicas() {
        return dicaRepository.findByAtivaTrue().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void registrarEnvioDica(Long usuarioId, Long dicaId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        Dica dica = dicaRepository.findById(dicaId)
                .orElseThrow(() -> new RuntimeException("Dica não encontrada"));

        HistoricoDica historico = new HistoricoDica();
        historico.setUsuario(usuario);
        historico.setDica(dica);
        historico.setReacaoUsuario("SEM_REACAO");

        historicoDicaRepository.save(historico);
    }

    @Transactional
    public void registrarReacao(Long historicoId, String reacao, String feedback) {
        HistoricoDica historico = historicoDicaRepository.findById(historicoId)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado"));

        historico.setReacaoUsuario(reacao);
        historico.setFeedback(feedback);

        historicoDicaRepository.save(historico);
    }

    private DicaResponseDTO converterParaDTO(Dica dica) {
        DicaResponseDTO dto = new DicaResponseDTO();
        dto.setId(dica.getId());
        dto.setTexto(dica.getTexto());
        dto.setCategoria(dica.getCategoria());
        return dto;
    }
}