package com.nlu.packages.enums;

public enum EIngredient {
    SUA_TUOI("SUA_TUOI"),
    CHOCOLATE("CHOCOLATE"),
    THACH_DUA("THACH_DUA"),
    NHIEU_DUONG("NHIEU_DUONG"),
    IT_DUONG("IT_DUONG"),
    TRAN_CHAU("TRAN_CHAU"),
    KEM_TUOI("KEM_TUOI"),
    SHOT_EXPRESSO("SHOT_EXPRESSO"),
    SOT_CARAMEL("SOT_CARAMEL"),;
    private final String text;

    private EIngredient(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public EIngredient valueOfLabel(String label) {
        for (EIngredient e : values()) {
            if (e.text.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
