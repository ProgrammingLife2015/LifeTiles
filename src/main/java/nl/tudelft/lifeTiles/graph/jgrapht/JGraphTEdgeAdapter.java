package nl.tudelft.lifetiles.graph.jgrapht;

import nl.tudelft.lifetiles.graph.Edge;

import org.jgrapht.graph.DefaultEdge;

/**
 * @author Rutger van den Berg Implementation of a directed edge.
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTEdgeAdapter<V> implements Edge<V> {
    /**
     * The internal edge.
     */
    private DefaultEdge internalEdge;

    /**
     * @param e
     *            The internal edge to use.
     */
    JGraphTEdgeAdapter(final DefaultEdge e) {
        internalEdge = e;
    }

    /**
     * @return the internalEdge
     */
    protected final DefaultEdge getInternalEdge() {
        return internalEdge;
    }

    /**
     * @param newInternalEdge
     *            the internalEdge to set
     */
    protected final void setInternalEdge(final DefaultEdge newInternalEdge) {
        this.internalEdge = newInternalEdge;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (internalEdge != null) {
            result += internalEdge.hashCode();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof JGraphTEdgeAdapter)) {
            return false;
        }
        JGraphTEdgeAdapter<?> other = (JGraphTEdgeAdapter<?>) obj;
        if (internalEdge == null) {
            if (other.internalEdge != null) {
                return false;
            }
        } else if (!internalEdge.equals(other.internalEdge)) {
            return false;
        }
        return true;
    }
}
