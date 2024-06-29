package com.nlu.packages.enums;

public enum EIngredientType {
  MILKS("Sữa"), SWEETENERS("Đồ ngọt"), TOPPINGS("Toppings");
  private final String text;

  private EIngredientType(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public EIngredientType valueOfLabel(String label) {
    for (EIngredientType e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
