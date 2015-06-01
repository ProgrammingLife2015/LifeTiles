package nl.tudelft.lifetiles.annotation.model;

/**
 * Resistance annotation which annotates a drug resistant mutation onto a
 * sequence.
 *
 * @author Jos
 *
 */
public class ResistanceAnnotation extends Annotation {

    /**
     * Name of the gene.
     */
    private final String geneName;

    /**
     * Type of the mutation, defined by client.
     */
    private final String typeOfMutation;

    /**
     * Change, defined by client.
     */
    private final String change;

    /**
     * Filter, defined by client.
     */
    private final String filter;

    /**
     * Drug resistance, which drug is mutation resistant to.
     */
    private final String drugResistance;

    /**
     * Construct a resistance annotation.
     * 
     * @param geneName
     *            Name of the gene.
     * @param typeOfMutation
     *            Type of the mutation, defined by client.
     * @param change
     *            Change, defined by client.
     * @param filter
     *            Filter, defined by client.
     * @param genomePosition
     *            Position of the annotation on the genome.
     * @param drugResistance
     *            Drug resistance, which drug is mutation resistant to.
     */
    public ResistanceAnnotation(final String geneName,
            final String typeOfMutation, final String change,
            final String filter, final long genomePosition,
            final String drugResistance) {
        super(genomePosition);
        this.geneName = geneName;
        this.typeOfMutation = typeOfMutation;
        this.change = change;
        this.filter = filter;
        this.drugResistance = drugResistance;
    }

}
