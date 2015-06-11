package nl.tudelft.lifetiles.graph.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;

/**
 * Creates a new Toolbar with two buttons and a slider.
 * These can be used to zoom.
 *
 * @author AC Langerak
 *
 */
public class ZoomToolbar {

    /**
     * The javafx toolbar.
     */
    private final ToolBar toolbar;

    /**
     * Integer which holds the current selected zoomlevel.
     * Can be listened to.
     */
    private final IntegerProperty zoomLevel;

    /**
     * The maximal amount of zooming.
     */
    private final int maxzoom;

    /**
     * Create a new Toolbar.
     *
     * @param zoom
     *            the maximal value of the zoom
     */
    public ZoomToolbar(final int zoom) {
        maxzoom = zoom;
        zoomLevel = new SimpleIntegerProperty();
        // 5 is temporary, until graph can start at the highest zoom level
        zoomLevel.set(5);
        Slider slider = createSlider();
        slider.getStyleClass().add("slider");
        slider.valueProperty().addListener(
                (ChangeListener<Number>) (obserVal, oldVal, newVal) -> {
                    if (oldVal.intValue() > newVal.intValue()
                            && newVal.intValue() <= maxzoom) {
                        zoomLevel.set(newVal.intValue());
                    }
                    if (oldVal.intValue() < newVal.intValue()) {
                        zoomLevel.set(newVal.intValue());
                    }
                });

        Button plus = new Button("+");
        plus.getStyleClass().add("plusButton");
        plus.setOnMouseClicked((event) -> {
            slider.setValue(slider.getValue() - slider.getMajorTickUnit());
        });

        Button minus = new Button("-");
        minus.getStyleClass().add("minusButton");
        minus.setOnMouseClicked((event) -> {
            slider.setValue(slider.getValue() + slider.getMajorTickUnit());
        });

        toolbar = new ToolBar();
        toolbar.getStyleClass().add("toolbar");

        toolbar.getItems().addAll(minus, slider, plus);
        toolbar.setOrientation(Orientation.VERTICAL);
    }

    /**
     *
     * @return javafx toolbar that is constructed
     */
    public final ToolBar getToolBar() {
        return toolbar;
    }

    /**
     * @return the zoomlevel property
     */
    public final IntegerProperty getZoomlevel() {
        return zoomLevel;
    }

    /**
     * Create a new Slider which runs from 0 to MAXZOOM.
     * The labeling will be in the opposite direction so MAXZOOM to 0
     *
     * @return the new slider
     */
    private Slider createSlider() {
        final CustomLabelSlider slider = new CustomLabelSlider(value -> Math
                .abs(value - maxzoom));

        slider.setOrientation(Orientation.VERTICAL);
        slider.setMin(0);
        slider.setValue(zoomLevel.intValue());
        slider.setMax(maxzoom);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setMinHeight(Slider.USE_PREF_SIZE);

        slider.valueChangingProperty().addListener(observable -> {
            slider.setValue(Math.round(slider.getValue()));

        });

        return slider;
    }

}
