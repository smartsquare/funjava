package funjava.utils;

import java.math.BigDecimal;

@FunctionalInterface
public interface CreateBankingTransactionFunction<A, T> {

    T create(A fromAccount, A toAccount, BigDecimal amount);
}
