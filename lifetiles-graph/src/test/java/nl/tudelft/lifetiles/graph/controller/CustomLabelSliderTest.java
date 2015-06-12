package nl.tudelft.lifetiles.graph.controller;

import static org.junit.Assert.assertEquals;

import java.util.function.DoubleFunction;

import javafx.embed.swing.JFXPanel;

import org.junit.Test;

public class CustomLabelSliderTest {

    @Test
    public void formatterTest() {
        DoubleFunction<Double> function = x -> x + 1;

        // Hack because javafx toolkit need to be initialized
        JFXPanel panel = new JFXPanel();
        CustomLabelSlider slider = new CustomLabelSlider(function);
        assertEquals("6", slider.getLabelFormatter().toString(5d));

    }

}
