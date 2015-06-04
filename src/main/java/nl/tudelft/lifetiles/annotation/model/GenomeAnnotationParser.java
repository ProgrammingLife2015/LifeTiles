package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
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

    /**
     * Parses a file of genomes into a map from genome name to genome.
     * 
     * @param genomeAnnotationFile
     *            the file with genome annotations.
     * @throws IOException
     *             When there is an error reading the specified file.
     * @return map from genome name to genome.
     */
    public static Map<String, GenomeAnnotation> parseGenomeAnnotations(
            final File genomeAnnotationFile) throws IOException {
        Map<String, GenomeAnnotation> genomeAnnotations = new HashMap<String, GenomeAnnotation>();
        Iterator<String> iterator = Files.lines(genomeAnnotationFile.toPath())
                .iterator();
        String line;
        while (iterator.hasNext()) {
            line = iterator.next();
            GenomeAnnotation genome = parseGenomeAnnotation(line);
            if (genome != null) {
                genomeAnnotations.put(genome.getName(), genome);
            }
        }
        return genomeAnnotations;
    }

    /**
     * Parses a single line of the genome file into a genome.
     * TODO: specific implementation of the genome field handling.
     * 
     * @param line
     *            Single line of the genome file.
     * @return parsed genome.
     */
    private static GenomeAnnotation parseGenomeAnnotation(final String line) {
        return null;
    }
}
