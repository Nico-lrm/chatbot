package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specialized searchProcessor searching for character matching.
 */
public class SearchWordProcessor extends SearchProcessor {
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Matches whole words. Pattern is the whole word to find in a message
     * @param content
     * @param wordToMatch
     * @return true if found, false if not found
     */
    @Override
    boolean match(final String content, final String wordToMatch) {
        pattern = Pattern.compile("\\b" + wordToMatch + "\\b");
        matcher = pattern.matcher(content);
        return matcher.find();
    }
}
