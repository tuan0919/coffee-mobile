package com.nlu.packages.enums;

public enum ERole {
  ROLE_ADMIN("ROLE_ADMIN"), ROLE_MANAGER("ROLE_MANAGER"), ROLE_USER("ROLE_USER");
  private final String text;

  private ERole(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public ERole valueOfLabel(String label) {
    for (ERole e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
