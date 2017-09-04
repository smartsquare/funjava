package funjava.block5;

import io.vavr.*;
import static io.vavr.API.*;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Function;

public class FunctionExamples {

    @Test
    public void functionTypeNames() {
        Function0<Integer> supplier = () -> 42;
        Function1<Integer, Integer> function = (a) -> a + 1;
        Function2<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
        //und so weiter, bis Function8<T1,T2,T3,T4,T5,T6,T7,T8,R>
        CheckedFunction0<Integer> checkedSuppier = () -> {
            throw new IOException("this function always throws a checked exception");
        };
    }

    @Test
    public void apply() {
        Function2<Integer, Integer, Integer> add = (a, b) -> a + b;
        Function1<Integer, Integer> add10 = add.apply(10);

        Assert.assertEquals(Integer.valueOf(13), add10.apply(3));
    }

    @Test
    public void memoize() {
        Function2<Integer, Integer, Integer> add = (a, b) -> {System.out.println("Adding " + a + " and " + b); return a + b;};
        Function2<Integer, Integer, Integer> addMemoized = add.memoized();
        addMemoized.apply(3, 4);
        addMemoized.apply(1, 1);
        addMemoized.apply(3, 4);
    }

    @Test
    public void lift() {
        Function1<String, Option<Integer>> parseInt = Function1.lift(Integer::parseInt);

        Assert.assertEquals(Some(13), parseInt.apply("13"));
        Assert.assertEquals(None(), parseInt.apply("Hello"));
    }

    @Test
    public void tupled() {
        Function2<Integer, Integer, Integer> add = (a, b) -> a + b;
        Function1<Tuple2<Integer, Integer>, Integer> addTupled = add.tupled();

        Assert.assertEquals(add.apply(1,2), addTupled.apply(Tuple(1, 2)));
    }

    @Test
    public void andThen() {
        Function1<Integer, Integer> add1 = a -> a + 1;
        Function1<Integer, Integer> times3= a -> a * 3;
        Function1<Integer, Integer> plus1ThenTimesThree = add1.andThen(times3);

        Assert.assertEquals(Integer.valueOf(9), plus1ThenTimesThree.apply(2));
    }

    @Test
    public void compose() {
        Function1<Integer, Integer> add1 = a -> a + 1;
        Function1<Integer, Integer> times3= a -> a * 3;
        Function1<Integer, Integer> plus1ThenTimesThree = times3.compose(add1);

        Assert.assertEquals(Integer.valueOf(9), plus1ThenTimesThree.apply(2));
    }

    @Test
    public void identityFunction() {
        Function<String, String> identity = Function.identity();

        Assert.assertEquals("hello", identity.apply("hello"));
        Assert.assertEquals("world", identity.apply("world"));
    }

    @Test
    public void constantFunction() {
        Function3<Integer, String, Boolean, String> constant = Function3.constant("Foo");

        Assert.assertEquals("Foo", constant.apply(13, "hello", false));
    }
}
