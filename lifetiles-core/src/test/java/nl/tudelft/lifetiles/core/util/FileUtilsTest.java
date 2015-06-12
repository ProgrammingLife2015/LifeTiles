package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {

    private File projectDir;

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

}
