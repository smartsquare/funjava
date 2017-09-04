package funjava.beans;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountBean {

    private String accountNumber;

    private BigDecimal balance;

    private PersonBean owner;

    //<editor-fold desc="Getter, Setter, etc.">

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public PersonBean getOwner() {
        return owner;
    }

    public void setOwner(PersonBean owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBean account = (AccountBean) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(owner, account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, owner);
    }

    //</editor-fold>
}
