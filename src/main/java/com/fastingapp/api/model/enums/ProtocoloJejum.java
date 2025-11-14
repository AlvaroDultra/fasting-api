package com.fastingapp.api.model.enums;

public enum ProtocoloJejum {
    JEJUM_12_12("12/12", 12),
    JEJUM_14_10("14/10", 14),
    JEJUM_16_8("16/8", 16),
    JEJUM_18_6("18/6", 18),
    JEJUM_20_4("20/4", 20),
    JEJUM_24H("24h (OMAD)", 24),
    JEJUM_36H("36h", 36),
    JEJUM_FLEX("Flexível", 0);

    private final String descricao;
    private final int horasMinimas;

    ProtocoloJejum(String descricao, int horasMinimas) {
        this.descricao = descricao;
        this.horasMinimas = horasMinimas;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getHorasMinimas() {
        return horasMinimas;
    }
}