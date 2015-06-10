package nl.tudelft.lifetiles.graph.view;

import javafx.application.Application;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.BucketCache;
import nl.tudelft.lifetiles.graph.model.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.model.StackedMutationContainer;
import nl.tudelft.lifetiles.graph.traverser.EmptySegmentTraverser;
import nl.tudelft.lifetiles.graph.traverser.MutationIndicationTraverser;
import nl.tudelft.lifetiles.graph.traverser.ReferencePositionTraverser;
import nl.tudelft.lifetiles.graph.traverser.UnifiedPositionTraverser;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Diagram view which contains a view of the stacked mutation diagram, which is
 * a stacked representation of the percentage of mutations in a bucket or a
 * merged bucket.
 * 
 * @author Jos
 *
 */
public class DiagramView {

    
    static final double SPACING = 2;
	/**
	 * Controller which holds the graph.
	 */
	private GraphController controller;
	private Map<Integer, StackedMutationContainer> containers;
	private int zoomLevel;
	
	/**
	 * Draws the diagram view to the screen.
	 * 
	 * @param container
	 * @param zoomLevel
	 * @return
	 */
	public Group drawDiagram(StackedMutationContainer container, int zoomLevel) {
		containers = container.mapLevelStackedMutation();
		Group root = new Group();

		int scale = 4;
		StackedMutationContainer stack = containers.get(scale);
		for (int index = 0; index < stack.getStack().size(); index++) {
			StackView stackView = new StackView(stack.getStack().get(index), zoomLevel, stack.getMaxMutations());
			stackView.setLayoutX((index * (SPACING * zoomLevel + StackView.HORIZONTAL_SCALE * zoomLevel)));
			root.getChildren().add(stackView);
		}
		return root;
	}
}
