package com.sarny.spocone.server.languages_support;

import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Wojciech Makiela
 */
public class TranslationControllerTest {

    @Test
    public void testGetTranslationForCode_whenPassedCodeForNotSupportedLanguage_returnNullWith400StatusCode() {
        // arrange
        SupportedLanguages supportedLanguages = new SupportedLanguages();
        TranslationController controller = new TranslationController(new TranslationProvider(supportedLanguages), supportedLanguages);

        // act
        ResponseEntity<Map<String, String>> response = controller.getTranslationForCode("someNotSupportedLanguage");
        // assert
        assertEquals(response.getStatusCodeValue(), 400);
    }

    @Test
    public void testGetAllSupportedLanguages_whenSupportedLanguagesProvided_returnCodesWithTheirFullNames() {
        // arrange
        SupportedLanguages supportedLanguages = new SupportedLanguages();
        TranslationController controller = new TranslationController(new TranslationProvider(supportedLanguages), supportedLanguages);

        // act
        ResponseEntity<Map<String, String>> response = controller.getAllSupportedTranslations();
        String responseAsString = response.toString();

        // assert
        for (String language : supportedLanguages.codes()) {
            assertTrue(responseAsString.contains(language));
        }
    }
}