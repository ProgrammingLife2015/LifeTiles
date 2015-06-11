package nl.tudelft.lifetiles.graph.controller;

import java.util.function.DoubleFunction;

import javafx.scene.control.Slider;
import javafx.util.StringConverter;

/**
 * Create a new Slider with custom labe values.
 *
 * @author AC Langerak
 *
 */
public class CustomLabelSlider extends Slider {

    /**
     * Creates a Slider where the values on the slider will use the function
     * given to determine the label values.
     *
     * @param function
     *            function to map the slider values to label values (e.g x ->
     *            x+1 offsets the values on the labels by one compared to the
     *            slider values)
     */
    public CustomLabelSlider(final DoubleFunction<Double> function) {

        super();

        // for 'snapping' to the values
        valueProperty().addListener(
                (observable, oldVal, newVal) -> setValue(newVal.intValue()));

        setLabelFormatter(new StringConverter<Double>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString(final Double number) {
                return String.format("%1$.0f", function.apply(number));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Double fromString(final String string) {
                return function.apply(Double.parseDouble(string));
            }
        });
    }
}
