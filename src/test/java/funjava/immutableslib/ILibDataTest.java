package funjava.immutableslib;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ILibDataTest {

    private static ILibData DATA = new ILibData();

    @Test
    public void load() throws Exception {
        assertEquals(20, DATA.getBankingTransactions().size());
        assertEquals(7, DATA.getAccounts().size());
        assertEquals(7, DATA.getAddresses().size());
        assertEquals(5, DATA.getPeople().size());
    }

    @Test
    public void aliceHasGermanNationality(){
        Optional<PersonILib> aliceOptional = DATA.getPeople().stream()
                .filter(p -> p.getName().equals("Alice"))
                .findFirst();
        assertTrue(aliceOptional.isPresent());
        PersonILib alice = aliceOptional.get();
        assertEquals(Optional.of("DE"), alice.getNationality());
    }

    @Test
    public void daveHasNoNationality(){
        Optional<PersonILib> daveOptional = DATA.getPeople().stream()
                .filter(p -> p.getName().equals("Dave"))
                .findFirst();
        assertTrue(daveOptional.isPresent());
        PersonILib dave = daveOptional.get();
        assertEquals(Optional.empty(), dave.getNationality());
    }
}