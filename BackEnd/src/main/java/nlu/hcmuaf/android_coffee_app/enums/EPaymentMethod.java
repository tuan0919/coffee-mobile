package nlu.hcmuaf.android_coffee_app.enums;

import java.util.HashSet;
import java.util.Set;

public enum EPaymentMethod {
  CREDIT_CART("Thẻ Tín Dụng"),
  COD("Thanh Toán Khi Nhận Hàng"),
  BANK_CART("Thẻ Ngân Hàng"),
  MOMO("MoMo");
  private final String text;

  private EPaymentMethod(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }

  public EPaymentMethod valueOfLabel(String label) {
    for (EPaymentMethod e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
