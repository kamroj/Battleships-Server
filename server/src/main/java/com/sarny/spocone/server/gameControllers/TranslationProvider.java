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

    private Map<String, Translation> supportedLanguages; // Key is language ISO 639-1 code - ect "pl", "en"

    public TranslationProvider() {
        supportedLanguages = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("pl");
        supportedLanguages.put("pl", new Translation(bundle));
    }

    Translation getTranslation(String code) {
        return supportedLanguages.get(code);
    }

    class Translation {
        Map<String, String> translation;

        Translation(ResourceBundle bundle) {
            translation = new HashMap<>();
            bundle.keySet().forEach(key -> translation.put(key, bundle.getString(key)));
        }
    }
}
