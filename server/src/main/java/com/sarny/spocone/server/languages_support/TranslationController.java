package com.sarny.spocone.server.languages_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Wojciech Makiela
 */
@RestController
class TranslationController {

    private TranslationProvider translationProvider;
    private SupportedLanguages supportedLanguages;

    @Autowired
    TranslationController(TranslationProvider translationProvider, SupportedLanguages supportedLanguages) {
        this.translationProvider = translationProvider;
        this.supportedLanguages = supportedLanguages;
    }

    @GetMapping("/language/{code}")
    ResponseEntity<Map<String, String>> getTranslationForCode(@PathVariable String code) {
        TranslationProvider.Translation translation = translationProvider.getTranslation(code.toUpperCase());
        return translation != null ?
                new ResponseEntity<>(translation.asMap, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/language")
    ResponseEntity<Map<String, String>> getAllSupportedTranslations() {
        Map<String, String> translations = supportedLanguages.asMap();
        return translations != null ?
                new ResponseEntity<>(translations, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.valueOf(503)); // 503 - Service Unavailable

    }
}
