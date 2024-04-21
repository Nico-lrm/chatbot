package fr.univ_lyon1.info.m1.elizagpt.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parses a French conjugation verb file.
 */
public class VerbFileParser {
    private VerbDao verbDao;

    /**
     * @brief Create a VerbFileParser object and parse a file.
     * @param filepath File to parse
     * @param delimiter When to cut the line
    */
    public VerbFileParser(final String filepath, final String delimiter) {
        this.verbDao = VerbDao.getInstance();
        parse(filepath, delimiter);
    }

    /**
     * @brief Create a VerbFileParser object
    */
    public VerbFileParser() {
        this.verbDao = VerbDao.getInstance();
    }

    // TODO: Find a way to make this parser less specific

    /**
     * @brief Read and parse a line
     * @param line Line to parse
     * @param delimiter When to cut the line
     * @return a list containing firstSingular, secondSingular and secondPlural to create a verb
    */
    private List<String> parseLine(final String line, final String delimiter) {
        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(line);
        sc.useDelimiter(delimiter);

        // Find first singular, second singular and second plural
        for (int i = 0; i < 5; i++) {
            sc.next();
        }
        list.add(sc.next());
        list.add(sc.next());
        for (int i = 0; i < 2; i++) {
            sc.next();
        }
        list.add(sc.next());
        return list;
    }

    /**
     * @brief Parse a file using a delimiter to fill the VerbDao
     * @param filepath Which file to parse
     * @param delimiter When to cut the line
    */
    public void parse(final String filepath, final String delimiter) {
        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            // Skip first line that contains legend for the standard file
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> list = parseLine(line, delimiter);
                if (list.size() != 3) {
                    continue;
                }
                String firstSingular = list.get(0);
                String secondSingular = list.get(1);
                String secondPlural = list.get(2);
                if (firstSingular.isEmpty()
                        || firstSingular.equals(" ")
                        || secondSingular.isEmpty()
                        || secondSingular.equals(" ")
                        || secondPlural.isEmpty()
                        || secondPlural.equals(" ")) {
                    continue;
                }
                this.verbDao.create(firstSingular, secondSingular, secondPlural);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
