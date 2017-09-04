package funjava.javaimmutables;

import java.math.BigDecimal;
import java.util.Objects;

public class BankingTransactionJavaImmutable {

    private final AccountJavaImmutable fromAccount;

    private final AccountJavaImmutable toAccount;

    private final BigDecimal amount;

    public BankingTransactionJavaImmutable(AccountJavaImmutable fromAccount, AccountJavaImmutable toAccount, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    //<editor-fold desc="Getter, equals, toString">
    public AccountJavaImmutable getFromAccount() {
        return fromAccount;
    }

    public AccountJavaImmutable getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankingTransactionJavaImmutable that = (BankingTransactionJavaImmutable) o;
        return Objects.equals(fromAccount, that.fromAccount) &&
                Objects.equals(toAccount, that.toAccount) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccount, toAccount, amount);
    }

    @Override
    public String toString() {
        return "BankingTransactionJavaImmutable{" +
                "fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                '}';
    }
    //</editor-fold>
}
