package funjava.utils;

import java.math.BigDecimal;

@FunctionalInterface
public interface CreateAccountFunction<P, A> {

    A create(String accountNumber, BigDecimal balance, P owner);

}
