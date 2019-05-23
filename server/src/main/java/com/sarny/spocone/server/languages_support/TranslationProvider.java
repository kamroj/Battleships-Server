package com.sarny.spocone.server.languages_support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Loads translations for languages specified in {@link SupportedLanguages} from .properties files
 * <p>
 * Map structure:
 * Key: language code (ISO 639-1 standard)
 * Value: {@link Translation} - 1 to 1 representation of .properties file content
 *
 * @author Wojciech Makiela
 */
@Component
public class TranslationProvider {

    private static Logger logger = LogManager.getLogger(TranslationProvider.class);
    private SupportedLanguages supportedLanguages;
    private Map<String, Translation> loadedTranslations;

    @Autowired
    public TranslationProvider(SupportedLanguages supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
        loadedTranslations = new HashMap<>();
        loadTranslations();
    }

    public TranslationProvider() {
        this.supportedLanguages = new SupportedLanguages();
        loadedTranslations = new HashMap<>();
        loadTranslations();
    }

    private void loadTranslations() {
        for (String languageCode : supportedLanguages.codes()) {
            ResourceBundle bundle = ResourceBundle.getBundle(languageCode);
            loadedTranslations.put(languageCode.toUpperCase(), new Translation(bundle));
        }
    }

    public String getValueForKeyForGivenLanguage(String language, String key) {
        Translation translation = loadedTranslations.get(language.toUpperCase());
        if (translation == null) {
            logger.warn("Searched for key " + key + " in unsupported translation {" + language + "}.");
            return "Unsupported Language";
        }
        return translation.asMap.get(key);
    }

    Translation getTranslation(String code) {
        return loadedTranslations.get(code.toUpperCase());
    }

    static class Translation {
        Map<String, String> asMap;

        Translation(ResourceBundle bundle) {
            asMap = new HashMap<>();
            bundle.keySet().forEach(key -> asMap.put(key, bundle.getString(key)));
        }
    }
}
