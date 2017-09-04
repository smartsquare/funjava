package funjava.block5;

import funjava.vavr.BankingTransactionVavr;
import funjava.vavr.PersonVavr;
import funjava.vavr.VavrData;
import io.vavr.Function1;
import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;


public class Block5Exercises {
    private final VavrData DATA = new VavrData();

    /**
     * @return true, falls es eine Überweisung von einem Konto von Person1 zu einem Konto von Person2 oder umgekehrt gab. False andernfalls.
     */
    public boolean hasInteractedWith(PersonVavr person1, PersonVavr person2) {
        //
        //Aufgabe: Verwendet Methoden auf DATA.getBankingTransactions() um das for loszuwerden.
        //         Könnt ihr das 'if' in eine Function2<> oder Function3<> auslagern? Ist das eine gute Idee?
        //
        for (BankingTransactionVavr transaction : DATA.getBankingTransactions()) {
            PersonVavr fromOwner = transaction.getFromAccount().getOwner();
            PersonVavr toOwner = transaction.getToAccount().getOwner();
            if ((fromOwner.equals(person1) && toOwner.equals(person2)) || (fromOwner.equals(person2) && toOwner.equals(person1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true, falls es eine Überweisung von einem Konto von person zu einem Konto von Bob gab oder umgekehrt. False andernfalls.
     */
    public boolean hasInteractedWithBob(PersonVavr person) {
        //
        //Aufgabe: In testHasInteractedWithBob() befindet sich eine Function1()-Variable die bisher keinen Wert enthält.
        //         Konstruiert mit Vavr einen Ersatz für hasInteractedWithBob um die bestehende
        //         hasInteractedWithBob()-Methode komplett löschen zu können.
        return hasInteractedWith(DATA.getPersonByName("Bob"), person);
    }

    @Test
    public void testHasInteractedWithBob() {
        PersonVavr alice = DATA.getPersonByName("Alice");
        PersonVavr bob = DATA.getPersonByName("Bob");
        PersonVavr eve = DATA.getPersonByName("Eve");

        //Aufgabe: Siehe Kommentar in der Methode hasInteractedWithBob(PersonVavr)
        Function1<PersonVavr, Boolean> hasInteractedWithBobFunction = null;

        Assert.assertFalse(hasInteractedWithBob(bob));
        Assert.assertFalse(hasInteractedWithBob(eve));
        Assert.assertTrue(hasInteractedWithBob(alice));
    }


    //Temperaturen in Grad Celsius in Bielefeld im Juni 2017, Laut https://www.accuweather.com/de/de/bielefeld/33602/june-weather/170367?monyr=6/1/2017&view=table
    public static final List<Integer> TEMPERATURES = List.ofAll(
            //Datum:    01. 02. 03. 04. 05. 06. 07. 08. 09. 10. 11. 12. 13. 14. 15. 16. 17. 18. 19. 20. 21. 22. 23. 24. 25. 26. 27. 28. 29. 30.
            /* °C */    24, 28, 26, 21, 23, 20, 16, 23, 23, 23, 28, 19, 20, 24, 30, 18, 18, 26, 30, 29, 28, 35, 25, 21, 21, 22, 22, 21, 19, 21
            //Temperaturänderungen um mindestens 10°C:                          ^^^^^^                      ^^^^^^
    );

    public List<Integer> findExtremeTemperatureChanges(int minimumChange) {
        //
        // Aufgabe: Werdet die Schleife los!
        // Tipp: Schaut euch die .zip() und .drop()-Methoden auf List an!
        //  (Alternativ dazu: .sliding())
        // Aufgabe 2: Liefert nicht nur den Temperaturunterschied, sondern auch das zugehörige Datum zurück.
        //
        List<Integer> result = List.empty();
        for (int index = 1; index < TEMPERATURES.size(); index++) {
            int previousTemperature = TEMPERATURES.get(index - 1);
            int currentTemperature = TEMPERATURES.get(index);
            int difference = currentTemperature - previousTemperature;
            if (Math.abs(difference) >= minimumChange) {
                result = result.append(difference);
            }
        }
        return result;
    }

    @Test
    public void testFindExtremeTemperatureChanges() {
        Assert.assertEquals(List.of(-12, -10), findExtremeTemperatureChanges(10));
    }
}