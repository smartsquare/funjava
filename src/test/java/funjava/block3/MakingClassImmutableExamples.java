package funjava.block3;

import funjava.beans.AddressBean;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MakingClassImmutableExamples {

    public static class MutablePerson {
        private String name;
        private Set<AddressBean> addresses;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<AddressBean> getAddresses() {
            return addresses;
        }

        public void setAddresses(Set<AddressBean> addresses) {
            this.addresses = addresses;
        }
    }

    public static class PersonWithFinalFields {
        private final String name;
        private final Set<AddressBean> addresses;

        public PersonWithFinalFields(String name, Set<AddressBean> addresses) {
            this.name = name;
            this.addresses = addresses;
        }

        public String getName() {
            return name;
        }

        public Set<AddressBean> getAddresses() {
            return addresses;
        }
    }

    @Test
    public void modifyPersonWithFinalFields() {
        HashSet<AddressBean> addresses = new HashSet<AddressBean>();
        PersonWithFinalFields person = new PersonWithFinalFields("Bob", addresses);
        Assert.assertTrue(person.getAddresses().size() == 0);

        addresses.add(new AddressBean());

        Assert.assertTrue(person.getAddresses().size() == 1);
    }

    public static final class PersonWithDefensiveCopies {
        private final String name;
        private final Set<AddressBean> addresses;

        public PersonWithDefensiveCopies(String name, Set<AddressBean> addresses) {
            this.name = name;
            this.addresses = Collections.unmodifiableSet(addresses);
        }

        public String getName() {
            return name;
        }

        public Set<AddressBean> getAddresses() {
            return Collections.unmodifiableSet(addresses);
        }
    }

}
