package funjava.block5;

import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static io.vavr.API.*;

public class TupleExamples {
    @Test
    public void createTuple() {
        Tuple2<String, String> keyValue = Tuple("Name", "Bob");
        Tuple3<String, String, LocalDate> person = Tuple("Karl", "Müller", LocalDate.of(1968, 7, 15));
    }

    @Test
    public void accessTupleElements() {
        Tuple3<String, String, LocalDate> person = Tuple("Karl", "Müller", LocalDate.of(1968, 7, 15));
        String firstName = person._1;
        String lastName = person._2();
    }

    @Test
    public void tuplesAsIntermediatesInStreams() {
        List<Integer> numbers = List(23, 17, 5, 42);

        Tuple2<Integer, Integer> initialMinAndMax = Tuple(Integer.MAX_VALUE, Integer.MIN_VALUE);

        Function2<Tuple2<Integer, Integer>, Integer, Tuple2<Integer, Integer>> updateMinAndMax = Function2.of(
                        (minMax, v) -> Tuple(Math.min(minMax._1, v), Math.max(minMax._2, v))
        );

        Tuple2<Integer, Integer> minAndMaxNumber = numbers.toStream()
                .foldLeft(initialMinAndMax, updateMinAndMax);

        Assert.assertEquals(minAndMaxNumber, Tuple(5, 42));
    }
}
