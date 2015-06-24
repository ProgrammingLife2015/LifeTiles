package nl.tudelft.lifetiles.graph.controller;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
import nl.tudelft.lifetiles.graph.view.MiniMapScrollPaneSkin;

/**
 * The controller of the mini map.
 *
 * @author Joren Hammudoglu
 *
 */
public final class MiniMapController {

    /**
     * The scroll bar to display the minimap on.
     */
    private final ScrollBar scrollBar;

    /**
     * The graph model.
     */
    private final GraphContainer model;

    /**
     * Create a new mini map controller.
     *
     * @param scrollPane
     *            the scroll pane to display the minimap in.
     * @param model
     *            the graph model
     */
    public MiniMapController(final ScrollPane scrollPane,
            final GraphContainer model) {
        this.model = model;

        MiniMapScrollPaneSkin skin = new MiniMapScrollPaneSkin(scrollPane);
        scrollPane.setSkin(skin);
        scrollBar = skin.getHorizontalScrollBar();
    }

    /**
     * Draw the miniMap onto the horizontal scrollbar.
     */
    public void drawMiniMap() {
        Background background = getMiniMapBackground();
        scrollBar.setBackground(background);
    }

    /**
     * Get the minimap scrollbar.
     *
     * @return the scrollbar
     */
    public ScrollBar getMiniMap() {
        assert scrollBar != null;
        return scrollBar;
    }

    /**
     * Gets the MiniMap background gradient.
     *
     * @return the minimap background
     */
    private Background getMiniMapBackground() {
        double buttonWidth = javafx.scene.text.Font.getDefault().getSize();
        Insets insets = new Insets(0.0, buttonWidth, 0.0, buttonWidth);
        return new Background(new BackgroundFill(getGradient(), null, insets));
    }

    /**
     * Get the generated linear gradient from the bucket interestingness.
     *
     * @return the {@link LinearGradient}.
     */
    private LinearGradient getGradient() {
        List<Stop> stops = model.getMiniMap().getStops();

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE, stops);

        return gradient;
    }

}
