package com.nlu.packages.enums;

import lombok.Getter;

@Getter
public enum EProductSize {
  SMALL(1.0f), MEDIUM(1.25f), LARGE(1.5f);
  private final float multipler;

  private EProductSize(float multipler) {
    this.multipler = multipler;
  }

  @Override
  public String toString() {
    return multipler+"";
  }
}
