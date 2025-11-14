package com.fastingapp.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarioAlimentarRequestDTO {

    @NotNull(message = "ID do jejum é obrigatório")
    private Long jejumId;

    private String ultimaRefeicao;

    private String primeiraRefeicao;

    private String comoSeSentiu;

    @Min(value = 1, message = "Nível de energia deve ser entre 1 e 5")
    @Max(value = 5, message = "Nível de energia deve ser entre 1 e 5")
    private Integer nivelEnergia;

    @Min(value = 1, message = "Nível de fome deve ser entre 1 e 5")
    @Max(value = 5, message = "Nível de fome deve ser entre 1 e 5")
    private Integer nivelFome;
}