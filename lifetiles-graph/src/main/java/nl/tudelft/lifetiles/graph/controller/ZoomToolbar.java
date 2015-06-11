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
    private IntegerProperty zoomLevel;

    /**
     * The maximal amount of zooming.
     */
    private final int maxzoom;

    /**
     * Create a new Toolbar.
     */
    public ZoomToolbar(final int zoom) {
        maxzoom = zoom;
        zoomLevel = new SimpleIntegerProperty();
        zoomLevel.set(5);
        Slider slider = createSlider();
        slider.valueProperty().addListener(
                (ChangeListener<Number>) (ov, old_val, new_val) -> {
                    if (old_val.intValue() > new_val.intValue()
                            && new_val.intValue() <= maxzoom) {
                        zoomLevel.set(new_val.intValue());
                    }
                    if (old_val.intValue() < new_val.intValue()) {
                        zoomLevel.set(new_val.intValue());
                    }
                });

        Button plus = new Button("+");
        plus.setOnMouseClicked((event) -> {
            slider.setValue(slider.getValue() - slider.getMajorTickUnit());
        });

        Button minus = new Button("-");
        minus.setOnMouseClicked((event) -> {
            slider.setValue(slider.getValue() + slider.getMajorTickUnit());
        });

        toolbar = new ToolBar();
        toolbar.getItems().addAll(minus, slider, plus);
        toolbar.setOrientation(Orientation.VERTICAL);
    }

    /**
     *
     * @return javafx toolbar that is constructed
     */
    public ToolBar getToolBar() {
        return toolbar;
    }

    /**
     * @return the zoomlevel property
     */
    public IntegerProperty getZoomlevel() {
        return zoomLevel;
    }

    /**
     * Create a new Slider which runs from 0 to MAXZOOM.
     * The labeling will be in the opposite direction so MAXZOOM to 0
     *
     * @return the new slider
     */
    private Slider createSlider() {
        final CustomLabelSlider slider = new CustomLabelSlider(x -> Math
                .abs(x - 10));

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
