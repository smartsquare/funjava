package funjava.javaimmutables;

import java.time.LocalDate;
import java.util.*;

public class PersonJavaImmutable {

    private final String name;

    private final LocalDate birthdate;

    private final Set<AddressJavaImmutable> addresses;

    private final String nationality;

    public PersonJavaImmutable(String name, LocalDate birthdate, Set<AddressJavaImmutable> addresses) {
        this(name, birthdate, addresses, null);
    }

    public PersonJavaImmutable(String name, LocalDate birthdate, Set<AddressJavaImmutable> addresses, String nationality) {
        this.name = name;
        this.birthdate = birthdate;
        this.addresses = Collections.unmodifiableSet(addresses);
        this.nationality = nationality;
    }


    //<editor-fold desc="Getter, equals, toString">
    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Set<AddressJavaImmutable> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Optional<String> getNationality() {
        return Optional.ofNullable(nationality);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonJavaImmutable that = (PersonJavaImmutable) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(addresses, that.addresses) &&
                Objects.equals(nationality, that.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthdate, addresses, nationality);
    }

    @Override
    public String toString() {
        return "PersonJavaImmutable{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", addresses=" + addresses +
                ", nationality='" + nationality + '\'' +
                '}';
    }
    //</editor-fold>
}
