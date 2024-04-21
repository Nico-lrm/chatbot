package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageDao;
import fr.univ_lyon1.info.m1.elizagpt.model.Observer;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchRegexProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchWordProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.SearchCharacterProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.Display;

import java.util.ArrayList;

/**
 * Controller dispatches data between the Model (Message and Search) and the View (Display).
 * Uses lists to handle multiple displays and searchProcessors.
 * Observes messageDao to update the view on change.
 */
public class Controller implements Observer {
    private final MessageProcessor messageProcessor;
    private final ArrayList<Display> displays;
    private final ArrayList<SearchProcessor> searchProcessor;

    /**
     * Controller initialization.
     * Stores the message processor and initializes its lists.
     * @param messageProcessor
     */
    public Controller(final MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        this.displays = new ArrayList<>();
        this.searchProcessor = new ArrayList<>();
    }

    /**
     * Adds display to the handled display list.
     * @param display
     */
    public void addDisplay(final Display display) {
        this.displays.add(display);
        this.searchProcessor.add(new SearchRegexProcessor());
    }

    /**
     * Sends the user request to the message processor.
     * @param data
     */
    public void sendMessage(final String data) {
        this.messageProcessor.processRequest(data);
    }

    /**
     * Sends the search pattern to the desired search processor.
     * @param pattern
     * @param id
     */
    public void searchMessage(final String pattern, final int id) {
        ArrayList<Message> searchedMessages = searchProcessor.get(id).search(pattern);
        displays.get(id).update(searchedMessages);
    }

    /**
     * Changes the desired search processor.
     * @param pattern
     * @param id
     */
    public void setSearchProcessor(final String pattern, final int id) {
        SearchProcessor sp = this.searchProcessor.get(id);
        switch (pattern) {
            case "Regex":
                sp =  new SearchRegexProcessor();
                break;
            case "Substring":
                sp =  new SearchWordProcessor();
                break;
            case "Character":
                sp = new SearchCharacterProcessor();
                break;
            default:
                break;
        }
        MessageDao messageDao = MessageDao.getInstance();
        displays.get(id).update(messageDao.findAll());
    }

    /**
     * Deletes the message pointed by the id.
     * @param id
     */
    public void deleteMessage(final int id) {
        MessageDao messageDao = MessageDao.getInstance();
        messageDao.delete(id);
    }

    @Override
    public void update() {
        MessageDao messageDao = MessageDao.getInstance();
        for (Display d : displays) {
            d.update(messageDao.findAll());
        }
    }
}
