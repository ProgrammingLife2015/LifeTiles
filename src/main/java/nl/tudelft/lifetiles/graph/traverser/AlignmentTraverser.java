package nl.tudelft.lifetiles.graph.traverser;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import nl.tudelft.lifetiles.graph.Edge;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.sequence.Sequence;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

/**
 * Aligns sequences in a graph of sequences, by filling empty space with empty vertices.
 * @author Jos
 */
public class AlignmentTraverser implements GraphTraverser<SequenceSegment> {

	/**
	 * Traverse a graph and return the aligned graph.
	 * @param graph
	 *			The graph that is being aligned.
	 * @return aligned graph.
	 */
	@Override
	public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
		for (SequenceSegment source : new HashSet<SequenceSegment>(graph.getSource())) {
			traverseVertex(graph, source);
		}
		return graph;
	}

	/**
	 * Traverse a vertex in the graph.
	 * @param graph
	 *			The graph that can be modified on.
	 * @param vertex
	 *			The vertex that is being traversed.
	 */
	private void traverseVertex(Graph<SequenceSegment> graph, SequenceSegment vertex) {
	    HashSet<Sequence> buffer = setCopy(vertex.getSources());
		PriorityQueue<SortedEdge> it = getSortedEdges(graph, graph.getOutgoing(vertex));
		while(!it.isEmpty()) {
		    SortedEdge edge = it.poll();
			SequenceSegment destination = edge.getSegment();
	        HashSet<Sequence> sources = setCopy(destination.getSources());
	        sources.retainAll(buffer);
			if (vertex.distanceTo(destination) > 1) {
				graph.divideEdge(edge.getEdge(), bridgeSequence(vertex, destination, sources));
			}
			buffer.removeAll(sources);
	        
		}
	}
	
	private PriorityQueue<SortedEdge> getSortedEdges(Graph<SequenceSegment> graph, Set<Edge<SequenceSegment>> edges) {
	    PriorityQueue<SortedEdge> it = new PriorityQueue<SortedEdge>();
	    for (Edge<SequenceSegment> edge : edges) {
	        it.add(new SortedEdge(edge, graph.getDestination(edge)));
        }
	    return it;
    }

    /**
	 * Helper method which copies a Set into a HashSet.
	 * Pass by value instead of pass by reference.
	 * @param set
     *          The set that must be copied.
	 * @return
     *          The copied set.
	 */
	private HashSet<Sequence> setCopy(Set<Sequence> set) {
	    if (set == null) {
	        return new HashSet<Sequence>();
	    }
        return  new HashSet<Sequence>(set);
	}
	
	/**
	 * Creates a bridge sequence segment between a source and destination segment.
	 * @param source
	 *			The vertex that is the source segment.
	 * @param destination
	 *			The vertex that is the destination segment.
	 * @param sources 
	 * @return sequence segment between source and destination segment
	 */
	private SequenceSegment bridgeSequence(SequenceSegment source, SequenceSegment destination, HashSet<Sequence> sources) {
		return new SequenceSegment(
			sources,
			source.getEnd() + 1,
			destination.getStart() - 1,
			new SegmentEmpty(source.distanceTo(destination))
		);
	}
}

/**
 * Temporary class.
 * @author Jos
 *
 */
class SortedEdge implements Comparable<SortedEdge> {

    private Edge<SequenceSegment> edge;
    private SequenceSegment segment;

    public SortedEdge(Edge<SequenceSegment> edge, SequenceSegment segment) {
        this.edge = edge;
        this.segment = segment;
    }
    
    public Edge<SequenceSegment> getEdge() {
        return edge;
    }

    @Override
    public int compareTo(SortedEdge other) {
        return  ((Long) other.getSegment().getAbsStart()).compareTo((Long) this.segment.getAbsStart());
    }

    public SequenceSegment getSegment() {
        return segment;
    }
    
    public String toString() {
        return segment.getSources().toString();
    }
    
}