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
        String[] columns = line.split("\t");
        if (columns[2].equals("gene")) {
            long start = Long.parseLong(columns[3]);
            long end = Long.parseLong(columns[4]);
            Map<String, String> fields = extractGenomeFields(columns[8]
                    .split(";"));
            String name = fields.get("Name");
            return new GenomeAnnotation(start, end, name);
        }
        return null;
    }

    /**
     * Method which extract the genome fields into a map.
     * 
     * @param fields
     *            Fields of the genome.
     * @return Map of attributes in the genome.
     */
    private static Map<String, String> extractGenomeFields(String[] fields) {
        Map<String, String> genomeFields = new HashMap<String, String>();
        for (String field : fields) {
            String[] attribute = field.split("=");
            genomeFields.put(attribute[0], attribute[1]);
        }
        return genomeFields;
    }
}
