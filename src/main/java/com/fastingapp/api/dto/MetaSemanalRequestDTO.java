package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaSemanalRequestDTO {

    @NotNull(message = "Data de início da semana é obrigatória")
    private LocalDate semanaInicio;

    @NotNull(message = "Data de fim da semana é obrigatória")
    private LocalDate semanaFim;

    @Min(value = 1, message = "Meta de dias deve ser no mínimo 1")
    private Integer metaDias;

    @Min(value = 1, message = "Meta de horas totais deve ser no mínimo 1")
    private Integer metaHorasTotal;

    private ProtocoloJejum protocoloAlvo;
}