package nl.tudelft.lifetiles.annotation.model;

/**
 * Annotation of a gene onto the graph.
 *
 * @author Jos
 *
 */
public class GeneAnnotation extends AbstractAnnotation {

    /**
     * Name of the gene.
     */
    private final String name;

    /**
     * Construct a gene annotation.
     *
     * @param start
     *            Start coordinate of the domain annotation.
     * @param end
     *            End coordinate of the domain annotation.
     * @param name
     *            Name of the gene.
     */
    public GeneAnnotation(final long start, final long end, final String name) {
        super(start, end);
        this.name = name;
    }

    /**
     * @return name of the gene.
     */
    public String getName() {
        return name;
    }

}
