package funjava.vavr;

import io.vavr.control.Option;
import static io.vavr.API.*;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class VavrDataTest {
    @Test
    public void load() throws Exception {
        VavrData data = new VavrData();
        assertEquals(20, data.getBankingTransactions().size());
        assertEquals(7, data.getAccounts().size());
        assertEquals(7, data.getAddresses().size());
        assertEquals(5, data.getPeople().size());
    }

    @Test
    public void aliceHasGermanNationality(){
        VavrData data = new VavrData();
        Option<PersonVavr> aliceOptional = data.getPeople().toStream()
                .filter(p -> p.getName().equals("Alice"))
                .headOption();
        assertTrue(aliceOptional.isDefined());
        PersonVavr alice = aliceOptional.get();
        assertEquals(Some("DE"), alice.getNationality());
    }

    @Test
    public void daveHasNoNationality(){
        VavrData data = new VavrData();
        Option<PersonVavr> daveOptional = data.getPeople().toStream()
                .filter(p -> p.getName().equals("Dave"))
                .headOption();
        assertTrue(daveOptional.isDefined());
        PersonVavr dave = daveOptional.get();
        assertEquals(None(), dave.getNationality());
    }
}