package funjava.vavr;

import org.immutables.value.Value;
import org.immutables.vavr.encodings.VavrEncodingEnabled;

import java.math.BigDecimal;

@Value.Immutable
@VavrEncodingEnabled
public interface BankingTransactionVavr {

    AccountVavr getFromAccount();

    AccountVavr getToAccount();

    BigDecimal getAmount();
}
