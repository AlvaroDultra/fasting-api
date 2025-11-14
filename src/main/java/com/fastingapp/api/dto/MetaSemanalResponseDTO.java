package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaSemanalResponseDTO {

    private Long id;
    private LocalDate semanaInicio;
    private LocalDate semanaFim;
    private Integer metaDias;
    private Integer metaHorasTotal;
    private ProtocoloJejum protocoloAlvo;
    private Integer jejunsConcluidos;
    private Double horasTotais;
    private Boolean metaCumprida;
    private String seloSemana;
    private Double porcentagemConclusao;
}