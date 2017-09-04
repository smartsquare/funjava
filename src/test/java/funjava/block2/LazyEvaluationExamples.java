package funjava.block2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LazyEvaluationExamples {

    @Test
    public void streamGenerateExample() {
        Random random = new Random();

        Stream.generate(random::nextDouble)
                .mapToDouble(Double::doubleValue)
                .limit(10)
                .average()
                .getAsDouble();
    }

    @Test
    public void lazyEvaluationDemo() {
        AtomicInteger numberOfElementsEvaluated = new AtomicInteger(0);

        int[] firstFiveNumbers = IntStream.rangeClosed(0, 10)
                .peek(i -> {
                    System.out.println("map evaluiert für Zahl " + i);
                    numberOfElementsEvaluated.incrementAndGet();
                })
                .limit(5)
                .toArray();

        Assert.assertEquals(5, numberOfElementsEvaluated.get());
    }

}
