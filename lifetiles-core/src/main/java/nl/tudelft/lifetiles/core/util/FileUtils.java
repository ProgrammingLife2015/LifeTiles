package nl.tudelft.lifetiles.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility functions for files.
 *
 * @author Joren Hammudoglu
 * @author Rutger van den Berg
 *
 */
public final class FileUtils {

    /**
     * Uninstantiable class.
     */
    private FileUtils() {
        // noop
    }

    /**
     * Find the files with an extension inside a directory.
     *
     * @param directory
     *            the directory to search
     * @param extension
     *            the extension to search for
     * @return the files inside the directory with the extension
     */
    public static List<File> findByExtension(final File directory,
            final String extension) {
        assert directory.isDirectory();

        File[] res = directory.listFiles(fileName -> fileName.getName()
                .endsWith(extension));
        if (res == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(res);
    }

    /**
     * Looks for and opens a file with the specified extension in the specified
     * directory, and throws an exception if multiple files are found with the
     * same extension.
     *
     * @param directory
     *            The directory in which to search for the file.
     * @param extension
     *            The file extension to look for.
     * @return The file with the specified extension.
     * @throws IOException
     *             When a number of files other than one is found with the
     *             specified extension.
     */
    public static File getSingleFileByExtension(final File directory,
            final String extension) throws IOException {
        List<File> files = FileUtils.findByExtension(directory, extension);
        if (files.size() != 1) {
            throw new IOException("Expected 1 " + extension
                    + " file instead of " + files.size());
        }
        return files.get(0);
    }
}
