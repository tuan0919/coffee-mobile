package com.nlu.packages.enums;

import java.util.HashMap;
import java.util.Map;

public enum ECategory {
  CA_PHE_MAY("Cà phê máy"),
  CA_PHE_VIET_NAM("Cà phê việt nam"),
  COLD_BREW("Cold brew"),
  TRAI_CAY_XAY("Trái cây xay"),
  TRA_TRAI_CAY("Trà trái cây"),
  TRA_XANH("Trà xanh"),
  DA_XAY("Đá xay"),
  BANH_NGOT("Bánh ngọt"),
  BANH_MAN("Bánh mặn"),
  BANH_SNACK("Bánh snack");
  private final String text;
  public static final Map<String, ECategory> MAP_TO_PATHNAME;
  static {
    MAP_TO_PATHNAME = new HashMap<>();
    MAP_TO_PATHNAME.put("ca-phe-may", CA_PHE_MAY);
    MAP_TO_PATHNAME.put("ca-phe-viet-nam", CA_PHE_VIET_NAM);
    MAP_TO_PATHNAME.put("cold-brew", COLD_BREW);
    MAP_TO_PATHNAME.put("trai-cay-xay", TRAI_CAY_XAY);
    MAP_TO_PATHNAME.put("tra-trai-cay", TRA_TRAI_CAY);
    MAP_TO_PATHNAME.put("tra-xanh", TRA_XANH);
    MAP_TO_PATHNAME.put("da-xay", DA_XAY);
    MAP_TO_PATHNAME.put("banh-ngot", BANH_NGOT);
    MAP_TO_PATHNAME.put("banh-man", BANH_MAN);
    MAP_TO_PATHNAME.put("banh-snack", BANH_SNACK);
  };
  private ECategory(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public ECategory valueOfLabel(String label) {
    for (ECategory e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
