package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * Represents a verb.
 * Contains french conjugation of a verb for firstSingular, secondSingular and secondPlural.
 */
public class Verb {
    private final String firstSingular;
    private final String secondSingular;
    private final String secondPlural;

    /**
     * Stores the verb conjugations.
     * @param firstSingular
     * @param secondSingular
     * @param secondPlural
     */
    public Verb(final String firstSingular,
                final String secondSingular,
                final String secondPlural) {
        this.firstSingular = firstSingular;
        this.secondSingular = secondSingular;
        this.secondPlural = secondPlural;
    }

    public String getFirstSingular() {
        return this.firstSingular;
    }

    public String getSecondSingular() {
        return this.secondSingular;
    }

    public String getSecondPlural() {
        return this.secondPlural;
    }
}
