package nl.tudelft.lifetiles.graph.sequence.mutation;

import javafx.scene.paint.Color;

/**
 * Insertion Mutation type of Mutation annotations.
 * Sequence with content on absolute range where
 * reference sequence has no content.
 * @author Jos
 *
 */
public class InsertionMutation implements Mutation {

	private static final Color color = Color.web("8df08c");

	/**
	 * @return Tile color of the mutation type.
	 */
	@Override
	public Color getColor() {
		return color;
	}

}