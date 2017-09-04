package funjava.beans;

import funjava.utils.*;

import java.util.Collection;
import java.util.HashSet;

public class BeanData implements Data<AddressBean, PersonBean, AccountBean, BankingTransactionBean> {

    private final DataLoader<AddressBean, PersonBean, AccountBean, BankingTransactionBean> loader;

    public BeanData() {
        CreateAddressFunction<AddressBean> addressCreator = (street, city) -> {
            AddressBean addressBean = new AddressBean();
            addressBean.setStreet(street);
            addressBean.setCity(city);
            return addressBean;
        };
        CreatePersonFunction<AddressBean, PersonBean> personCreator = (name, birthdate, addresses, nationality) -> {
            PersonBean personBean = new PersonBean();
            personBean.setName(name);
            personBean.setBirthdate(birthdate);
            personBean.setAddresses(new HashSet<>(addresses));
            personBean.setNationality(nationality);
            return personBean;
        };
        CreateAccountFunction<PersonBean, AccountBean> accountCreator = (accountNumber, balance, owner) -> {
            AccountBean accountBean = new AccountBean();
            accountBean.setAccountNumber(accountNumber);
            accountBean.setBalance(balance);
            accountBean.setOwner(owner);
            return accountBean;
        };
        CreateBankingTransactionFunction<AccountBean, BankingTransactionBean> transactionCreator = (from, to, amount) -> {
            BankingTransactionBean bankingTransactionBean = new BankingTransactionBean();
            bankingTransactionBean.setFromAccount(from);
            bankingTransactionBean.setToAccount(to);
            bankingTransactionBean.setAmount(amount);
            return bankingTransactionBean;
        };
        loader = DataLoader.loadData(addressCreator, personCreator, accountCreator, transactionCreator);
    }

    @Override
    public Collection<PersonBean> getPeople() {
        return loader.getPeople();
    }

    public PersonBean getPersonByName(String name) {
        for (PersonBean person: getPeople()) {
            if (name.equals(person.getName())) {
                return person;
            }
        }
        return null;
    }

    @Override
    public Collection<AddressBean> getAddresses() {
        return loader.getAddresses();
    }

    @Override
    public Collection<AccountBean> getAccounts() {
        return loader.getAccounts();
    }

    @Override
    public Collection<BankingTransactionBean> getBankingTransactions() {
        return loader.getBankingTransactions();
    }
}
