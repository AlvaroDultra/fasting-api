package com.fastingapp.api.dto;

import com.fastingapp.api.model.enums.TipoNotificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoResponseDTO {

    private Long id;
    private TipoNotificacao tipo;
    private String mensagem;
    private LocalDateTime dataDisparo;
    private Boolean enviada;
    private Boolean lida;
    private String canal;
}