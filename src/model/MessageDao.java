package fr.univ_lyon1.info.m1.elizagpt.model;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Singleton data access object for messages.
* Shortened DAO implementation as the app only requires findall, create and delete.
*/
public final class MessageDao extends Observable {

    private int indexIncrement = 0;
    private HashMap<Integer, Message> messageMap = new HashMap();
    private static MessageDao instance = null;

    /**
     * Singleton assurance.
     */
    private MessageDao() { }

    /**
     * Singleton assurance.
     * Returns itself.
     * @return MessageDao
     */
    public static MessageDao getInstance() {
        if (instance == null) {
            MessageDao.instance = new MessageDao();
        }
        return instance;
    }

    /**
     * Creates a message and adds it to the DAO.
     * @param data
     * @param sender
     */
    public void create(final String data, final MessageSource sender) {
        messageMap.put(indexIncrement, new Message(data, indexIncrement, sender));
        indexIncrement++;
        this.notifyObservers();
    }

    /**
     * Returns all the messages contained in the DAO as an ArrayList.
     * @return ArrayList
     */
    public ArrayList<Message> findAll() {
        ArrayList<Message> msgList = new ArrayList<>();
        messageMap.forEach((key, value) -> {
            msgList.add(value);
        });
        return msgList;
    }

    /**
     * Returns the researched message based on its id.
     * @param id : the researched message id
     * @return Message NULL if not in the DAO
     */
    public Message find(final int id) {
        return messageMap.get(id);
    }

    /**
     * Deletes the message from the DAO based on its id.
     * @param id : the researched message id
     */
    public void delete(final int id) {
        messageMap.remove(id);
        this.notifyObservers();
    }


}
