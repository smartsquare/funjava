package funjava.immutableslib;


import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Value.Immutable(prehash = true)
@MyStyle
public interface BankingTransactionILib {

    @Value.Parameter(order = 1)
    AccountILib fromAccount();

    @Value.Parameter(order = 3)
    AccountILib toAccount();

    @Value.Parameter(order = 2)
    BigDecimal amount();

    @Value.Lazy
    default int getAddressCount(){
        Set<AddressILib> fromAddresses = fromAccount().getPerson().getAddresses();
        Set<AddressILib> toAddresses = toAccount().getPerson().getAddresses();
        Set<AddressILib> allAddresses = new HashSet<>();
        allAddresses.addAll(fromAddresses);
        allAddresses.addAll(toAddresses);
        System.out.println("lazy address count!");
        return allAddresses.size();
    }
}
