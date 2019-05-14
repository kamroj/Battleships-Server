package com.sarny.spocone.server.languages_support;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Container for map with structure
 * Key: language code (ISO 639-1 standard)
 * Value: language name in this language (eg: English, Polski, Français)
 *
 * @author Wojciech Makiela
 */
@Component
class SupportedLanguages {

    private Map<String, String> codeLanguageMap = new HashMap<>();

    SupportedLanguages() {
        codeLanguageMap.put("EN", "English");
        codeLanguageMap.put("PL", "Polski");
    }

    Set<String> codes() {
        return codeLanguageMap.keySet();
    }

    Map<String, String> asMap() {
        return codeLanguageMap;
    }
}
