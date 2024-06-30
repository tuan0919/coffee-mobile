package com.nlu.packages.enums;

public enum EOrderStatus {
  CONFIRMING("Chờ xác nhận"), PREPARING("Đang chuẩn bị"),
  DELIVERING("Đang giao hàng"), DELIVERED(
      "Đã giao hàng"), CANCELLED("Đã hủy");
  private final String text;

  private EOrderStatus(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }

  public static EOrderStatus valueOfLabels(String label) {
    for (EOrderStatus e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
