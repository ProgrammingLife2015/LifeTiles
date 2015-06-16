package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GeneAnnotationParserTest {
    static final String testGenomeFilename = "/data/test_annotations/simple_annotations";
    private Map<String, GeneAnnotation> genomes;

    @Before
    public void setUp() throws Exception {
        File genomeFile = new File(this.getClass()
                .getResource(testGenomeFilename + ".gff").toURI());

        genomes = GeneAnnotationParser.parseGeneAnnotations(genomeFile);
    }

    @Test
    public void parseGenomeTotalTest() throws Exception {
        assertEquals(2, genomes.size());
    }

    @Test
    public void parseGenomeSingleTest() throws Exception {
        GeneAnnotation genome = genomes.get("test1");
        assertEquals("test1", genome.getName());
        assertEquals(1, genome.getGenomePosition());
        assertEquals(1000, genome.getGenomeEndPosition());
    }
}
