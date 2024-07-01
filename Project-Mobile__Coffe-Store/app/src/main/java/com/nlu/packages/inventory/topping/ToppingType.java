package com.nlu.packages.inventory.topping;

import com.nlu.packages.enums.EIngredient;

import java.util.Map;

public class ToppingType {
    static Map<String, EIngredient> nameMap = Map.of(
            "Ít Đường", EIngredient.IT_DUONG,
            "Chocolate", EIngredient.CHOCOLATE,
            "Nhiều đường", EIngredient.NHIEU_DUONG,
            "Shot Expresso", EIngredient.SHOT_EXPRESSO,
            "Sốt Caramel", EIngredient.SOT_CARAMEL,
            "Thạch Dừa", EIngredient.THACH_DUA,
            "Trân Châu", EIngredient.TRAN_CHAU,
            "Kem Tươi", EIngredient.KEM_TUOI
    );
    EIngredient ei;
    public ToppingType(EIngredient ei){
        this.ei = ei;
    }

    public EIngredient getEi() {
        return ei;
    }

    public void setEi(EIngredient ei) {
        this.ei = ei;
    }

    public String stringEI(){
        return nameMap.entrySet().stream().filter(e -> e.getValue() == this.getEi())
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
