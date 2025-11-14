package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JejumRequestDTO {

    @NotNull(message = "Data e hora de início são obrigatórias")
    private LocalDateTime inicio;

    private LocalDateTime fim;

    private Integer metaHoras;

    private ProtocoloJejum protocolo;

    private String observacoes;
}