package funjava.javaimmutables;

import java.util.Objects;

public class AddressJavaImmutable {

    private final String street;

    private final String city;

    public AddressJavaImmutable(String street, String city) {
        this.street = street;
        this.city = city;
    }

    //<editor-fold desc="Getter, equals, toString">
    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressJavaImmutable that = (AddressJavaImmutable) o;
        return Objects.equals(street, that.street) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city);
    }

    @Override
    public String toString() {
        return "AddressJavaImmutable{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
    //</editor-fold>
}
