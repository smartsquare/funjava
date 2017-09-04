package funjava.block5;

import io.vavr.control.Option;
import org.junit.Test;
import static io.vavr.API.*;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

public class OptionExamples {
    @Test
    public void createOption() {
        Option<String> greeting = Some("hello");
        Option<String> greeting2 = Option.of("hello");
        Option<String> empty = None();

        Assert.assertFalse(greeting.isEmpty());
        Assert.assertTrue(greeting.isDefined());

        Assert.assertTrue(empty.isEmpty());
        Assert.assertFalse(empty.isDefined());

        Assert.assertEquals(greeting, greeting2);
    }

    @Test
    public void accessOption() {
        Option<String> greeting = Some("hello");
        Option<String> empty = None();

        greeting.forEach(System.out::println);
        greeting.onEmpty(() -> {throw new RuntimeException("Greeting may not be empty!");});

        for (String item: greeting) {
            System.out.println(item);
        }

        System.out.println(greeting.get());

        System.out.println(empty.getOrElse("Aloha"));
    }

    @Test
    public void optionToStream() {
        Option.of("Hello").toStream();
        //TODO
    }

    /**
     * Demonstriert das Verhalten von Java-Optionals, wenn ein Aufruf von map() zu einem null-Wert führt.
     */
    @Test
    public void jdkOptionalNullHandling() {
        Optional<String> value = Optional.of("Hello");
        Optional<Object> mapResult = value.map(v -> null);

        Assert.assertFalse(mapResult.isPresent());
        Assert.assertEquals(Optional.empty(), mapResult);
    }

    /**
     * Demonstriert das Verhalten von VAVR-Options, wenn ein Aufruf von map() zu einem null-Wert führt.
     */
    @Test
    public void vavrOptionNullHandling() {
        Option<String> value = Option.of("Hello");

        Option<Object> mapResult = value.map(v -> null);
        Assert.assertFalse(mapResult.isEmpty());
        Assert.assertEquals(mapResult.get(), null);

        Option<Object> flatMapResult = value.flatMap(v -> Option.of(null));
        Assert.assertTrue(flatMapResult.isEmpty());
    }
}
