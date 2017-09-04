package funjava.javaimmutables;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountJavaImmutable {
    private final String accountNumber;

    private final BigDecimal balance;

    private final PersonJavaImmutable owner;

    public AccountJavaImmutable(String accountNumber, BigDecimal balance, PersonJavaImmutable owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    //<editor-fold desc="Getter, equals, toString">

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public PersonJavaImmutable getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountJavaImmutable that = (AccountJavaImmutable) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, owner);
    }

    @Override
    public String toString() {
        return "AccountJavaImmutable{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
    //</editor-fold>
}
