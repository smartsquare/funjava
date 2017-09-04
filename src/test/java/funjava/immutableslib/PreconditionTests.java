package funjava.immutableslib;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PreconditionTests {

    private static ILibData DATA = new ILibData();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void futureBirthdateShouldNotBeAllowed(){
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Future birthdate");
        PersonValue.builder()
                .name("name")
                .birthdate(LocalDate.now().plusDays(10))
                .build();
    }

    @Test
    public void balanceScaleShouldBeSetCorrectly(){
        AccountValue account = AccountValue.builder()
                .accountNumber("123")
                .person(DATA.getPeople().iterator().next())
                .balance(BigDecimal.TEN)
                .build();

        assertEquals(4, account.getBalance().scale());
    }

}
