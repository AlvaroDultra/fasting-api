package com.fastingapp.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_dicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoDica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dica_id", nullable = false)
    private Dica dica;

    @CreationTimestamp
    @Column(name = "enviada_em", updatable = false)
    private LocalDateTime enviadaEm;

    @Column(name = "reacao_usuario", length = 20)
    private String reacaoUsuario; // UTIL, NAO_UTIL, SEM_REACAO

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;
}