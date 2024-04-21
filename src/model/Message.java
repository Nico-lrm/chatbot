package fr.univ_lyon1.info.m1.elizagpt.model;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that represents a message.
 * Contains all its local data : timestamp, data, message sender aka messageSource.
 */
public class Message {

    private String timestamp;
    private String data;
    private MessageSource messageSource;
    private int id;

    /**
     * Creates a message given the parameters, the timestamp is generated creation time.
     * @param data
     * @param id
     * @param messageSource
     */
    public Message(final String data, final int id, final MessageSource messageSource) {
        this.data = data;
        this.id = id;
        this.messageSource = messageSource;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        timestamp = dateFormat.format(new Date());
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

}
