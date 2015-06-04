package nl.tudelft.lifetiles.annotation.model;

import java.util.Formatter;
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
     * Maps resistance annotations onto a set of segments.
     *
     * @param segments
     *            Segments to map the annotation to.
     * @param reference
     *            The current reference used in the list of segments.
     * @return segment which annotation should be mapped to.
     */
    @Override
    public SequenceSegment mapOntoSequence(final Set<SequenceSegment> segments,
            final Sequence reference) {
        for (SequenceSegment segment : segments) {
            if (segment.getSources().contains(reference)
                    && segment.getStart() <= getGenomePosition()
                    && segment.getEnd() > getGenomePosition()) {
                return segment;
            }
        }
        return null;
    }

    /**
     * Returns the String representation for the annotation to be displayed in
     * the tooltip of it's bookmark.
     * 
     * @return
     *         Tooltip string representation.
     */
    public String toString() {
        Formatter formatter = new Formatter();
        formatter
                .format("Gene Name: %1$s%nGene Position: %2$s%nMutation Type: %3$s%nChange: %4$s%nFilter: %5$s%nDrug Resistance: %6$s",
                        geneName, getGenomePosition(), typeOfMutation, change,
                        filter, drugResistance);
        String annotation = formatter.toString();
        formatter.close();
        return annotation;
    }

}
