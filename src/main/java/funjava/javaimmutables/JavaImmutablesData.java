package funjava.javaimmutables;

import funjava.utils.Data;
import funjava.utils.DataLoader;

import java.util.Collection;
import java.util.HashSet;

public class JavaImmutablesData implements Data<AddressJavaImmutable, PersonJavaImmutable, AccountJavaImmutable, BankingTransactionJavaImmutable> {

    private final DataLoader<AddressJavaImmutable, PersonJavaImmutable, AccountJavaImmutable, BankingTransactionJavaImmutable> loader;

    public JavaImmutablesData() {
        loader = DataLoader.loadData(AddressJavaImmutable::new,
                (name, birthdate, addresses, nationality) -> new PersonJavaImmutable(name, birthdate, new HashSet<>(addresses), nationality),
                AccountJavaImmutable::new,
                BankingTransactionJavaImmutable::new);
    }


    //<editor-fold desc="Getter">
    public Collection<PersonJavaImmutable> getPeople() {
        return loader.getPeople();
    }

    public Collection<AddressJavaImmutable> getAddresses() {
        return loader.getAddresses();
    }

    public Collection<AccountJavaImmutable> getAccounts() {
        return loader.getAccounts();
    }

    public Collection<BankingTransactionJavaImmutable> getBankingTransactions() {
        return loader.getBankingTransactions();
    }
    //</editor-fold>
}
