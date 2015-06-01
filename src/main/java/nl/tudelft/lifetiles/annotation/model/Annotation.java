package nl.tudelft.lifetiles.annotation.model;

/**
 * Abstract class for Annotation. An annotation is a point on the genome with
 * some information attached to it.
 *
 * @author Jos
 *
 */
public abstract class Annotation {

    /**
     * Position of the annotation on the genome.
     */
    private final long genomePosition;

    /**
     * Constructs an annotation.
     *
     * @param genomePosition
     *            Position of the annotation on the genome.
     */
    protected Annotation(final long genomePosition) {
        this.genomePosition = genomePosition;
    }

    /**
     * Returns the position of the annotation on the genome.
     *
     * @return position of the annotation on the genome.
     */
    public final long getGenomePosition() {
        return genomePosition;
    }
}
