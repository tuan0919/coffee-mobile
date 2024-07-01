package com.nlu.packages.inventory.size;

import com.nlu.packages.enums.EProductSize;

import java.util.Map;

public class SizeType {
    static Map<String, EProductSize> nameMap = Map.of(
            "Bé",EProductSize.SMALL,
            "Vừa",EProductSize.MEDIUM,
            "To",EProductSize.LARGE
    );
    EProductSize eps;
    public SizeType(EProductSize eps) {
        this.eps = eps;
    }

    public EProductSize getEps() {
        return eps;
    }

    public void setEps(EProductSize eps) {
        this.eps = eps;
    }

    public String stringEPS(){
        return nameMap.entrySet().stream().filter(e -> e.getValue() == this.getEps())
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
