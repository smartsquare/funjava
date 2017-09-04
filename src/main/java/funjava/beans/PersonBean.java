package funjava.beans;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class PersonBean {

    private String name;

    private LocalDate birthdate;

    private Set<AddressBean> addresses;

    private String nationality;

    //<editor-fold desc="Getter, Setter, etc.">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Set<AddressBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressBean> addresses) {
        this.addresses = addresses;
    }

    public Optional<String> getNationality() {
        return Optional.ofNullable(nationality);
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", addresses=" + addresses +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonBean that = (PersonBean) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(addresses, that.addresses) &&
                Objects.equals(nationality, that.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthdate, addresses, nationality);
    }
//</editor-fold>
}
