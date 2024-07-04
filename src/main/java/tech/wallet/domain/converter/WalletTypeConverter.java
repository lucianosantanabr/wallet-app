package tech.wallet.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.wallet.domain.enums.WalletType;

@Converter(autoApply = true)
public class WalletTypeConverter implements AttributeConverter<WalletType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(WalletType walletType) {
    return walletType != null ? walletType.getTypeCode() : null;
  }

  @Override
  public WalletType convertToEntityAttribute(Integer type) {
    return WalletType.of(type);
  }
}
