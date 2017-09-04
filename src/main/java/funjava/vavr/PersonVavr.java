package funjava.vavr;


import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.immutables.value.Value;
import org.immutables.vavr.encodings.VavrEncodingEnabled;

import java.time.LocalDate;

@Value.Immutable
@VavrEncodingEnabled
public interface PersonVavr {

    String getName();

    LocalDate getBirthdate();

    Set<AddressVavr> getAddresses();

    Option<String> getNationality();

}
