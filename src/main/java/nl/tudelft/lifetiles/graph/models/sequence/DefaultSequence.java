package nl.tudelft.lifetiles.graph.models.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains an entire sequence.
 *
 * @author Rutger van den Berg
 */
public class DefaultSequence implements Sequence {
	/**
	 * Identifier for this sequence.
	 */
	private String ident;
	/**
	 * List of sequence segments related to this sequence.
	 */
	private List<SequenceSegment> sequenceList;

	/**
	 * @param identifier
	 *            The identifier for this sequence.
	 */
	public DefaultSequence(final String identifier) {
		ident = identifier;
		sequenceList = new ArrayList<>();
	}

	@Override
	public final void appendSegment(final SequenceSegment segment) {
		sequenceList.add(segment);
	}

	@Override
	public final String getIdentifier() {
		return ident;
	}

	@Override
	public final List<SequenceSegment> getSegments() {
		return sequenceList;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Sequence) {
			return ident.equals(((Sequence) other).getIdentifier());
		}
		return false;
	}

}
