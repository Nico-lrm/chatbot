package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * Homemade simplified observer interface.
 * Only contains update for the moment.
 */
public interface Observer {
    /**
     * Function called when the observable notifies his observers.
     */
    void update();
}
