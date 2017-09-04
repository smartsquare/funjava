package funjava.vavr;

import funjava.utils.Data;
import funjava.utils.DataLoader;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class VavrData implements Data<AddressVavr, PersonVavr, AccountVavr, BankingTransactionVavr> {

    private final DataLoader<AddressVavr, PersonVavr, AccountVavr, BankingTransactionVavr> loader;

    public VavrData() {
        loader = DataLoader.loadData(
                (street, city) -> ImmutableAddressVavr
                        .builder()
                        .street(street)
                        .city(city)
                        .build(),
                (name, birthdate, addresses, nationality) -> ImmutablePersonVavr
                        .builder()
                        .name(name)
                        .birthdate(birthdate)
                        .addAllAddresses(addresses)
                        .setValueNationality(nationality)
                        .build(),
                (accountNumber, balance, owner) -> ImmutableAccountVavr
                        .builder()
                        .accountNumber(accountNumber)
                        .balance(balance)
                        .owner(owner)
                        .build(),
                (fromAccount, toAccount, amount) -> ImmutableBankingTransactionVavr
                        .builder()
                        .fromAccount(fromAccount)
                        .toAccount(toAccount)
                        .amount(amount)
                        .build()
        );
    }


    //<editor-fold desc="Getter">
    public Set<PersonVavr> getPeople() {
        return HashSet.ofAll(loader.getPeople());
    }

    public Set<AddressVavr> getAddresses() {
        return HashSet.ofAll(loader.getAddresses());
    }

    public Set<AccountVavr> getAccounts() {
        return HashSet.ofAll(loader.getAccounts());
    }

    public List<BankingTransactionVavr> getBankingTransactions() {
        return List.ofAll(loader.getBankingTransactions());
    }

    //</editor-fold>
    public PersonVavr getPersonByName(String name) {
        return getPeople()
                .find(p -> p.getName().equals(name))
                .get();
    }

}
