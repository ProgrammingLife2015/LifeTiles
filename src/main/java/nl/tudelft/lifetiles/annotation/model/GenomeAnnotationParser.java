package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Static class which parses genome annotations.
 * 
 * @author Jos
 *
 */
public final class GenomeAnnotationParser {

    /**
     * Static class can not have a public or default constructor.
     */
    private GenomeAnnotationParser() {
        // noop
    }

    public static Map<String, GenomeAnnotation> parseGenomeAnnotations(
            final File genomeAnnotationFile) throws IOException {
        Map<String, GenomeAnnotation> genomeAnnotations = new HashMap<String, GenomeAnnotation>();
        Iterator<String> iterator = Files.lines(genomeAnnotationFile.toPath())
                .iterator();
        String line;
        while (iterator.hasNext()) {
            line = iterator.next();
            parseGenomeAnnotation(line);
            genomeAnnotations
        }
        return annotations;
    }

    private static GenomeAnnotation parseGenomeAnnotation(final String line) {
        return null;
    }
}
