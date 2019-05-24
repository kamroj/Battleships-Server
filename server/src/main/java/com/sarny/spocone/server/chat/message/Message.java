package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.server.languages_support.TranslationProvider;

/**
 * Representation of single message.
 * Has ability to translate its content to specific language.
 *
 * @author Wojciech Makiela
 */
public abstract class Message {

    String sentBy;
    private String formatKey;
    private TranslationProvider translationProvider = new TranslationProvider();

    public Message(String sentBy, String formatKey) {
        this.sentBy = sentBy;
        this.formatKey = formatKey;
    }

    /**
     * Translate and format message content.
     * @param language desired message translation.
     * @return translated message.
     */
    public abstract String asString(String language);

    String formatForLanguage(String language) {
        return translationProvider.getValueForKeyForGivenLanguage(language, formatKey);
    }

    String shotOutcomeInLanguage(String language, ShotOutcome outcome) {
        return translationProvider.getValueForKeyForGivenLanguage(language, outcome.toString());
    }
}
