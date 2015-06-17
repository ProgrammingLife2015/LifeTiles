package nl.tudelft.lifetiles.graph.model.jgrapht;

import nl.tudelft.lifetiles.graph.model.Edge;

import org.jgrapht.graph.DefaultEdge;

/**
 * Implementation of a directed edge.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTEdgeAdapter<V> implements Edge<V> {
    /**
     * The internal edge.
     */
    private DefaultEdge internalEdge;

    /**
     * @param edge
     *            The internal edge to use.
     */
    public JGraphTEdgeAdapter(final DefaultEdge edge) {
        internalEdge = edge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
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

    /**
     * @return the internalEdge
     */
    public DefaultEdge getInternalEdge() {
        return internalEdge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
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
