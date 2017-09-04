package funjava.immutableslib.solution;

import funjava.immutableslib.AccountILib;
import funjava.immutableslib.AddressILib;
import funjava.immutableslib.PersonILib;
import org.immutables.value.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value.Immutable
@MyBetterStyle
public interface BranchBank {

    String getName();

    AddressILib getAddress();

    @Value.Redacted
    Set<PersonILib> getEmployees();

    @Value.Redacted
    Set<AccountILib> getAccounts();

    @Value.Derived
    default Set<PersonILib> getCustomers() {
        return getAccounts().stream()
                .map(AccountILib::getPerson)
                .collect(Collectors.toSet());
    }

    @Value.Check
    default void checkALeastOneEmployee() {
        if (getEmployees().isEmpty()) {
            throw new IllegalStateException("At least one employee is required!");
        }
    }

    @Value.Check
    default void checkALeastOneAccount() {
        if (getAccounts().isEmpty()) {
            throw new IllegalStateException("At least one account is required!");
        }
    }

}
