package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

public class SequenceMetaParserTest {
    static final String META_FILE = "/data/meta_data/test.meta";
    private File file;
    private SequenceMetaParser parser;

    @Before
    public void setUp() throws URISyntaxException, IOException {
        file = new File(this.getClass().getResource(META_FILE).toURI());
        parser = new SequenceMetaParser();
        parser.parse(file);
    }

    @Test
    public void parserIsParsedTest() {
        assertTrue(parser.isParsed());
    }

    @Test(expected=IllegalStateException.class)
    public void parserIsNotParsedTest() {
        SequenceMetaParser unParser = new SequenceMetaParser();
        unParser.getData();
    }

    @Test
    public void parserColumnsTest() throws IOException {
        assertArrayEquals(new String[] {
                "Foo", "Bar"
        }, parser.getColumns().toArray());
    }

    @Test
    public void parserValueTest1() {
        assertEquals("yes", parser.getData().get("TKK_1").get("Foo"));
    }

    @Test
    public void parserValueTest2() {
        assertEquals("no", parser.getData().get("TKK_1").get("Bar"));
    }

    @Test
    public void parserValueTest3() {
        assertEquals("no", parser.getData().get("TKK_2").get("Foo"));
    }

    @Test
    public void parserValueTest4() {
        assertEquals("yes", parser.getData().get("TKK_2").get("Bar"));
    }
}
