package com.nlu.packages.enums;

public enum EGender {
  MALE("Nam"), FEMALE("Nữ");
  private final String text;

  private EGender(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }

  public static EGender valueOfLabel(String label) {
    for (EGender e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
