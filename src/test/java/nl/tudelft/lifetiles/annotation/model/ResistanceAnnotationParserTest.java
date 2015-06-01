package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ResistanceAnnotationParserTest {
    static final String testAnnotationFilename = "/data/test_annotations/simple_annotations";
    private List<ResistanceAnnotation> annotations;

    @Before
    public void setUp() throws Exception {
        ResistanceAnnotationParser parser = new ResistanceAnnotationParser();
        File annotationFile = new File(this.getClass()
                .getResource(testAnnotationFilename + ".txt").toURI());
        
        annotations = parser
                .parseAnnotations(annotationFile);
    }
    
    @Test
    public void parseAnnotationsTotalTest() throws Exception {
        assertEquals(10, annotations.size());
    }
    
    @Test
    public void parseAnnotationsSingleTest() throws Exception {
        ResistanceAnnotation annotation = annotations.get(0);
        assertEquals("test1", annotation.getGeneName());
        assertEquals("mut1", annotation.getTypeOfMutation());
        assertEquals("NNN", annotation.getChange());
        assertEquals("pass", annotation.getFilter());
        assertEquals(10, annotation.getGenomePosition());
        assertEquals("A", annotation.getDrugResistance());
        
    }
}
