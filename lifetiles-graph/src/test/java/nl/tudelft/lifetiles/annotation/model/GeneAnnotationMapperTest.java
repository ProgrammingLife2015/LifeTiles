package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.traverser.UnifiedPositionTraverser;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneAnnotationMapperTest {
    static final String testGenomesFilename = "/data/test_annotations/simple_annotations.gff";
    static final String testGenomeFilename = "/data/test_annotations/simple_annotation.gff";
    private Set<GeneAnnotation> genomes;

    GraphFactory<SequenceSegment> gf;
    static Set<Sequence> s1;
    static DefaultSequence reference;
    SequenceSegment v1, v2, v3;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        reference = new DefaultSequence("reference");
        s1 = new HashSet<Sequence>();
        s1.add(reference);
    }

    @Before
    public void setUp() throws Exception {
        gf = FactoryProducer.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 10001, new SegmentString("AAAAAAAAAA"));
        v2 = new SequenceSegment(s1, 10001, 20001, new SegmentString(
                "AAAAAAAAAA"));
        v3 = new SequenceSegment(s1, 20001, 30001, new SegmentString(
                "AAAAAAAAAA"));
        gr = gf.getGraph();

        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);

        gr.addEdge(v1, v2);
        gr.addEdge(v2, v3);
        UnifiedPositionTraverser.unifyGraph(gr);

        File genomesFile = new File(this.getClass()
                .getResource(testGenomesFilename).toURI());

        genomes = GeneAnnotationParser.parseGeneAnnotations(genomesFile);
    }

    @Test
    public void mapAnnotationsTest() throws URISyntaxException, IOException {
        File genomeFile = new File(this.getClass()
                .getResource(testGenomeFilename).toURI());

        Set<GeneAnnotation> genome = GeneAnnotationParser.parseGeneAnnotations(genomeFile);
        
        Map<SequenceSegment, List<GeneAnnotation>> mappedAnnotations = GeneAnnotationMapper
                .mapAnnotations(gr, genome, reference);
        assertEquals(1, mappedAnnotations.size());
        assertEquals(1, mappedAnnotations.get(v1).get(0).getUnifiedPosition());
    }

}
