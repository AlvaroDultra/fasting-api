package com.fastingapp.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "diario_alimentar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarioAlimentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jejum_id", nullable = false)
    private Jejum jejum;

    @Column(name = "ultima_refeicao", columnDefinition = "TEXT")
    private String ultimaRefeicao;

    @Column(name = "primeira_refeicao", columnDefinition = "TEXT")
    private String primeiraRefeicao;

    @Column(name = "como_se_sentiu", columnDefinition = "TEXT")
    private String comoSeSentiu;

    @Column(name = "nivel_energia")
    private Integer nivelEnergia; // 1 a 5

    @Column(name = "nivel_fome")
    private Integer nivelFome; // 1 a 5

    @CreationTimestamp
    @Column(name = "registrado_em", updatable = false)
    private LocalDateTime registradoEm;
}