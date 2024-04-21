package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specialized searchProcessor searching for character matching.
 */
public class SearchRegexProcessor extends SearchProcessor {
    private Pattern pattern;
    private Matcher matcher;
    /**
     * Matches regex expression. Pattern is the whole word to find in a message
     * @param content
     * @param regexPattern
     * @return true if found, false if not found
     */
    @Override
    boolean match(final String content, final String regexPattern) {
        pattern = Pattern.compile(regexPattern); //Pattern.CASE_INSENSITIVE ?
        matcher = pattern.matcher(content);
        return matcher.find();
    }
}

