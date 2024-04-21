package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic to process a message (and probably reply to it).
 */
public class MessageProcessor {
    private VerbDao verbDao = VerbDao.getInstance();
    private MessageDao msgDao = MessageDao.getInstance();
    private final Random random = new Random();
    private String username;
    private boolean allowFamiliarity = false;

    private final Answer answerData = new Answer();

    /**
     * Normalize the text: remove extra spaces, add a final dot if missing.
     * @param text
     * @return normalized text.
     */
    public String normalize(final String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0.")
                .replaceFirst("^[a-zàâçéèêëîïôûùüÿñæœ]", text.substring(0, 1).toUpperCase());
    }

    /**
     * Returns the pronoun depending on either it is allowed to be familiar or not.
     * @return
     */
    private String getPronoun() {
        return ((allowFamiliarity) ?  "tu" : "vous");
    }

    /**
     * Returns the possessive pronoun depending on either it is allowed to be familiar or not.
     * @return
     */
    private String getPersonalPronoun() {
        return ((allowFamiliarity) ?  "te" : "vous");
    }

    /**
     * Returns the personal pronoun depending on either it is allowed to be familiar or not.
     * @return
     */
    private String getPossessivePronoun() {
        return ((allowFamiliarity) ? "ton" : "votre");
    }

    /**
     * Matches the User input to create a response.
     * There are 3 types of answers :
     *  - "je" + "verb" -> "vous/tu" + "verb"
     *  - questions -> specific pre written answers
     *  - "tu" -> specific pre written answers
     *  - default -> when no pattern match
     * Precondition String must be normalized !!
     * @param normalizedText
     * @return
     */
    String answerMatcher(final String normalizedText) {
        Pattern pattern;
        Matcher matcher;
        String answer = "";
        // Highly specific answers
        pattern = Pattern.compile(
                ".*Je m'appelle ([a-zàâçéèêëîïôûùüÿñæœ]+).*", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            if (username != null) {
                answer = "Nouveau nom ? Je préférais l'ancien... bref.. ";
            }
            username = matcher.group(1);
            answer = answer + "Bonjour " + matcher.group(1) + ".";
            return answer;
        }

        pattern = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            if (username != null) {
                answer = getPossessivePronoun() + " nom est " + username + ".";
            } else {
                answer = "Je ne connais pas " + getPossessivePronoun() + " nom.";
            }
            return answer;
        }

        pattern = Pattern.compile(".*tuto[yi].*", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            if (!allowFamiliarity) {
                answer = "Très bien je vais te tutoyer à partir de maintenant !";
                allowFamiliarity = true;
                return answer;
            } else {
                answer = "Pardon.. je vais arrêter de vous tutoyer.";
                allowFamiliarity = false;
                return answer;
            }
        }

        pattern = Pattern.compile(
                "Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            answer = "Le plus " + matcher.group(1)
                    + " est bien sûr Nicolas !";
            return answer;
        }

        pattern = Pattern.compile(".* tu .*", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            answer = answerReplace(pickRandom(answerData.getDirectAnswers()));
            return answer;
        }

        // Smart randomised generic answers
        pattern = Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            answer = answerReplace(pickRandom(answerData.getQuestionAnswers()));
            return answer;
        }

        pattern = Pattern.compile(".*((J'|Je).*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText);
        if (matcher.matches()) {
            String startQuestion = pickRandom(answerData.getSmartQuestionStarts());
            String endQuestion = generateQuestionEnd(matcher.group(1));
            if (endQuestion != null) {
                answer = answerReplace(startQuestion) + " " + endQuestion + " ?";
                return answer;
            }
        }

        // Default answers
        answer = answerReplace(pickRandom(answerData.getDefaultAnswers()));
        return answer;
}

    /**
     * Replaces the generic attributes from the generic answers
     * and adapts it to the current context.
     * @param answerInput
     * @return
     */
    private String answerReplace(final String answerInput) {
        String replacedAnswer = answerInput
                .replaceAll("_PERSONAL_PRONOUN_", getPersonalPronoun())
                .replaceAll("_PRONOUN_", getPronoun())
                .replaceAll("_USERNAME_", (username != null) ? username : "");
        //Generic verb replacing
        Pattern pattern = Pattern.compile(
                "VB_([a-zàâçéèêëîïôûùüÿñæœ]+)_VB", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(answerInput);
        while (matcher.find()) {
            Verb verb = verbDao.findOne(matcher.group(1));
            replacedAnswer = replacedAnswer.replaceAll("VB_" + matcher.group(1) + "_VB",
                    ((allowFamiliarity) ? verb.getSecondSingular() : verb.getSecondPlural()));
        }
        return replacedAnswer;
    }
    /**
     * Takes a sentence and returns the main verb of the sentence using regex.
     * The verb has to be behind the pronoun "je" in order to be extracted.
     * @param sentence
     * @return the extracted Verb null if not found
     */
    private String verbExtractor(final String sentence) {

        Pattern pattern = Pattern.compile("[Jj](e |')+(me |ne )*([a-zàâçéèêëîïôûùüÿñæœ]+) ");
        Matcher matcher = pattern.matcher(sentence);
        if (matcher.find()) {
            String foundVerb = matcher.group(3);
            return foundVerb;
        }
        return null;
    }

    /**
     * Takes the verb as a string, finds it in the dao and creates the answer.
     * @param verbText
     * @param lastRequest
     * @return the processed reponse null if not found
     */
    private String firstToSecondPerson(final String verbText, final String lastRequest) {
        String processedResponse;
        try {
            Verb verb = verbDao.findOne(verbText);

            if (!allowFamiliarity) {
                processedResponse = lastRequest
                        .replaceAll("[Jj](e |')+", "vous ")
                        .replaceFirst(" " + verbText + " ", " " + verb.getSecondPlural() + " ")
                        .replace(" mon ", " votre ")
                        .replace(" me ", " vous ")
                        .replace(" ma ", " votre ")
                        .replace(" mes ", " vos ")
                        .replace(" moi ", " vous ");
            } else {
                processedResponse = lastRequest
                        .replaceAll("[Jj](e |')+", "tu ")
                        .replaceFirst(" " + verbText + " ", " " + verb.getSecondSingular() + " ")
                        .replace(" me ", " te ")
                        .replace(" mon ", " ton ")
                        .replace(" ma ", " ta ")
                        .replace(" mes ", " tes ")
                        .replace(" moi ", " toi ");
            }
            return processedResponse;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous/Tu ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     * @param lastRequest
     * @return The 2nd-person sentence.
     */
    public String generateQuestionEnd(final String lastRequest) {
        String verb = verbExtractor(lastRequest);
        if (verb == null) {
            return null;
        }
        String processedResponse = firstToSecondPerson(verb, lastRequest);
        if (processedResponse == null) {
            return null;
        }
        return processedResponse;
    }

    /**
     * Takes the user request sentence and adds it to the msgDAO,
     * processes the answer and adds it to the DAO.
     * @param userSentence
     */
    public void processRequest(final String userSentence) {
        String normalizedUserSentence = normalize(userSentence);
        msgDao.create(normalizedUserSentence, MessageSource.USER);
        String processedResponse = normalize(answerMatcher(normalizedUserSentence));
        msgDao.create(processedResponse, MessageSource.ELIZA);
    }


    /** Pick an element randomly in a list. */
    public <T> T pickRandom(final List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
