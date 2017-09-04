package funjava.immutableslib;

import funjava.utils.Data;
import funjava.utils.DataLoader;

import java.util.Collection;
import java.util.Optional;

public class ILibData implements Data<AddressILib, PersonILib, AccountILib, BankingTransactionILib> {

    private final DataLoader<AddressILib, PersonILib, AccountILib, BankingTransactionILib> loader;

    public ILibData(){
        loader = DataLoader.loadData((AddressValue::of),
                (name, birthdate, addresses, nationality) -> PersonValue.of(name, birthdate, addresses, Optional.ofNullable(nationality)),
                AccountValue::of,
                (fromAccount, toAccount, amount) -> BankingTransactionValue.of(fromAccount, amount, toAccount));
    }

    @Override
    public Collection<PersonILib> getPeople() {
        return loader.getPeople();
    }

    @Override
    public Collection<AddressILib> getAddresses() {
        return loader.getAddresses();
    }

    @Override
    public Collection<AccountILib> getAccounts() {
        return loader.getAccounts();
    }

    @Override
    public Collection<BankingTransactionILib> getBankingTransactions() {
        return loader.getBankingTransactions();
    }
}
