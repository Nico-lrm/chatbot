package fr.univ_lyon1.info.m1.elizagpt.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Answer sentences data sets. Used to answer to the user in MessageProcessor.
 * Parse answers files to store the sentences in different lists.
 *
 *  The answers contain _TAGS_ so that MessageProcessor
 *  adapt the sentences to the user preferences.
 *  Verbs must be 2nd person singular.
 *      TAGS:  "_PERSONAL_PRONOUN_"    -> "vous/te"
 *             "_PRONOUN_"             -> "vous/tu"
 *             "_USERNAME_"            -> detected username
 *             "VB_verb_VB"            -> 2nd Pers of singular verb to 2nd Pers singular/plural
 */
public class Answer {
    private List<String> defaultAnswers;
    private List<String> smartQuestionStarts;
    private List<String> questionAnswers;
    private List<String> directAnswers;

    /**
     * Parses the different answer files on initialization.
     * see parseAnswers()
     */
    public Answer() {
        parseAnswers();
    }
    /**
     * Parses the different answer files.
     * Stores the different sentences in lists of string.
     * Throws runtime exception when an answer file is unreadable.
     */
    private void parseAnswers() {
        try {
            defaultAnswers = Files.readAllLines(Paths.get("defaultAnswers.txt"));
            smartQuestionStarts = Files.readAllLines(Paths.get("smartQuestionStarts.txt"));
            questionAnswers = Files.readAllLines(Paths.get("questionAnswers.txt"));
            directAnswers = Files.readAllLines(Paths.get("directAnswers.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getDefaultAnswers() {
        return defaultAnswers;
    }

    public List<String> getSmartQuestionStarts() {
        return smartQuestionStarts;
    }

    public List<String> getQuestionAnswers() {
        return questionAnswers;
    }

    public List<String> getDirectAnswers() {
        return directAnswers;
    }
}
