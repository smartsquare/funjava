package funjava.block2;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTerminalOperationExamples {

    /**
     * collect(): Erzeugt eine Collection mit allen im Stream enthaltenen Elementen.
     * <p>
     * Hier: Erzeugte eine java.util.List.
     */
    @Test
    public void collectToListExample() {
        List<Integer> numbers = Stream.of(1, 2, 3, 3)
                .collect(Collectors.toList());

        Assert.assertEquals(numbers, Arrays.asList(1, 2, 3, 3));
    }

    /**
     * collect(): Erzeugt eine Collection mit allen im Stream enthaltenen Elementen.
     * <p>
     * Hier: Erzeugte ein java.util.Set.
     */
    @Test
    public void collectToSetExample() {
        Set<Integer> numbers = Stream.of(1, 2, 3, 3)
                .collect(Collectors.toSet());

        Assert.assertEquals(3, numbers.size());
        Assert.assertTrue(numbers.contains(1));
        Assert.assertTrue(numbers.contains(2));
        Assert.assertTrue(numbers.contains(3));
    }

    /**
     * collect(): Erzeugt eine Collection mit allen im Stream enthaltenen Elementen.
     * <p>
     * Hier: Erzeugte ein java.util.Map die von Zahlen auf ihre Quadrate abbildet.
     */
    @Test
    public void collectToMapExample() {
        Map<Integer, Integer> numbersToSquares = Stream.of(1, 2, 3)
                .collect(Collectors.toMap(
                        i -> i,
                        i -> i * i
                ));

        Assert.assertEquals(3, numbersToSquares.size());
        Assert.assertEquals(1 * 1, numbersToSquares.get(1).intValue());
        Assert.assertEquals(2 * 2, numbersToSquares.get(2).intValue());
        Assert.assertEquals(3 * 3, numbersToSquares.get(3).intValue());
    }

    /**
     * reduce(): Wende eine Funktion auf Paare von Elementen im Stream an, um ein einzelnes Ergebnis zu erhalten.
     * <p>
     * Hier: Berechne das Produkt der Zahlen von 1 bis 5
     */
    @Test
    public void reduceExample() {
        int factorial = IntStream.rangeClosed(1, 5)
                .reduce(1, (a, b) -> a * b);

        Assert.assertEquals(1 * 2 * 3 * 4 * 5, factorial);
    }

    /**
     * findFirst(): Liefere das erste Element des Streams zurück - oder Optional.empty() falls der Stream leer ist.
     */
    @Test
    public void findFirstExample() {
        OptionalInt first = IntStream.rangeClosed(67, 89)
                .findFirst();

        Assert.assertTrue(first.isPresent());
        Assert.assertEquals(67, first.getAsInt());
    }

    /**
     * findAny(): Liefere irgendein Element des Streams zurück - oder Optional.empty falls der Stream leer ist.
     *
     * Kann vor allem bei parallelen Streams ein anderes Ergebnis liefern als findFirst()
     */
    @Test
    public void findAnyExample() {
        OptionalInt any = IntStream.rangeClosed(67, 89)
                .parallel()
                .findAny();

        //Hier vielleicht mal im Debugger anschauen welchen Wert any hat - bei mir war es 81.
        Assert.assertTrue(any.isPresent());
    }

    /**
     * forEach(): Führe eine Funktion für jedes Element des Streams aus und beende danach die Verarbeitung.
     *
     * Bei parallelen Streams auf den Unterschied zwischen forEach und forEachOrdered achten!
     *
     * Hier: Gib die Zahlen von 1 bis 5 in beliebiger Reihenfolge aus.
     */
    @Test
    public void forEachExample() {
        IntStream.rangeClosed(1, 5)
                .parallel()
                .forEach(System.out::println);
    }
}
