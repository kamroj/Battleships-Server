package com.sarny.spocone.server.gameControllers;

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

    @Autowired
    public TranslationController(TranslationProvider translationProvider) {
        this.translationProvider = translationProvider;
    }

    @GetMapping("/language/{code}")
    ResponseEntity<Map<String, String>> isPlayersTurn(@PathVariable String code) {
        TranslationProvider.Translation translation = translationProvider.getTranslation(code);
        return translation != null ?
                new ResponseEntity<>(translation.translation, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
