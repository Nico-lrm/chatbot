package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Singleton data access object for verbs.
 * Shortened DAO implementation as the app only requires findOne, create and delete.
 */
public final class VerbDao {
    private ArrayList<Verb> verbs;
    private static VerbDao instance = null;

    /**
     * Initializes verb arraylist.
     */
    private VerbDao() {
        this.verbs = new ArrayList<>();
    }

    /**
     * Returns itself or creates itself if it doesn't exist already.
     * @return verbDao instance
     */
    public static VerbDao getInstance() {
        if (instance == null) {
            VerbDao.instance = new VerbDao();
        }
        return VerbDao.instance;
    }

    /**
     * @brief Create a new Verb used for message processing
     * @param firstSingular
     * @param secondSingular
     * @param secondPlural
    */
    public void create(final String firstSingular,
                       final String secondSingular,
                       final String secondPlural) {
        this.verbs.add(new Verb(firstSingular, secondSingular, secondPlural));
    }

    /**
     * @brief Find a Verb in the DAO
     * @throws NoSuchElementException if the verb is not found in the list
     * @param data
     * @return
    */
    public Verb findOne(final String data) {
        for (Verb v : verbs) {
            if (v.getFirstSingular().equals(data)
                    || v.getSecondSingular().equals(data)
                    || v.getSecondPlural().equals(data)) {
                return v;
            }
        }
        throw new NoSuchElementException(
                "Impossible de trouver un verbe contenant \"" + data + "\" dans le DAO");
    }

    /**
     * @brief Return all verbs
     * @return
    */
    public ArrayList<Verb> findAll() {
        return this.verbs;
    }
}
