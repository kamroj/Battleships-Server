package com.sarny.spocone.server.gameControllers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Wojciech Makiela
 */
@Component
class TranslationProvider {

    private Map<String, Translation> loadedTranslations;    // Key is language ISO 639-1 code - ect "pl", "en"

    public TranslationProvider() {
        loadedTranslations = new HashMap<>();
        String[] supportedLanguages = {"PL", "EN"}; // Language ISO 639-1 code
        for (String languageCode : supportedLanguages) {
            ResourceBundle bundle = ResourceBundle.getBundle(languageCode);
            loadedTranslations.put(languageCode, new Translation(bundle));
        }
    }

    Translation getTranslation(String code) {
        return loadedTranslations.get(code);
    }

    class Translation {
        Map<String, String> asMap;

        Translation(ResourceBundle bundle) {
            asMap = new HashMap<>();
            bundle.keySet().forEach(key -> asMap.put(key, bundle.getString(key)));
        }
    }
}
