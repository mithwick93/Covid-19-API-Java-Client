package org.mithwick.covid19.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mithwick.covid19.client.models.Covid19Information;
import org.mithwick.covid19.client.models.request.InputChoice;
import org.mithwick.covid19.client.services.Covid19APIClientService;
import org.mithwick.covid19.client.utils.InputProcessor;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
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
    public void run_exitChoice_exitApplication() {
        InputProcessor mockInputProcessor = Mockito.mock(InputProcessor.class);
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);

        Mockito.when(mockInputProcessor.getMainMenuUserChoice()).thenReturn(InputChoice.EXIT);

        Main main = new Main();
        main.run(mockInputProcessor, mockCovid19APIClientService);

        Mockito.verify(mockInputProcessor, Mockito.times(1)).displayMainMenu();
        Mockito.verify(mockInputProcessor, Mockito.times(1)).getMainMenuUserChoice();

        assertTrue(outputStreamCaptor.toString().trim().contains("Exiting program."));
    }

    @Test
    public void run_invalidChoice_showErrorMessage() {
        InputProcessor mockInputProcessor = Mockito.mock(InputProcessor.class);
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);

        Mockito.when(mockInputProcessor.getMainMenuUserChoice()).thenReturn(InputChoice.INVALID).thenReturn(InputChoice.EXIT);

        Main main = new Main();
        main.run(mockInputProcessor, mockCovid19APIClientService);

        Mockito.verify(mockInputProcessor, Mockito.times(1)).displayMainMenu();
        Mockito.verify(mockInputProcessor, Mockito.times(2)).getMainMenuUserChoice();

        assertTrue(outputStreamCaptor.toString().trim().contains("Invalid input. Please try again"));
    }

    @Test
    public void run_enterCountryChoice_prettyPrintInformation() {
        String country = "Sri Lanka";
        InputProcessor mockInputProcessor = Mockito.mock(InputProcessor.class);
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);
        Covid19Information covid19Information = Mockito.mock(Covid19Information.class);

        Mockito.when(mockInputProcessor.getMainMenuUserChoice()).thenReturn(InputChoice.ENTER_COUNTRY_NAME).thenReturn(InputChoice.EXIT);
        Mockito.when(mockInputProcessor.getCountryName()).thenReturn(country);
        Mockito.when(mockCovid19APIClientService.getCovid19Information(country)).thenReturn(covid19Information);

        Main main = new Main();
        main.run(mockInputProcessor, mockCovid19APIClientService);

        Mockito.verify(mockInputProcessor, Mockito.times(1)).displayMainMenu();
        Mockito.verify(mockInputProcessor, Mockito.times(2)).getMainMenuUserChoice();
        Mockito.verify(mockCovid19APIClientService, Mockito.times(1)).getCovid19Information(country);
        Mockito.verify(covid19Information, Mockito.times(1)).prettyPrint();
    }

    @Test
    public void displayCovidInformation_correctCountrySet_prettyPrintInformation() {
        String country = "Sri Lanka";
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);
        Covid19Information covid19Information = Mockito.mock(Covid19Information.class);

        Mockito.when(mockCovid19APIClientService.getCovid19Information(country)).thenReturn(covid19Information);

        new Main().displayCovidInformation(country, mockCovid19APIClientService);

        Mockito.verify(mockCovid19APIClientService, Mockito.times(1)).getCovid19Information(country);
        Mockito.verify(covid19Information, Mockito.times(1)).prettyPrint();
    }

    @Test
    public void displayCovidInformation_nullCountrySet_doNotPrettyPrintInformation() {
        String country = null;
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);

        new Main().displayCovidInformation(country, mockCovid19APIClientService);

        Mockito.verify(mockCovid19APIClientService, Mockito.times(0)).getCovid19Information(country);
    }

    @Test
    public void displayCovidInformation_invalidCountrySet_doNotPrettyPrintInformation() {
        String country = "not a country";
        Covid19APIClientService mockCovid19APIClientService = Mockito.mock(Covid19APIClientService.class);

        new Main().displayCovidInformation(country, mockCovid19APIClientService);

        assertTrue(outputStreamCaptor.toString().trim().contains("Invalid Country Name. Please try again"));
        Mockito.verify(mockCovid19APIClientService, Mockito.times(0)).getCovid19Information(country);
    }
}