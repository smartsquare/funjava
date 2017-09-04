package funjava.block2.solution;

import funjava.beans.AccountBean;
import funjava.beans.BankingTransactionBean;
import funjava.beans.BeanData;
import funjava.beans.PersonBean;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Block2Solutions {
    /* Zu den Lösungen bitte runter scrollen.


























































    */

    private static BeanData DATA = new BeanData();

    @Test
    public void testGetAllNamesAlphabetically() {
        List<String> names = getAllNamesAlphabetically();

        Assert.assertEquals(5, names.size());
        Assert.assertEquals("Alice", names.get(0));
        Assert.assertEquals("Bob", names.get(1));
        Assert.assertEquals("Carol", names.get(2));
        Assert.assertEquals("Dave", names.get(3));
        Assert.assertEquals("Eve", names.get(4));
    }

    /**
     * @return Die Namen aller in DATA vorhanden Personen, aufsteigend in alphabetischer Reihenfolge.
     */
    public List<String> getAllNamesAlphabetically() {
        List<String> names = new ArrayList<>();
        return DATA.getPeople()
                .stream()
                .map(PersonBean::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    @Test
    public void testGetAnyPersonWithNegativeBalance() {
        Optional<PersonBean> negativeBalanceAccountOwner = getAnyPersonWithNegativeBalance();

        Assert.assertTrue(negativeBalanceAccountOwner.isPresent());
        Assert.assertTrue(negativeBalanceAccountOwner.get().getName().equals("Bob") ||
                negativeBalanceAccountOwner.get().getName().equals("Eve"));
    }


    /**
     * @return Eine beliebige Person aus DATA die mindestens ein Konto mit negativem Kontostand besitzt.
     * (In den Testdaten sind dies Bob und Eve)
     */
    public Optional<PersonBean> getAnyPersonWithNegativeBalance() {
            return DATA.getAccounts()
                .stream()
                .filter(account -> account.getBalance().signum() < 0)
                .map(AccountBean::getOwner)
                .findAny();
    }


    @Test
    public void testCalculateExpensesForAccount() {
        BigDecimal expenses = calculateExpensesForAccount("K99LM5");
        Assert.assertEquals(new BigDecimal("112.11"), expenses);
    }

    /**
     * @param accountNumber
     * @return Den Gesamtbetrag aller Transaktionen die Geld <b>von</b> dem angegeben Konto abbuchen (accountNumber entspricht fromAccount)
     */
    public BigDecimal calculateExpensesForAccount(String accountNumber) {
        return DATA.getBankingTransactions()
                .stream()
                .filter(bankingTransaction -> bankingTransaction.getFromAccount().getAccountNumber().equals(accountNumber))
                .map(BankingTransactionBean::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Test
    public void testFilterOptional() {
        PersonBean bob = DATA.getPersonByName("Bob");
        PersonBean alice = DATA.getPersonByName("Alice");

        //Alice ist OK:
        Assert.assertEquals(getAgeIfCreditWorthy(alice), Optional.of(17));
        //Bob hat mehrere Adressen. Das ist uns zu unübersichtlich:
        Assert.assertFalse(getAgeIfCreditWorthy(bob).isPresent());
    }

    /**
     * Liefert das Alter der angegebenen Person zurück, falls die Person als Kreditwürdig einzustufen ist, andernfalls empty().
     *
     * Eine Person ist Kreditwürdig, wenn:
     *  die Person genau eine Adresse hat,
     *  die Nationalität der Person bekannt (nicht empty()) ist,
     *  und die Person Mindestens 16 Jahre alt ist
     */
    public Optional<Integer> getAgeIfCreditWorthy(PersonBean person) {
        Optional<PersonBean> personBeanOptional = Optional.ofNullable(person);

        return personBeanOptional
                .filter(p -> p.getAddresses().size() == 1)
                .filter(p -> p.getNationality().isPresent())
                .map(p -> p.getBirthdate().until(LocalDate.now()).getYears())
                .filter(age -> age >= 16);
    }
}
