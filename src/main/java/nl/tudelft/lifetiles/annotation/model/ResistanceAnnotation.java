package nl.tudelft.lifetiles.annotation.model;

import java.util.Formatter;
import java.util.Map;
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
public class ResistanceAnnotation {

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
     * Position of the annotation on the genome.
     */
    private final long genomePosition;

    /**
     * SequenceSegment this resistance annotation is mapped to. Needed for
     * feature: jumping to segment in the view.
     */
    private SequenceSegment segment;

    /**
     * Genome annotation this resistance is belonging to.
     */
    private GenomeAnnotation genome;

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
        this.genomePosition = genomePosition;
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
    public SequenceSegment mapOntoSequence(final Set<SequenceSegment> segments,
            final Sequence reference) {
        if (genome != null) {
            for (SequenceSegment segment : segments) {
                if (segment.getSources().contains(reference)
                        && !segment.getContent().isEmpty()
                        && segment.getStart() <= getPosition()
                        && segment.getEnd() > getPosition()) {
                    this.segment = segment;
                    return segment;
                }
            }
        }
        return null;
    }

    /**
     * Maps genome annotation onto this resistance annotation.
     * 
     * @param genomes
     *            Map of annotations by genomeName.
     */
    public void mapOntoGenome(final Map<String, GenomeAnnotation> genomes) {
        if (genomes.containsKey(geneName)) {
            genome = genomes.get(geneName);
        }
    }

    /**
     * Returns the position of the annotation on the genome.
     *
     * @return position of the annotation on the genome.
     */
    public final long getGenomePosition() {
        return genomePosition;
    }

    /**
     * Returns the position of the resistance annotation on the sequence.
     * 
     * @return
     *         Position of the resistance annotation on the sequence.
     */
    private long getPosition() {
        return getGenomePosition() + genome.getStart();
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
                .format("Gene Name: %1$s%nGene Position: %2$s%nMutation Type: %3$s%nGene Name: %4$s%nChange: %5$s%nFilter: %6$s%nDrug Resistance %7$s",
                        geneName, getGenomePosition(), typeOfMutation,
                        geneName, change, filter, drugResistance);
        String annotation = formatter.toString();
        formatter.close();
        return annotation;
    }

}
