package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Resistance Annotation Parser which parses annotations from a file into a
 * annotation list.
 *
 * @author Jos
 *
 */
public class ResistanceAnnotationParser {

    /**
     * Parses a annotation file and returns a list of resistance annotations.
     *
     * @param annotationFile
     *            The annotation file to be parsed.
     * @throws IOException
     *             When there is an error reading the specified file.
     */
    public List<ResistanceAnnotation> parseAnnotations(final File annotationFile) throws IOException {
        List<ResistanceAnnotation> annotations = new ArrayList<ResistanceAnnotation>();
        Iterator<String> iterator = Files.lines(annotationFile.toPath())
                .iterator();
        String line;
        while (iterator.hasNext()) {
            line = iterator.next();
            if (!line.startsWith("##")) {
                parseAnnotation(line);
                annotations.add(parseAnnotation(line));
            }
        }
        return annotations;
    }

    /**
     * Parses a single annotation from a line.
     *
     * @param line
     *            Line which contains a single annotation and is not a comment.
     * @return parsed annotation in the given line.
     */
    private ResistanceAnnotation parseAnnotation(final String line) {
        String[] genomeResistance = line.split("\t");
        String drugResistance = genomeResistance[1];
        String[] genome = genomeResistance[0].split(",");
        String change = genome[1];
        String filter = genome[2];
        Long genomePosition = Long.parseLong(genome[3]);
        String[] mutation = genome[0].split(":");
        String geneName = mutation[0];
        String typeOfMutation = mutation[1];
        return new ResistanceAnnotation(geneName, typeOfMutation, change,
                filter, genomePosition, drugResistance);
    }
}
