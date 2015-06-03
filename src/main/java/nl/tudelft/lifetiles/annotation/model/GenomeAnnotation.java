package nl.tudelft.lifetiles.annotation.model;

/**
 * Annotation of a genome onto the graph.
 * 
 * @author Jos
 *
 */
public class GenomeAnnotation extends AbstractDomainAnnotation {

    /**
     * Construct a genome annotation.
     * 
     * @param start
     *            Start coordinate of the domain annotation.
     * @param end
     *            End coordinate of the domain annotation.
     * @param name
     *            Name of the genome.
     */
    public GenomeAnnotation(final long start, final long end, final String name) {
        super(start, end);
    }

}
