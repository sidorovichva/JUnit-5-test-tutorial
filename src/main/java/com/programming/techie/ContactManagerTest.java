package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //this way @BeforeAll and AfterAll shouldn't be static
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        System.out.println("Should Print Before Each Test Method");
        contactManager = new ContactManager();
    }

    @Test
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
            .anyMatch(contact -> contact.getFirstName().equals("John") && contact.getLastName().equals("Doe")));
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @DisplayName("Repeat Contact creation test 5 Times")
    //@RepeatedTest(value = 5, name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}") //just fancier
    @RepeatedTest(5)
    public void shouldTestContactCreationFiveTimes() {
        contactManager.addContact("John", "Row", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Value Source - Repeat Contact creation test 5 Times")
    @ParameterizedTest
    @ValueSource(strings = {"0453455434", "0453452345", "0342238765"})
    public void shouldTestContactCreationValueSource(String phoneNumber) {
        contactManager.addContact("John", "Row", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Method Source - Repeat Contact creation test 5 Times")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Row", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0453455434", "0453452345", "0342238765");
    }

    @DisplayName("Cvs source - Repeat Contact creation test 5 Times")
    @ParameterizedTest
    @CsvSource({"0453455434", "0453452345", "0342238765"}) //CSV stands for Comma Separated Values
    //@CsvFileSource(resources = "/data.csv") //From file located in resource folder. data.csv for instance
    public void shouldTestContactCreationCsvSource(String phoneNumber) {
        contactManager.addContact("John", "Row", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test Method");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed at the end of the Test");
    }

}