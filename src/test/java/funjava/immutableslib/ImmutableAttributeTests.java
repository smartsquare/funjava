package funjava.immutableslib;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

public class ImmutableAttributeTests {

    private static ILibData DATA = new ILibData();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void attributesMustBeSetIfNotOptional(){
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("[birthdate]");
        AddressValue address = AddressValue.of("street", "city");
        PersonValue missingBirthdatePerson = PersonValue.builder()
                .name("name")
                .addAddress(address)
                .nationality("DE")
                .build();
    }

    @Test
    public void optionalAttributesAreOptional(){
        AddressValue address = AddressValue.of("street", "city");
        PersonValue personWithoutNationality = PersonValue.builder()
                .name("name")
                .birthdate(LocalDate.of(1990, 12, 31))
                .addAddress(address)
                .build();

        assertEquals(Optional.empty(), personWithoutNationality.getNationality());
    }

    @Test
    public void defaultAttributShouldBeAbleToBeSet(){
        AddressValue addressWithDefaultCountry = AddressValue.of("street", "city");
        AddressValue addressWithCountry = AddressValue.builder()
                .street("streetFR")
                .city("cityFR")
                .country("FR")
                .build();

        assertEquals("DE", addressWithDefaultCountry.getCountry());
        assertEquals("FR", addressWithCountry.getCountry());
    }

    @Test
    public void addressDisplayLabelIsADerivedAttribute(){
        AddressValue address = AddressValue.of("street", "city");
        assertEquals("computed derived", systemOutRule.getLogWithNormalizedLineSeparator());
        systemOutRule.clearLog();
        assertEquals("street in city (DE)", address.displayLabel());
        assertEquals("", systemOutRule.getLogWithNormalizedLineSeparator());
    }


    @Test
    public void lazyAttributesAreOnlyEvaluatedOnce(){
        BankingTransactionILib transaction = DATA.getBankingTransactions().iterator().next();
        for (int i = 0; i< 5; i++){
            transaction.getAddressCount();
        }
        assertEquals("lazy address count!\n", systemOutRule.getLogWithNormalizedLineSeparator());
    }

    @Test
    public void auxiliaryAttributesAreNotPartOfEqualsAndToString(){
        PersonValue runningPerson = PersonValue.builder()
                .name("name")
                .birthdate(LocalDate.of(2000, 12, 31))
                .hobby("running")
                .build();
        PersonValue walkingPerson = PersonValue.builder()
                .name("name")
                .birthdate(LocalDate.of(2000, 12, 31))
                .hobby("walking")
                .build();

        assertFalse("hobby should not be part of toString()",
                runningPerson.toString().contains("running"));
        assertTrue("running and walking person should be equal",
                runningPerson.equals(walkingPerson));

    }

    @Test
    public void birthdateIsARedactedAttribute(){
        PersonValue person = PersonValue.builder()
                .name("name")
                .birthdate(LocalDate.of(2000, 12, 31))
                .build();

        assertFalse("birtdate should not be part of toString()", person.toString().contains("2000-12-31"));
        assertTrue("mask shouldbe part of toString()", person.toString().contains("####"));
    }





}
