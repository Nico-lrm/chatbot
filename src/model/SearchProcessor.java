package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class responsible for element research in messages.
 * Search method is generic, pattern match function must be overriden.
 */
public abstract class SearchProcessor {
    private MessageDao msgDao = MessageDao.getInstance();
    private Pattern pattern;
    private Matcher matcher;
    /**
     * Returns if pattern matches content.
     * @param content
     * @param pattern
     * @return true is match, false if non match
     */
    abstract boolean match(String content, String pattern);

    /**
     * Searches through the messages DAO looking for messages that match the researched pattern.
     * If no matches found returns an empty list of IDs.
     * @param pattern
     * @return a list of Message IDs
     */
    public ArrayList<Message> search(final String pattern) {
        ArrayList<Message> searchedMsgIDs = new ArrayList<>();
        ArrayList<Message> msgList = msgDao.findAll();
        msgList.forEach((msg) -> {
            if (match(msg.getData(), pattern)) {
                searchedMsgIDs.add(msg);
            }
        });
        return searchedMsgIDs;
    }

}
