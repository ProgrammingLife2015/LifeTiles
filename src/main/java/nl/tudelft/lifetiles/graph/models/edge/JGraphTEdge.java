package nl.tudelft.lifetiles.graph.models.edge;

import org.jgrapht.graph.DefaultEdge;

/**
 * Implementation of a directed edge.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTEdge<V> implements Edge<V> {
    /**
     * The internal edge.
     */
    private DefaultEdge internalEdge;

    /**
     * @param e
     *            The internal edge to use.
     */
    public JGraphTEdge(final DefaultEdge e) {
        internalEdge = e;
    }

    /*
     * (non-Javadoc)
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
        if (!(obj instanceof JGraphTEdge)) {
            return false;
        }
        JGraphTEdge<?> other = (JGraphTEdge<?>) obj;
        if (internalEdge == null) {
            if (other.internalEdge != null) {
                return false;
            }
        } else if (!internalEdge.equals(other.internalEdge)) {
            return false;
        }
        return true;
    }

    /**
     * @return the internalEdge
     */
    public final DefaultEdge getInternalEdge() {
        return internalEdge;
    }

    /*
     * (non-Javadoc)
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

    /**
     * @param newInternalEdge
     *            the internalEdge to set
     */
    protected final void setInternalEdge(final DefaultEdge newInternalEdge) {
        this.internalEdge = newInternalEdge;
    }
}
