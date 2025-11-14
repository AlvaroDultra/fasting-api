package com.fastingapp.api.model.entity;

import com.fastingapp.api.model.enums.ProtocoloJejum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "metas_semanais")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "semana_inicio", nullable = false)
    private LocalDate semanaInicio;

    @Column(name = "semana_fim", nullable = false)
    private LocalDate semanaFim;

    @Column(name = "meta_dias")
    private Integer metaDias = 5;

    @Column(name = "meta_horas_total")
    private Integer metaHorasTotal = 80;

    @Enumerated(EnumType.STRING)
    @Column(name = "protocolo_alvo")
    private ProtocoloJejum protocoloAlvo;

    @Column(name = "jejuns_concluidos")
    private Integer jejunsConcluidos = 0;

    @Column(name = "horas_totais")
    private Double horasTotais = 0.0;

    @Column(name = "meta_cumprida")
    private Boolean metaCumprida = false;

    @Column(name = "selo_semana", length = 50)
    private String seloSemana;
}