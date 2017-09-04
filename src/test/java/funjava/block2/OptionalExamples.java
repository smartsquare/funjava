package funjava.block2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

public class OptionalExamples {
    @Test
    public void createOptional() {
        Optional<String> greeting = Optional.of("Hello");
        Optional<String> empty = Optional.empty();
        Optional<String> alsoEmpty = Optional.ofNullable(null);
    }

    @Test(expected = NullPointerException.class)
    public void optionalOfNull() {
        Optional.of(null);
    }

    @Test
    public void isPresentExample() {
        Optional<String> greeting = Optional.of("Hello");
        if (greeting.isPresent()) {
            System.out.println("Present: " + greeting.get());
        }
    }

    /**
     * ifPresent(): Falls das Optional einen Wert hat, rufe eine Methode mit dem Wert als Parameter auf.
     */
    @Test
    public void ifPresentExample() {
        Optional<String> hello = Optional.of("Hello");
        Optional<String> empty = Optional.empty();

        hello.ifPresent(string -> {
            System.out.println("'hello' Present: " + string);
        });

        empty.ifPresent(string -> {
            System.out.println("'empty' Present: " + string);
        });
    }

    /**
     * orElse: Falls das Optional einen Wert hat, liefere ihn zurück. Andernfalls liefere den Parameter von orElse zurück.
     */
    @Test
    public void orElseExample() {
        String helloOrElseBye = Optional.of("hello").orElse("bye");
        Assert.assertEquals(helloOrElseBye, "hello");

        String emptyOrElseBye = Optional.<String>empty().orElse("bye");
        Assert.assertEquals(emptyOrElseBye, "bye");
    }

    /**
     * orElseGet: Falls das Optional einen Wert hat, liefere ihn zurück.
     * Andernfalls rufe eine Methode auf, um den Rückgabewert zu bestimmen.
     *
     * Alternative zu orElse, wenn die Ermittlung des Defaultwertes aufwendig ist oder Seiteneffekte hat, zum Beispiel Datenbankabfrage, SOAP Request, ...
     */
    @Test
    public void orElseGetExample() {
        String helloOrElseBye = Optional.of("hello").orElseGet(() -> System.getProperty("java.vendor"));
        Assert.assertEquals(helloOrElseBye, "hello");

        String emptyOrElseBye = Optional.<String>empty().orElseGet(() -> System.getProperty("java.vendor"));
        Assert.assertEquals(emptyOrElseBye, System.getProperty("java.vendor"));
    }

    /**
     * Map: Falls das Optional einen Wert hat, rufe eine Methode mit dem Wert als Parameter auf und erstelle ein neues Optional aus dem Rückgabewert.
     * Ansonsten liefere Optional#empty() zurück.
     */
    @Test
    public void mapExample() {
        Optional<String> greeting = Optional.of("Hello");
        Optional<Integer> helloLength = greeting.map(String::length);
        Assert.assertTrue(helloLength.isPresent());
        Assert.assertEquals(5, helloLength.get().intValue());

        Optional<Integer> emptyLength = Optional.<String>empty().map(String::length);
        Assert.assertTrue(!emptyLength.isPresent());
    }

    @Test
    public void mapWithFunctionReturningNull() {
        Optional<String> greeting = Optional.of("Hello");
        Optional<String> result = greeting.map(value -> (String)null);
        Assert.assertEquals(Optional.empty(), result);
    }

    /**
     * Filter: Falls das Optional einen Wert enthält <em>und</em> das übergebene Prädikat für den Wert true zurückgibt, liefere das Optional unverändert zurück.
     * Ansonsten liefere Optional#empty() zurück.
     *
     *
     */
    @Test
    public void filterExample() {
        Optional<String> hello = Optional.of("Hello");
        Optional<String> greetingOnlyIfLongerThan10 = hello.filter(s -> s.length() > 10);
        Assert.assertFalse(greetingOnlyIfLongerThan10.isPresent());

        Optional<String> greeingOnlyIfStartsWithHell = hello.filter(s -> s.startsWith("Hell"));
        Assert.assertTrue(greeingOnlyIfStartsWithHell.isPresent());

        Optional<String> empty = Optional.empty();
        Assert.assertFalse(empty.filter(s -> s.startsWith("Hell")).isPresent());
    }


}
