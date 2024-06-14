package nlu.hcmuaf.android_coffee_app.enums;

public enum EProductType {
  COFFEE("Cà phê"), TEA("Trà"), ICE_BLENDED("Đá xay"), FOOD("Thức ăn"), FRUIT("Trái cây");
  private final String text;

  private EProductType(String text) {
    this.text = text;
  }

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
