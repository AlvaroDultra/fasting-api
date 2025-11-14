package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import com.fastingapp.api.model.enums.StatusJejum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JejumResponseDTO {

    private Long id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private StatusJejum status;
    private Double duracaoHoras;
    private Integer metaHoras;
    private ProtocoloJejum protocolo;
    private Boolean metaAtingida;
    private String observacoes;

    // Campos calculados para jejum ativo
    private Long tempoDecorridoMinutos;
    private Long tempoRestanteMinutos;
    private Double porcentagemConcluida;
    private LocalDateTime previsaoTermino;
}