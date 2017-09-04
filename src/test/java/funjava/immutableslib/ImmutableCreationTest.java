package funjava.immutableslib;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImmutableCreationTest {

    private static ILibData DATA = new ILibData();

    @Test
    public void buildAddressWithMethod(){
        AddressILib address = AddressValue.of("street", "city"); // <1>

        assertEquals("street", address.getStreet());
        assertEquals("city", address.getCity());
    }

    @Test
    public void buildAddressWithBuilder(){
        AddressValue biAddress = AddressValue.builder()
                .city("Bielefeld")
                .street("Krokusweg")
                .build();

        assertEquals("Bielefeld", biAddress.getCity());
        assertEquals("Krokusweg", biAddress.getStreet());
    }

    @Test
    public void buildPersonWithAddresses(){
        AddressValue address1 = AddressValue.of("street1", "city1");
        AddressValue address2 = AddressValue.of("street2", "city2");
        PersonValue mustermann = PersonValue.builder() // <1>
                .name("Mustermann")  // <2>
                .birthdate(LocalDate.of(1990, 12, 31)) // <3>
                .addAddress(address1) // <4>
                .addAddress(address2)
                .addAllAddresses(DATA.getAddresses()) // <5>
                .build(); // <6>

        assertEquals(9, mustermann.getAddresses().size());
    }

    @Test
    public void createCopyWithBuilder(){
        AccountILib account = DATA.getAccounts().iterator().next();
        AccountValue newAccount = AccountValue.builder()
                .from(account) // <1>
                .accountNumber("newAccountNumber")
                .balance(BigDecimal.ZERO)
                .build();

        assertEquals(account.getPerson(), newAccount.getPerson());
        assertEquals("newAccountNumber", newAccount.getAccountNumber());
        assertEquals(BigDecimal.ZERO.intValue(), newAccount.getBalance().intValue());
    }

    @Test
    public void copyImmutables(){
        AccountILib account = DATA.getAccounts().iterator().next();
        AccountValue copyOfAccount = AccountValue.copyOf(account);

        assertTrue(account.equals(copyOfAccount));
        assertTrue(account == copyOfAccount);
    }

    @Test
    public void createFromCopyWithNewAttributes(){
        AccountILib account = DATA.getAccounts().iterator().next();
        AccountValue newAccount = AccountValue.copyOf(account) // <1>
                .withAccountNumber("newAccountNumber") // <2>
                .withBalance(BigDecimal.ZERO);

        assertEquals(account.getPerson(), newAccount.getPerson());
        assertEquals("newAccountNumber", newAccount.getAccountNumber());
        assertEquals(BigDecimal.ZERO.intValue(), newAccount.getBalance().intValue());

    }

}
