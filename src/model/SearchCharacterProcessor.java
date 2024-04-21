package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specialized searchProcessor searching for character matching.
 */
public class SearchCharacterProcessor extends SearchProcessor {
    private Pattern pattern;
    private Matcher matcher;
    /**
     * Matches characters. Pattern is the whole word to find in a message
     * @param content
     * @param characterToMatch
     * @return true if found, false if not found
     */
    @Override
    boolean match(final String content, final String characterToMatch) {
        pattern = Pattern.compile(".*" + characterToMatch + ".*");
        matcher = pattern.matcher(content);
        return matcher.find();
    }
}
