package funjava.block2;

import funjava.beans.AccountBean;
import funjava.beans.BankingTransactionBean;
import funjava.beans.BeanData;
import funjava.beans.PersonBean;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Block2Exercises {

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

        //
        //Aufgabe: Werdet das 'for' und den davon getrennten sort()-Schritt los!
        //
        for (PersonBean person : DATA.getPeople()) {
            names.add(person.getName());
        }

        names.sort(String::compareTo);

        return names;
    }

    @Test
    public void testGetAnyPersonWithNegativeBalance() {
        PersonBean negativeBalanceAccountOwner = getAnyPersonWithNegativeBalance();

        Assert.assertTrue(negativeBalanceAccountOwner != null);
        Assert.assertTrue(negativeBalanceAccountOwner.getName().equals("Bob") ||
                negativeBalanceAccountOwner.getName().equals("Eve"));
    }


    /**
     * @return Eine beliebige Person aus DATA die mindestens ein Konto mit negativem Kontostand besitzt.
     * (In den Testdaten sind dies Bob und Eve)
     */
    public PersonBean getAnyPersonWithNegativeBalance() {
        PersonBean negativeBalanceAccountOwner = null;

        //
        // Aufgabe: Das 'for' loswerden.
        //
        for (AccountBean account : DATA.getAccounts()) {
            if (account.getBalance().signum() < 0) {
                negativeBalanceAccountOwner = account.getOwner();
            }
        }

        //
        // Aufgabe: Statt eines Null-baren Rückgabewertes ein Optional zurückliefern. Erfordert Anpassungen am Test.
        //
        return negativeBalanceAccountOwner;
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
        //
        // Aufgabe: Der gesamte Funktionskörper lässt sich als ein Stream-Ausdruck darstellen!
        //

        BigDecimal expenses = BigDecimal.ZERO;

        for (BankingTransactionBean bankingTransaction : DATA.getBankingTransactions()) {
            if (bankingTransaction.getFromAccount().getAccountNumber().equals(accountNumber)) {
                expenses = expenses.add(bankingTransaction.getAmount());
            }
        }

        return expenses;
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
        //
        //Aufgabe: Benutzt 'personBeanOptional' um die Prüfungen in dieser Methode mit map() und filter() etwas
        //kompakter zu gestalten.
        //

        if (person.getAddresses().size() > 1) {
            return Optional.empty();
        }

        if (!person.getNationality().isPresent()) {
            return Optional.empty();
        }

        int age = person.getBirthdate().until(LocalDate.now()).getYears();
        if (age < 16) {
            return Optional.empty();
        }

        return Optional.of(age);
    }
}
