package funjava.immutableslib;

import org.immutables.value.Value;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Value.Immutable
@MyStyle
public interface PersonILib {

    @Value.Parameter
    String getName();

    @Value.Parameter
    @Value.Redacted
    LocalDate getBirthdate();

    @Value.Parameter
    Set<AddressILib> getAddresses();

    @Value.Parameter
    Optional<String> getNationality();

    @Value.Auxiliary
    Optional<String> getHobby();

    @Value.Check
    default void check(){
        if (getBirthdate().isAfter(LocalDate.now())){
            throw new IllegalStateException("Future birthdate not allowed!");
        }
    }

}
