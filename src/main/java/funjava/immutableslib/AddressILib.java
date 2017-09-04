package funjava.immutableslib;

import org.immutables.value.Value;

@Value.Immutable // <1>
@MyStyle         // <2>
public interface AddressILib { // <3>

    @Value.Parameter  // <4>
    String getStreet();

    @Value.Parameter
    String getCity();

    @Value.Default
    default String getCountry(){
        return "DE";
    }

    @Value.Derived
    default String displayLabel(){
        System.out.printf("computed derived");
        return String.format("%s in %s (%s)", getStreet(), getCity(), getCountry());
    }

}
