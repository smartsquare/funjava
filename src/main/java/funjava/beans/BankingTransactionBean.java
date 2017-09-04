package funjava.beans;

import java.math.BigDecimal;
import java.util.Objects;

public class BankingTransactionBean {

    private AccountBean fromAccount;

    private AccountBean toAccount;

    private BigDecimal amount;

    //<editor-fold desc="Getter, Setter, etc.">
    public AccountBean getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountBean fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountBean getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountBean toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BankingTransaction{" +
                "fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankingTransactionBean that = (BankingTransactionBean) o;
        return Objects.equals(fromAccount, that.fromAccount) &&
                Objects.equals(toAccount, that.toAccount) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccount, toAccount, amount);
    }
    //</editor-fold>
}
