package com.fastingapp.api.model.entity;

import com.fastingapp.api.model.enums.TipoNotificacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_disparo", nullable = false)
    private LocalDateTime dataDisparo;

    @Column(name = "enviada")
    private Boolean enviada = false;

    @Column(name = "canal", length = 20)
    private String canal = "EMAIL"; // EMAIL, PUSH, WEBHOOK

    @Column(name = "lida")
    private Boolean lida = false;

    @CreationTimestamp
    @Column(name = "criada_em", updatable = false)
    private LocalDateTime criadaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jejum_id")
    private Jejum jejum;
}