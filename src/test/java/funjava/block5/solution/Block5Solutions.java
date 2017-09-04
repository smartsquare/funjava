package funjava.block5.solution;

import funjava.vavr.BankingTransactionVavr;
import funjava.vavr.PersonVavr;
import funjava.vavr.VavrData;
import io.vavr.*;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.Assert;
import org.junit.Test;


public class Block5Solutions {
    /* Zu den Lösungen bitte runter scrollen.


























































    */
    private final VavrData DATA = new VavrData();

    /**
     * @return true, falls es eine Überweisung von einem Konto von Person1 zu einem Konto von Person2 oder umgekehrt gab. False andernfalls.
     */
    public boolean hasInteractedWith(PersonVavr person1, PersonVavr person2) {
        //Funktion die für eine Transaktion tx und zwei personen p1 und p2 prüft, ob die Transaktion eine Überweisung von p1 nach p2 ist.
        Function3<BankingTransactionVavr, PersonVavr, PersonVavr, Boolean>
                isTransferFromTo = (tx, p1, p2) -> tx.getFromAccount().getOwner().equals(p1) && tx.getToAccount().getOwner().equals(p2);

        //Funktion, die für eine Transaktino tx und zwei personen p1 und p2 prüft, ob die Transaktion zwischen p1 und p2 stattfindet
        // (in irgendeine Richtung)
        Function3<BankingTransactionVavr, PersonVavr, PersonVavr, Boolean> interactionBetween =
                (tx, p1, p2) -> isTransferFromTo.apply(tx, p1, p2) || isTransferFromTo.apply(tx, p2, p1);

        return DATA.getBankingTransactions()
                .exists(
                        tx -> interactionBetween.apply(tx, person1, person2)
                );
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

        Function1<PersonVavr, Boolean> hasInteractedWithBobFunction = Function2
                .of(this::hasInteractedWith)
                .apply(bob);

        Assert.assertFalse(hasInteractedWithBobFunction.apply(bob));
        Assert.assertFalse(hasInteractedWithBobFunction.apply(eve));
        Assert.assertTrue(hasInteractedWithBobFunction.apply(alice));
    }


    //Temperaturen in Grad Celsius in Bielefeld im Juni 2017, Laut https://www.accuweather.com/de/de/bielefeld/33602/june-weather/170367?monyr=6/1/2017&view=table
    public static final List<Integer> TEMPERATURES = List.ofAll(
            //Datum:    01. 02. 03. 04. 05. 06. 07. 08. 09. 10. 11. 12. 13. 14. 15. 16. 17. 18. 19. 20. 21. 22. 23. 24. 25. 26. 27. 28. 29. 30.
            /* °C */    24, 28, 26, 21, 23, 20, 16, 23, 23, 23, 28, 19, 20, 24, 30, 18, 18, 26, 30, 29, 28, 35, 25, 21, 21, 22, 22, 21, 19, 21
            //Temperaturänderungen um mindestens 10°C:                          ^^^^^^                      ^^^^^^
    );

    //Variante 1: zip + drop
    public List<Integer> findExtremeTemperatureChanges(int minimumChange) {

        //Erzeugt eine Liste von Tuplen mit jeweils der Temperatur eines Tages und der des Folgetages:
        // [ (24, 28), (28, 26), ..., (19, 21) ]
        List<Tuple2<Integer, Integer>> consecutiveTemperaturePairs =
                TEMPERATURES.zip(TEMPERATURES.drop(1));

        //Temperaturunterschiede zwischen aufeinander folgenden Tagen:
        //[ 4, -2, ..., 2 ]
        List<Integer> temperatureDifferences = consecutiveTemperaturePairs.map(pair -> pair._2 - pair._1);

        return temperatureDifferences.filter(d -> Math.abs(d) >= minimumChange);
    }

    //Variante: sliding anstelle von zip() und drop()
    public List<Integer> findExtremeTemperatureChangesWithSliding(int minimumChange) {
        Iterator<List<Integer>> consecutiveTemperaturePairs = TEMPERATURES.sliding(2);

        Iterator<Integer> temperatureDifferences = consecutiveTemperaturePairs.map(pair -> pair.get(1) - pair.get(0));

        return temperatureDifferences.filter(d -> Math.abs(d) >= minimumChange).toList();
    }

    @Test
    public void testFindExtremeTemperatureChanges() {
        Assert.assertEquals(List.of(-12, -10), findExtremeTemperatureChanges(10));
    }


    //Aufgabe 2: zip() und drop(), liefert zusätzlich das Datum der Änderung zurück.
    public List<Tuple2<Integer, Integer>> findExtremeTemperatureChangesWithDate(int minimumChange) {
        //Zahlen von 1 bis 30:
        //[1, 2, ..., 30]
        List<Integer> dates = List.rangeClosed(1, 30);

        //Paare von Temperaturen:
        // [ (24, 28), (28, 26), ..., (19, 21) ]
        List<Tuple2<Integer, Integer>> consecutiveTemperaturePairs = TEMPERATURES.zip(TEMPERATURES.drop(1));

        //Temperaturpaare, jeweils zusamen mit dem Datum des ersten Tages aus dem Paar:
        // [ (1, 24, 28), (2, 28, 26), ... (30, 19, 21) ]
        List<Tuple3<Integer, Integer, Integer>> datesAndTemperatures =
                consecutiveTemperaturePairs.zipWith(dates,
                        (temperatures, date) -> Tuple.of(date, temperatures._1, temperatures._2));

        //Temperaturunterschiede, jeweils zusammen mit dem Datum des ersten Tages für den der Unterschied bestimmt wurde:
        List<Tuple2<Integer, Integer>> datesAndTemperaturDifferences =
                datesAndTemperatures.map(dateAndTemperatures -> Tuple.of(dateAndTemperatures._1, dateAndTemperatures._3 - dateAndTemperatures._2));

        return datesAndTemperaturDifferences.filter(dateAndDiff -> Math.abs(dateAndDiff._2) >= minimumChange);
    }


    //Identisch zu findExtremeTemperatureChangesWithDate, aber ohne die Zwischenvariablen und Kommentare:
    public List<Tuple2<Integer, Integer>> findExtremeTemperatureChangesWithDateNoVariables(int minimumChange) {
        return TEMPERATURES.zip(TEMPERATURES.drop(1))
                .zipWith(List.rangeClosed(1, 30),
                        (temperatures, date) -> Tuple.of(date, temperatures._1, temperatures._2))
                .map(dateAndTemperatures -> Tuple.of(dateAndTemperatures._1, dateAndTemperatures._3 - dateAndTemperatures._2))
                .filter(dateAndDiff -> Math.abs(dateAndDiff._2) >= minimumChange);
    }

    @Test
    public void testFindExtremeTemperatureChangesWithDate() {
        Assert.assertEquals(List.of(
                Tuple.of(15, -12), /* Änderung um -12 Grad vom 15. auf den 16. */
                Tuple.of(22, -10)  /* Änderung um -10 Grad vom 22. auf den 23. */
        ), findExtremeTemperatureChangesWithDate(10));
    }


}