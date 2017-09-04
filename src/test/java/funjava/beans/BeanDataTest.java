package funjava.beans;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BeanDataTest {
    @Test
    public void load() throws Exception {
        BeanData beanData = new BeanData();
        assertEquals(20, beanData.getBankingTransactions().size());
        assertEquals(7, beanData.getAccounts().size());
        assertEquals(7, beanData.getAddresses().size());
        assertEquals(5, beanData.getPeople().size());
    }

    @Test
    public void aliceHasGermanNationality(){
        BeanData beanData = new BeanData();
        Optional<PersonBean> aliceOptional = beanData.getPeople().stream()
                .filter(p -> p.getName().equals("Alice"))
                .findFirst();
        assertTrue(aliceOptional.isPresent());
        PersonBean alice = aliceOptional.get();
        assertEquals(Optional.of("DE"), alice.getNationality());
    }

    @Test
    public void daveHasNoNationality(){
        BeanData beanData = new BeanData();
        Optional<PersonBean> daveOptional = beanData.getPeople().stream()
                .filter(p -> p.getName().equals("Dave"))
                .findFirst();
        assertTrue(daveOptional.isPresent());
        PersonBean dave = daveOptional.get();
        assertEquals(Optional.empty(), dave.getNationality());
    }
}