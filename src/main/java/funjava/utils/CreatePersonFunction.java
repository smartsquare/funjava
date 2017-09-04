package funjava.utils;

import java.time.LocalDate;
import java.util.Collection;

@FunctionalInterface
public interface CreatePersonFunction<A, P> {

    P create(String name, LocalDate birthdate, Collection<A> addresses, String nationality);
}
