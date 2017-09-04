package funjava.immutableslib.solution;

import funjava.immutableslib.AccountValue;
import funjava.immutableslib.AddressValue;
import funjava.immutableslib.PersonValue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

public class BranchBankTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void atLeastOneEmployee() {
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("At least one employee is required!");

        ImBranchBankValue.builder()
                .name("foo")
                .address(AddressValue.of("a", "b"))
                .accounts(Collections.singletonList(AccountValue.of(
                        "c",
                        BigDecimal.ONE,
                        PersonValue.of(
                                "d",
                                LocalDate.now(),
                                Collections.singletonList(AddressValue.of("e", "f")),
                                Optional.of("de")
                        )
                )))
                .build();
    }

    @Test
    public void atLeastOneAccount() {
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("At least one account is required!");

        ImBranchBankValue.builder()
                .name("foo")
                .address(AddressValue.of("a", "b"))
                .employees(Collections.singletonList(PersonValue.of(
                        "d",
                        LocalDate.now(),
                        Collections.singletonList(AddressValue.of("e", "f")),
                        Optional.of("de")
                )))
                .build();
    }

}
