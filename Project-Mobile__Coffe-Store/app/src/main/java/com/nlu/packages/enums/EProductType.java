package com.nlu.packages.enums;

import java.util.HashMap;
import java.util.Map;

public enum EProductType {
  DRINK("Nước uống"), FOOD("Thức ăn"), FRUIT("Trái cây");
  private final String text;

  private EProductType(String text) {
    this.text = text;
  }
  public static final Map<String, EProductType> MAP_TO_PATHNAME;
  static {
      MAP_TO_PATHNAME = new HashMap<>();
    MAP_TO_PATHNAME.put("nuoc-uong", DRINK);
    MAP_TO_PATHNAME.put("thuc-an", FOOD);
    MAP_TO_PATHNAME.put("trai-cay", FRUIT);
  };
  @Override
  public String toString() {
    return text;
  }

  public EProductType valueOfLabel(String label) {
    for (EProductType e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
