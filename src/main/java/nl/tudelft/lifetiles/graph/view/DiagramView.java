package nl.tudelft.lifetiles.graph.view;

import java.util.Map;

import javafx.scene.Group;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.StackedMutationContainer;

/**
 * Diagram view which contains a view of the stacked mutation diagram, which is
 * a stacked representation of the percentage of mutations in a bucket or a
 * merged bucket.
 * 
 * @author Jos
 *
 */
public class DiagramView {

    /**
     * Controller which holds the graph.
     */
    private GraphController controller;

    /**
     * Constructs a DiagramView.
     * 
     * @param control
     *            The controller which holds the graph.
     */
    DiagramView(final GraphController control) {
        controller = control;
    }

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

        StackedMutationContainer stacks = container.mapLevelStackedMutation()
                .get(zoomLevel);
        for (int index = 0; index < stacks.getStack().size(); index++) {
            StackView stackView = new StackView(stacks.getStack().get(index));
            stackView.setLayoutX(index * StackView.HORIZONTAL_SCALE);
            root.getChildren().add(stackView);
        }
        return root;
    }
}
