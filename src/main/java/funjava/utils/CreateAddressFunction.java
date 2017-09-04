package funjava.utils;

@FunctionalInterface
public interface CreateAddressFunction<A> {

    A create(String street, String city);
}
