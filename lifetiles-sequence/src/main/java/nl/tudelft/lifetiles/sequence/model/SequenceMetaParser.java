package nl.tudelft.lifetiles.sequence.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.tudelft.lifetiles.core.util.IteratorUtils;

/**
 * The parser for the sequence meta data.
 *
 * @author Joren Hammudoglu
 *
 */
public class SequenceMetaParser {

    /**
     * The name of the sequence ID column.
     */
    private static final String ID_COLUMN = "##ID";

    /**
     * The column names, excluding the <code>##ID</code> column.
     */
    private List<String> columns;

    /**
     * The parsed data.
     */
    private Map<String, Map<String, String>> data;

    /**
     * Parse the data.
     *
     * @param file
     *            the file to parse
     * @throws IOException
     *             when the file was not found
     */
    public void parse(final File file) throws IOException {
        Iterator<String> lineStream = Files.lines(file.toPath()).iterator();

        String header = lineStream.next();
        parseHeader(header);

        data = new HashMap<>();
        for (String line : IteratorUtils.toIterable(lineStream)) {
            Entry<String, Map<String, String>> parsed = parseLine(line);
            data.put(parsed.getKey(), parsed.getValue());
        }
    }

    /**
     * Get the column names.
     *
     * @return a list of column names.
     */
    public List<String> getColumns() {
        maybeThrowNotParsed();
        return columns;
    }

    /**
     * Ge the parsed data.
     *
     * @return the data
     */
    public Map<String, Map<String, String>> getData() {
        maybeThrowNotParsed();
        return data;
    }

    /**
     * @return true iff the data is parsed, else false.
     */
    public boolean isParsed() {
        return data != null;
    }

    /**
     * Throw a {@link IllegalStateException} when the data is not parsed yet.
     */
    private void maybeThrowNotParsed() {
        if (!isParsed()) {
            throw new IllegalStateException("Data not parsed.");
        }
    }

    /**
     * Parse the header line into column names.
     *
     * @param headerLine
     *            the first line of the file
     * @throws IOException
     *             when the header cannot be parsed
     */
    private void parseHeader(final String headerLine) throws IOException {
        List<String> allColumns = Arrays.asList(headerLine.split("\t"));
        String firstColumn = allColumns.get(0);
        if (!firstColumn.equals(ID_COLUMN)) {
            throw new IOException("Invalid column header :" + firstColumn);
        }

        columns = allColumns.subList(1, allColumns.size());
    }

    /**
     * Parse a single line.
     *
     * @param line
     *            the line.
     * @return an entry of identifier and a value map of columns to values.
     */
    private Entry<String, Map<String, String>> parseLine(final String line) {
        assert columns != null;

        Map<String, String> result = new HashMap<>(columns.size());
        List<String> values = Arrays.asList(line.split("\t"));
        String identifier = values.get(0);

        assert values.size() + 1 != columns.size();

        for (int index = 0; index < values.size() - 1; index++) {
            String key = columns.get(index);
            String value = values.get(index + 1);
            result.put(key, value);
        }

        return new AbstractMap.SimpleEntry<String, Map<String, String>>(
                identifier, result);
    }
}
