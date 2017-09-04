package funjava.block2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperationExamples {
    /**
     * filter(): Führt den Rest des Streams nur für genau diejenigen Elemente aus die eine Eigenschaft erfüllen.
     * <p>
     * Hier: Filtert so dass nur gerade Zahlen verbleiben.
     */
    @Test
    public void filterExample() {
        int firstEvenNumber = IntStream
                .rangeClosed(1, 10)
                .filter(number -> number % 2 == 0)
                .findFirst()
                .getAsInt();

        Assert.assertEquals(2, firstEvenNumber);
    }

    /**
     * peek(): Führt für jedes Element eines Streams eine Methode aus und wendet danach den Rest des Streams auf das
     * Element an.
     * <p>
     * Hier: Gibt Zahlen auf System.out aus.
     */
    @Test
    public void peekExample() {
        int[] allNumbers = IntStream.rangeClosed(1, 5)
                .peek(System.out::println)
                .toArray();

        Assert.assertEquals(1, allNumbers[0]);
        Assert.assertEquals(2, allNumbers[1]);
        Assert.assertEquals(3, allNumbers[2]);
        Assert.assertEquals(4, allNumbers[3]);
        Assert.assertEquals(5, allNumbers[4]);
    }

    /**
     * parallel(): Verteile die darauf folgenden Stream-operationen auf einen Threadpool zur parallelen Ausführung.
     */
    @Test
    public void parallelExample() {
        int[] ignoredResult = IntStream.rangeClosed(1, 5)
                .parallel()
                .peek(i -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]: " + i);
                })
                .toArray();
    }

    /**
     * map(): Wendet auf jedes Element eines Streams eine Methode aus, und wendet danach den Rest des Streams auf den
     * Rückgabewert der Methode an.
     * <p>
     * Hier: Berechnet die Quadrate der Zahlen von 1 bis 5.
     */
    @Test
    public void mapExample() {
        int[] allSquares = IntStream.rangeClosed(1, 5)
                .map(i -> i * i)
                .toArray();

        Assert.assertEquals(1 * 1, allSquares[0]);
        Assert.assertEquals(2 * 2, allSquares[1]);
        Assert.assertEquals(3 * 3, allSquares[2]);
        Assert.assertEquals(4 * 4, allSquares[3]);
        Assert.assertEquals(5 * 5, allSquares[4]);
    }

    /**
     * sorted(): Sortiert die Elemente eines Streams anhand eines Comparators und wendet den Rest des Streams auf
     * die sortierten Elemente an.
     * <p>
     * Hier: Sortiere die Zahlen 5, 3, 167, -3;
     */
    @Test
    public void sortExample() {
        List<Integer> sortedNumbers = Stream.of(5, 3, 167, -3)
                .sorted(Integer::compare)
                .collect(Collectors.toList());

        Assert.assertEquals(Arrays.asList(-3, 3, 5, 167), sortedNumbers);
    }

    /**
     * distinct(): Entfernt alle Duplikate aus einem Stream (anhand von equals())
     */
    @Test
    public void distinctExample() {
        List<Integer> distinctNumbers = Stream.of(5, 3, 167, -3, 5)
                .distinct()
                .collect(Collectors.toList());

        Assert.assertEquals(Arrays.asList(5, 3, 167, -3), distinctNumbers);
    }

    /**
     * limit(): Verwendet nur die ersten X Elemente des Streams für weitere Verarbeitung.
     */
    @Test
    public void limitExample() {
        int[] numbers = IntStream.rangeClosed(1, 10)
                .limit(3)
                .toArray();

        Assert.assertEquals(3, numbers.length);
        Assert.assertEquals(1, numbers[0]);
        Assert.assertEquals(2, numbers[1]);
        Assert.assertEquals(3, numbers[2]);
    }

    /**
     * skip(): Entferne die ersten X Elemente des Streams für weitere Verarbeitung
     */
    @Test
    public void skipExample() {
        int[] numbers = IntStream.rangeClosed(1, 10)
                .skip(7)
                .toArray();

        Assert.assertEquals(3, numbers.length);
        Assert.assertEquals(8, numbers[0]);
        Assert.assertEquals(9, numbers[1]);
        Assert.assertEquals(10, numbers[2]);
    }
}
