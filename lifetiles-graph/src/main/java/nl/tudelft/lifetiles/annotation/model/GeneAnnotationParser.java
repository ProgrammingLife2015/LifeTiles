package nl.tudelft.lifetiles.annotation.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Static class which parses gene annotations.
 *
 * @author Jos
 *
 */
public final class GeneAnnotationParser {

    /**
     * The index of the start field in a annotation line.
     */
    private static final int START_FIELD = 3;
    /**
     * The index of the end field in a annotation line.
     */
    private static final int END_FIELD = 4;
    /**
     * The index of the extra field in a annotation line.
     */
    private static final int EXTRA_FIELD = 8;
    /**
     * The index of the name field in the extra field in a annotation line.
     */
    private static final String NAME_FIELD = "Name";
    /**
     * The standard null name for annotations without a name.
     */
    private static final String NULL_NAME = "";

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
    public static Set<GeneAnnotation> parseGeneAnnotations(final File file)
            throws IOException {
        Set<GeneAnnotation> genomeAnnotations = new HashSet<>();
        Stream<String> annotationLines = Files.lines(file.toPath());
        annotationLines.map(GeneAnnotationParser::parseGeneAnnotation)
                .filter(genome -> genome != null)
                .forEach(genome -> genomeAnnotations.add(genome));
        annotationLines.close();
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
        long start = Long.parseLong(columns[START_FIELD]);
        long end = Long.parseLong(columns[END_FIELD]);
        Map<String, String> fields = extractGeneFields(columns[EXTRA_FIELD]
                .split(";"));
        String name;
        if (fields.containsKey(NAME_FIELD)) {
            name = fields.get(NAME_FIELD);
        } else {
            name = NULL_NAME;
        }
        return new GeneAnnotation(start, end, name);
    }

    /**
     * Method which extract the gene fields into a map.
     *
     * @param fields
     *            Fields of the gene.
     * @return Map of attributes in the gene.
     */
    private static Map<String, String> extractGeneFields(final String... fields) {
        final Map<String, String> genomeFields = new HashMap<String, String>();
        for (String field : fields) {
            String[] attribute = field.split("=");
            genomeFields.put(attribute[0], attribute[1]);
        }
        return genomeFields;
    }
}
