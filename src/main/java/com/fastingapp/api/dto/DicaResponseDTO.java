package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.CategoriaDica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicaResponseDTO {

    private Long id;
    private String texto;
    private CategoriaDica categoria;
}