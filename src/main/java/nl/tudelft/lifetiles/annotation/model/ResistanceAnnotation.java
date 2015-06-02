package nl.tudelft.lifetiles.annotation.model;

import java.util.Set;

import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Resistance annotation which annotates a drug resistant mutation onto a
 * sequence.
 *
 * @author Jos
 *
 */
public class ResistanceAnnotation extends AbstractAnnotation {

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

    /**
     * @return the drugResistance
     */
    public String getDrugResistance() {
        return drugResistance;
    }

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @return the change
     */
    public String getChange() {
        return change;
    }

    /**
     * @return the typeOfMutation
     */
    public String getTypeOfMutation() {
        return typeOfMutation;
    }

    /**
     * @return the geneName
     */
    public String getGeneName() {
        return geneName;
    }

    /**
     * TODO: Figure out a way to map resistance annotations onto a graph or list
     * of segments.
     * Method which maps this annotation to a sequence.
     *
     * @param segments
     *            Segments to map the annotation to.
     * @param reference
     *            The current reference used in the list of segments.
     * @return segment which annotation should be mapped to.
     */
    @Override
    public SequenceSegment mapOntoSequence(
            final Set<SequenceSegment> segments, final Sequence reference) {
        return null;
    }

}
