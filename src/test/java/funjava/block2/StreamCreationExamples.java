package funjava.block2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Enthält verschiedene Beispiele dazu wie man Streams erzeugen kann.
 */
public class StreamCreationExamples {
    /**
     * Erzeugung eines Streams aus einer festkodierten Liste von Werten.
     */
    @Test
    public void streamOfExample() {
        int largestInteger = Stream.of(17, 42, 23)
                .max(Integer::compareTo)
                .get();

        Assert.assertEquals(42, largestInteger);
    }

    /**
     * Erzeugung eines IntStreams aus einer festen Liste von Werten.
     * IntStreams haben einige Komfortmethoden wie .max() oder .getAsInt() die in allgemeinen Stream&lt;&gt;s nicht möglich sind.
     */
    @Test
    public void intStreamOfExample() {
        int largestInteger = IntStream.of(17, 42, 23)
                .max()
                .getAsInt();

        Assert.assertEquals(42, largestInteger);
    }

    /**
     * Erzeugung eines IntStreams aus allen Ganzzahlen in einem vorgegebenen Intervall.
     */
    @Test
    public void intStreamRangeExample() {
        IntStream.rangeClosed(1, 10)
                .forEachOrdered(
                        System.out::println
                );
    }

    /**
     * Erzeugung eines Streams aus dem Inhalt einer Collection.
     */
    @Test
    public void collectionStreamExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        int firstNumber = numbers.stream()
                .findFirst()
                .get();

        Assert.assertEquals(1, firstNumber);
    }

    @Test
    public void streamFromIterable() {
        Iterable<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = StreamSupport.stream(numbers.spliterator(), false);
    }
}
