package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GeneAnnotationParserTest {
    static final String testGenomesFilename = "/data/test_annotations/simple_annotations.gff";
    static final String testGenomeFilename = "/data/test_annotations/simple_annotation.gff";
    private Set<GeneAnnotation> genomes;
    private Set<GeneAnnotation> genome;

    @Before
    public void setUp() throws Exception {
        File genomesFile = new File(this.getClass()
                .getResource(testGenomesFilename).toURI());
        File genomeFile = new File(this.getClass()
                .getResource(testGenomeFilename).toURI());

        genomes = GeneAnnotationParser.parseGeneAnnotations(genomesFile);
        genome = GeneAnnotationParser.parseGeneAnnotations(genomeFile);
    }

    @Test
    public void parseGenomeTotalTest() throws Exception {
        assertEquals(2, genomes.size());
    }

    @Test
    public void parseGenomeSingleTest() throws Exception {        
        GeneAnnotation gene = genome.iterator().next();
        assertEquals("test1", gene.getName());
        assertEquals(1, gene.getGenomePosition());
        assertEquals(1000, gene.getGenomeEndPosition());
    }
}
