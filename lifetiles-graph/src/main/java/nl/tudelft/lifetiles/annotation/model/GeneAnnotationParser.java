package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Static class which parses gene annotations.
 *
 * @author Jos
 *
 */
public final class GeneAnnotationParser {

    /**
     * Static class can not have a public or default constructor.
     */
    private GeneAnnotationParser() {
        // noop
    }

    /**
     * Parses a file of genes into a map from gene name to gene.
     *
     * @param file
     *            the file with gene annotations.
     * @throws IOException
     *             When there is an error reading the specified file.
     * @return map from gene name to gene.
     */
    public static Map<String, GeneAnnotation> parseGeneAnnotations(
            final File file) throws IOException {
        Map<String, GeneAnnotation> genomeAnnotations = new HashMap<String, GeneAnnotation>();
        Stream<String> annotationFile = Files.lines(file.toPath());
        annotationFile.forEach(line -> {
            GeneAnnotation genome = parseGeneAnnotation(line);
            if (genome != null) {
                genomeAnnotations.put(genome.getName(), genome);
            }
        });
        annotationFile.close();
        return genomeAnnotations;
    }

    /**
     * Parses a single line of the gene file into a gene.
     *
     * @param line
     *            Single line of the gene file.
     * @return parsed gene.
     */
    private static GeneAnnotation parseGeneAnnotation(final String line) {
        String[] columns = line.split("\t");
        if (columns[2].equals("gene")) {
            long start = Long.parseLong(columns[3]);
            long end = Long.parseLong(columns[4]);
            Map<String, String> fields = extractGeneFields(columns[8]
                    .split(";"));
            String name = fields.get("Name");
            return new GeneAnnotation(start, end, name);
        }
        return null;
    }

    /**
     * Method which extract the gene fields into a map.
     *
     * @param fields
     *            Fields of the gene.
     * @return Map of attributes in the gene.
     */
    private static Map<String, String> extractGeneFields(final String[] fields) {
        final Map<String, String> genomeFields = new HashMap<String, String>();
        for (String field : fields) {
            String[] attribute = field.split("=");
            genomeFields.put(attribute[0], attribute[1]);
        }
        return genomeFields;
    }
}
