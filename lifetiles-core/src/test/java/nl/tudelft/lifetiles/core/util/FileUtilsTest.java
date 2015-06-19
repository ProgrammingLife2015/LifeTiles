package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilsTest {

    private File projectDir;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        projectDir = new File(".");
    }

    @Test
    public void testFindByExtension() {
        List<File> files = FileUtils.findByExtension(projectDir, ".xml");
        assertTrue(files.contains(new File("./pom.xml")));
    }

    @Test
    public void testFindByExtensionNotFound() {
        List<File> files = FileUtils.findByExtension(projectDir, ".satan");
        assertTrue(files.isEmpty());
    }

    @Test
    public void getSingleFileByExtensionTest() throws IOException {
        thrown.expect(IOException.class);
        FileUtils.getSingleFileByExtension(projectDir, ".txt");
    }
}
