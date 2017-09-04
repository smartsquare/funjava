package funjava.javaimmutables;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaImmutablesDataTest {
    @Test
    public void load() throws Exception {
        JavaImmutablesData data = new JavaImmutablesData();
        assertEquals(20, data.getBankingTransactions().size());
        assertEquals(7, data.getAccounts().size());
        assertEquals(7, data.getAddresses().size());
        assertEquals(5, data.getPeople().size());
    }

    @Test
    public void aliceHasGermanNationality(){
        JavaImmutablesData data = new JavaImmutablesData();
        Optional<PersonJavaImmutable> aliceOptional = data.getPeople().stream()
                .filter(p -> p.getName().equals("Alice"))
                .findFirst();
        assertTrue(aliceOptional.isPresent());
        PersonJavaImmutable alice = aliceOptional.get();
        assertEquals(Optional.of("DE"), alice.getNationality());
    }

    @Test
    public void daveHasNoNationality(){
        JavaImmutablesData data = new JavaImmutablesData();
        Optional<PersonJavaImmutable> daveOptional = data.getPeople().stream()
                .filter(p -> p.getName().equals("Dave"))
                .findFirst();
        assertTrue(daveOptional.isPresent());
        PersonJavaImmutable dave = daveOptional.get();
        assertEquals(Optional.empty(), dave.getNationality());
    }
}