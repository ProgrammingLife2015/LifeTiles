package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Known Mutation Parser which parses known mutations from a file into a
 * known mutations list.
 *
 * @author Jos
 *
 */
public final class KnownMutationParser {

    /**
     * Static class can not have a public or default constructor.
     */
    private KnownMutationParser() {
        // noop
    }

    /**
     * Parses a known mutations file and returns a list of resistance
     * annotations.
     *
     * @param knownMutationFile
     *            The known mutations file to be parsed.
     * @throws IOException
     *             When there is an error reading the specified file.
     * @return list of parsed resistance annotations.
     */
    public static List<KnownMutation> parseKnownMutations(
            final File knownMutationFile) throws IOException {
        List<KnownMutation> knownMutations = new ArrayList<KnownMutation>();
        Iterator<String> iterator = Files.lines(knownMutationFile.toPath())
                .iterator();
        String line;
        while (iterator.hasNext()) {
            line = iterator.next();
            if (!line.startsWith("##")) {
                knownMutations.add(parseKnownMutation(line));
            }
        }
        return knownMutations;
    }

    /**
     * Parses a single known mutation from a line.
     *
     * @param line
     *            Line which contains a single known mutation and is not a
     *            comment.
     * @return parsed known mutation in the given line.
     */
    private static KnownMutation parseKnownMutation(final String line) {
        String[] genomeResistance = line.split("\t");
        String drugResistance = genomeResistance[1];
        String[] genome = genomeResistance[0].split(",");
        String change = genome[1];
        String filter = genome[2];
        Long genomePosition = Long.parseLong(genome[3]);
        String[] mutation = genome[0].split(":");
        String geneName = mutation[0];
        String typeOfMutation = mutation[1];
        return new KnownMutation(geneName, typeOfMutation, change, filter,
                genomePosition, drugResistance);
    }
}
