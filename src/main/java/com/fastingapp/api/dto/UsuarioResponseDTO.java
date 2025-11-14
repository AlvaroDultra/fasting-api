package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private ProtocoloJejum protocoloPadrao;
    private Integer nivelUsuario;
    private Integer xpTotal;
    private LocalDateTime criadoEm;
    private Boolean ativo;
}