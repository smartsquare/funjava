package funjava.vavr;

import org.immutables.value.Value;
import org.immutables.vavr.encodings.VavrEncodingEnabled;

import java.math.BigDecimal;

@Value.Immutable
@VavrEncodingEnabled
public interface AccountVavr {

    String getAccountNumber();

    PersonVavr getOwner();

    BigDecimal getBalance();
}
