package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;

/**
 * Homemade simplified Observable.
 * Keeps a list of observers and notifies them when needed.
 */
public class Observable {
    private ArrayList<Observer> observerList = new ArrayList<>();

    /**
     * Adds observer to the observers list.
     * @param observer
     */
    public void addObserver(final Observer observer) {
        this.observerList.add(observer);
    }
    /**
     * Removes observer from the observers list.
     * @param observer
     */
    public void removeObserver(final Observer observer) {
        this.observerList.remove(observer);
    }

    /**
     * Notifies all the observers contained in the observers list.
     */
    public void notifyObservers() {
        for (Observer observer: observerList) {
            observer.update();
        }
    }

}
