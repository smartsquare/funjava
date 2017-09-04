package funjava.block5;

import io.vavr.Lazy;
import io.vavr.control.Try;
import org.junit.Test;

public class LazyExamples {
    @Test
    public void lazy() {
        Lazy<String> greeting = Lazy.of(this::expensiveOperation);

        System.out.println("Using lazy value for the first time...");
        System.out.println(greeting.get());
        System.out.println("Using lazy value for the second time...");
        System.out.println(greeting.get());
    }


    @Test
    public void lazyValue() {
        CharSequence greeting = Lazy.val(this::expensiveOperation, CharSequence.class);
        System.out.println("Using lazy value for the first time...");
        System.out.println(greeting);
        System.out.println("Using lazy value for the second time...");
        System.out.println(greeting);
    }

    private String expensiveOperation() {
        Try.run( () -> Thread.sleep(1000));
        return "Hello, World";
    }
}
