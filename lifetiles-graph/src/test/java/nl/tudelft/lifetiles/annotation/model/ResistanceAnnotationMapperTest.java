package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResistanceAnnotationMapperTest {
    private List<ResistanceAnnotation> annotations;
    private ResistanceAnnotation r1, r2, r3;
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
        annotations = new ArrayList<ResistanceAnnotation>();
        r1 = new ResistanceAnnotation(null, null, null, null, 5, null);
        r2 = new ResistanceAnnotation(null, null, null, null, 15, null);
        r3 = new ResistanceAnnotation(null, null, null, null, 16, null);
        annotations.add(r1);
        annotations.add(r2);
        annotations.add(r3);

        gf = FactoryProducer.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 11, new SegmentString("AAAAAAAAAA"));
        v2 = new SequenceSegment(s1, 11, 21, new SegmentString("AAAAAAAAAA"));
        v3 = new SequenceSegment(s1, 21, 31, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
    }

    @Test
    public void mapSingleNodePositiveGraphTest() {
        gr.addVertex(v1);
        Map<SequenceSegment, List<ResistanceAnnotation>> mappedAnnotations = ResistanceAnnotationMapper
                .mapAnnotations(gr, annotations, reference);
        assertEquals(1, mappedAnnotations.size());
        assertEquals(r1, mappedAnnotations.get(v1).get(0));
    }

    @Test
    public void mapSingleNodeNegativeGraphTest() {
        gr.addVertex(v3);
        Map<SequenceSegment, List<ResistanceAnnotation>> mappedAnnotations = ResistanceAnnotationMapper
                .mapAnnotations(gr, annotations, reference);
        assertEquals(0, mappedAnnotations.size());
    }

    @Test
    public void mapMultipleNodeGraphTest() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        Map<SequenceSegment, List<ResistanceAnnotation>> mappedAnnotations = ResistanceAnnotationMapper
                .mapAnnotations(gr, annotations, reference);
        assertEquals(2, mappedAnnotations.size());
        assertEquals(r1, mappedAnnotations.get(v1).get(0));
        assertEquals(r2, mappedAnnotations.get(v2).get(0));
    }

    @Test
    public void mapMultipleAssertionsNodeGraphTest() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        Map<SequenceSegment, List<ResistanceAnnotation>> mappedAnnotations = ResistanceAnnotationMapper
                .mapAnnotations(gr, annotations, reference);
        assertEquals(2, mappedAnnotations.size());
        assertEquals(r1, mappedAnnotations.get(v1).get(0));
        assertEquals(r2, mappedAnnotations.get(v2).get(0));
        assertEquals(r3, mappedAnnotations.get(v2).get(1));
    }

    @Test
    public void mapBoundaryGraphTest() {
        ResistanceAnnotation r4 = new ResistanceAnnotation(null, null, null, null, 11, null);
        annotations = new ArrayList<ResistanceAnnotation>();
        annotations.add(r4);
        gr.addVertex(v1);
        gr.addVertex(v2);
        Map<SequenceSegment, List<ResistanceAnnotation>> mappedAnnotations = ResistanceAnnotationMapper
                .mapAnnotations(gr, annotations, reference);
        assertEquals(1, mappedAnnotations.size());
        assertEquals(r4, mappedAnnotations.get(v2).get(0));
    }
}
