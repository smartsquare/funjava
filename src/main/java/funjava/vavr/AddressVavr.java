package funjava.vavr;


import org.immutables.value.Value;

@Value.Immutable
public interface AddressVavr {

    String getStreet();

    String getCity();

}
