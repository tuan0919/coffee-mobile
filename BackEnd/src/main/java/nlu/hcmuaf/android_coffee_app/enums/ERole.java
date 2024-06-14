package nlu.hcmuaf.android_coffee_app.enums;

public enum ERole {
  ADMIN("ADMIN"), MANAGER("MANAGER"), USER("USER");
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
