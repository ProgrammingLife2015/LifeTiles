package nl.tudelft.lifetiles.annotation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Static class which maps a list of gene annotations to a graph and
 * returns a map which maps a segment to a list of gene annotations.
 *
 * @author Jos
 *
 */
public final class GeneAnnotationMapper {

    /**
     * A static class can't have a public or default constructor.
     */
    private GeneAnnotationMapper() {
        // noop
    }

    /**
     * Maps a list of annotations to a graph.
     *
     * @param graph
     *            Graph to annotate the annotations onto.
     * @param genomes
     *            List of gene annotations to map.
     * @param reference
     *            Reference to map to, resistanceAnnotations only can map to the
     *            reference sequence.
     * @return Map which maps segments to a list of resistance annotations.
     */
    public static Map<SequenceSegment, List<GeneAnnotation>> mapAnnotations(
            final Graph<SequenceSegment> graph,
            final List<GeneAnnotation> genomes, final Sequence reference) {
        Set<SequenceSegment> segments = selectReference(graph, reference);
        Map<SequenceSegment, List<GeneAnnotation>> annotatedSegments = new HashMap<>();
        for (GeneAnnotation geneAnnotation : genomes) {
            Set<SequenceSegment> mappedSegments = geneAnnotation
                    .mapOntoSequence(segments, reference);
            for (SequenceSegment segment : mappedSegments) {
                if (!annotatedSegments.containsKey(segment)) {
                    // We do actually need to instantiate here.
                    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
                    List<GeneAnnotation> newAnnotations = new ArrayList<>();
                    annotatedSegments.put(segment, newAnnotations);
                }
                annotatedSegments.get(segment).add(geneAnnotation);
            }
        }
        return annotatedSegments;
    }

    /**
     * Selects the segments in the graph which are part of the reference
     * sequence and returns them in a list.
     *
     * @param graph
     *            The graph to be searched for reference segments.
     * @param reference
     *            The reference to search for in the segments.
     * @return set of reference segments in the graph.
     */
    private static Set<SequenceSegment> selectReference(
            final Graph<SequenceSegment> graph, final Sequence reference) {
        Set<SequenceSegment> segments = new TreeSet<>();
        for (SequenceSegment segment : graph.getAllVertices()) {
            if (segment.getSources().contains(reference)) {
                segments.add(segment);
            }
        }
        return segments;
    }

}
