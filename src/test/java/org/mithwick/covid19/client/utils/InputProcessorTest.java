package org.mithwick.covid19.client.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mithwick.covid19.client.models.request.InputChoice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputProcessorTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void displayMainMenu_printMessages() {
        InputProcessor inputProcessor = new InputProcessor(new Scanner(System.in));

        inputProcessor.displayMainMenu();

        assertTrue(outputStreamCaptor.toString().trim().contains("Welcome to Covid-19-API Java Client"));
        assertTrue(outputStreamCaptor.toString().trim().contains("Press 1 to enter country name"));
        assertTrue(outputStreamCaptor.toString().trim().contains("Press 0 to exit the program"));
    }

    @Test
    public void getMainMenuUserChoice_validInput_validInputChoice() {
        String input = "1";
        InputProcessor inputProcessor = new InputProcessor(new Scanner(input));

        InputChoice mainMenuUserChoice = inputProcessor.getMainMenuUserChoice();

        assertTrue(outputStreamCaptor.toString().trim().contains("Please enter your choice:"));
        assertEquals(InputChoice.ENTER_COUNTRY_NAME, mainMenuUserChoice);
    }

    @Test
    public void getMainMenuUserChoice_invalidInput_invalidInputChoice() {
        String input = "aefaegfegf";
        InputProcessor inputProcessor = new InputProcessor(new Scanner(input));

        InputChoice mainMenuUserChoice = inputProcessor.getMainMenuUserChoice();

        assertTrue(outputStreamCaptor.toString().trim().contains("Please enter your choice:"));
        assertEquals(InputChoice.INVALID, mainMenuUserChoice);
    }

    @Test
    public void getCountryName_enterCountryNameChoice_readInput() {
        String expectedInput = "Sri Lanka";
        InputProcessor inputProcessor = new InputProcessor(new Scanner(expectedInput));

        String countryName = inputProcessor.getCountryName();

        assertTrue(outputStreamCaptor.toString().trim().contains("Please enter ISO 3166-1 alpha-2 compliant country name:"));
        assertEquals(expectedInput, countryName);
    }

    @Test
    public void getCountryName_enterCountryNameChoice_readInputTrimmed() {
        String input = "  Sri Lanka  ";
        String expectedOutput = input.trim();
        InputProcessor inputProcessor = new InputProcessor(new Scanner(input));

        String countryName = inputProcessor.getCountryName();

        assertTrue(outputStreamCaptor.toString().trim().contains("Please enter ISO 3166-1 alpha-2 compliant country name:"));
        assertEquals(expectedOutput, countryName);
    }
}