package funjava.utils;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import static io.vavr.API.*;
import static io.vavr.Function1.identity;

public class DataLoader<A, P, Ac, B> {

    private final CreateAddressFunction<A> addressCreator;
    private final CreatePersonFunction<A, P> personCreator;
    private final CreateAccountFunction<P, Ac> accountCreator;
    private final CreateBankingTransactionFunction<Ac, B> bankingTransactionCreator;

    private Map<String, P> personMap = Map();
    private Set<A> addressSet = Set();
    private Map<String, Ac> accountMap = Map();
    private List<B> transactionList = List();

    private DataLoader(CreateAddressFunction<A> addressCreator,
                       CreatePersonFunction<A, P> personCreator,
                       CreateAccountFunction<P, Ac> accountCreator,
                       CreateBankingTransactionFunction<Ac, B> bankingTransactionCreator) {

        this.addressCreator = addressCreator;
        this.personCreator = personCreator;
        this.accountCreator = accountCreator;
        this.bankingTransactionCreator = bankingTransactionCreator;
    }

    public static  <A, P, Ac, B> DataLoader<A, P, Ac, B> loadData(CreateAddressFunction<A> addressCreator,
                                                                  CreatePersonFunction<A, P> personCreator,
                                                                  CreateAccountFunction<P, Ac> accountCreator,
                                                                  CreateBankingTransactionFunction<Ac, B> bankingTransactionCreator){
        DataLoader<A, P, Ac, B> loader = new DataLoader<>(addressCreator, personCreator, accountCreator, bankingTransactionCreator);
        loader.loadPeople();
        loader.loadAccounts();
        loader.loadTransactions();
        return loader;
    }

    private void loadPeople() {
        personMap = CsvParser.parseCsv("people.csv").stream()
                .map(row -> {
                    String name = row.get("Name");
                    LocalDate birthDate = LocalDate.parse(row.get("Birthdate"));
                    String nationality = row.get("Nationality");
                    String street1 = row.get("Street1");
                    String street2 = row.get("Street2");
                    String street3 = row.get("Street3");
                    String city1 = row.get("City1");
                    String city2 = row.get("City2");
                    String city3 = row.get("City3");
                    Option<A> address1 = initAddress(street1, city1);
                    Option<A> address2 = initAddress(street2, city2);
                    Option<A> address3 = initAddress(street3, city3);
                    Set<A> addresses = Set(address1, address2, address3)
                            .flatMap(identity());
                    addressSet = addressSet.addAll(addresses);
                    P person = personCreator.create(name, birthDate, addresses.toJavaSet(), nationality);
                    return Tuple(name, person);
                }).collect(HashMap.collector());
    }

    private Option<A> initAddress(String street, String city) {
        if (street != null && city != null) {
            return Some(addressCreator.create(street, city));
        } else {
            return None();
        }
    }

    private void loadAccounts() {
        accountMap = CsvParser.parseCsv("accounts.csv").stream()
                .map(row -> {
                    String accountNumber = row.get("No");
                    BigDecimal balance = new BigDecimal(row.get("Balance"));
                    String ownerName = row.get("Owner");

                    P owner = personMap.get(ownerName)
                            .getOrElseThrow(() -> new RuntimeException("No Person with name " + ownerName + " found"));

                    return Tuple(accountNumber, accountCreator.create(accountNumber, balance, owner));

                }).collect(HashMap.collector());
    }

    private void loadTransactions() {
        transactionList = CsvParser.parseCsv("transactions.csv").stream()
                .map(row -> {
                    String fromAccountId = row.get("From");
                    String toAccountId = row.get("To");
                    BigDecimal amount = new BigDecimal(row.get("Amount"));
                    Ac fromAccount = accountMap.get(fromAccountId)
                            .getOrElseThrow(() -> new RuntimeException("No account with number " + fromAccountId + " found"));
                    Ac toAccount = accountMap.get(toAccountId)
                            .getOrElseThrow(() -> new RuntimeException("No account with number " + toAccountId + " found"));
                    return bankingTransactionCreator.create(fromAccount, toAccount, amount);
                }).collect(List.collector());
    }

    public Collection<A> getAddresses(){
        return addressSet.toJavaSet();
    }

    public Collection<P> getPeople(){
        return personMap.values().asJava();
    }

    public Collection<Ac> getAccounts(){
        return accountMap.values().asJava();
    }

    public Collection<B> getBankingTransactions(){
        return transactionList.asJava();
    }

}
