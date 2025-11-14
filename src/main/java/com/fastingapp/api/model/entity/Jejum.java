package com.fastingapp.api.model.entity;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import com.fastingapp.api.model.enums.StatusJejum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "jejuns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jejum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime inicio;

    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusJejum status = StatusJejum.ATIVO;

    @Column(name = "duracao_horas")
    private Double duracaoHoras;

    @Column(name = "meta_horas")
    private Integer metaHoras;

    @Enumerated(EnumType.STRING)
    private ProtocoloJejum protocolo;

    @Column(name = "meta_atingida")
    private Boolean metaAtingida = false;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // Método para calcular duração automaticamente
    @PreUpdate
    @PrePersist
    public void calcularDuracao() {
        if (inicio != null && fim != null) {
            Duration duration = Duration.between(inicio, fim);
            this.duracaoHoras = duration.toMinutes() / 60.0;

            if (metaHoras != null) {
                this.metaAtingida = this.duracaoHoras >= metaHoras;
            }
        }
    }
}