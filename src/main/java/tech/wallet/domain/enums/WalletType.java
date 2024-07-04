package tech.wallet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WalletType {

  USER(1, "user"),
  SHOPKEEPER(2, "shopkeeper");

  private final Integer typeCode;
  private final String description;

  public static WalletType of(Integer typeCode) {
    for (WalletType t : values()) {
      if (t.getTypeCode().equals(typeCode)) {
        return t;
      }
    }
    return null;
  }
}
