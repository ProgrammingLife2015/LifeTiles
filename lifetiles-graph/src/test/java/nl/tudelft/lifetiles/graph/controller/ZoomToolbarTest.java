package nl.tudelft.lifetiles.graph.controller;

import static org.junit.Assert.assertEquals;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.junit.Test;

public class ZoomToolbarTest {

    @Test
    public void clickOnPlus() {
        // Hack because javafx toolkit need to be initialized
        JFXPanel panel = new JFXPanel();
        ZoomToolbar toolbar = new ZoomToolbar(10);

        ToolBar javafxBar = toolbar.getToolBar();
        int initZoom = toolbar.getZoomlevel().intValue();

        Button plus = (Button) javafxBar.getItems().get(0);

        Event.fireEvent(plus, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0,
                0, MouseButton.PRIMARY, 1, true, true, true, true, true, true,
                true, true, true, true, null));

        assertEquals(initZoom + 1, toolbar.getZoomlevel().intValue(), 1e-10);
    }

    @Test
    public void clickOnMinus() {
        // Hack because javafx toolkit need to be initialized
        JFXPanel panel = new JFXPanel();
        ZoomToolbar toolbar = new ZoomToolbar(10);

        ToolBar javafxBar = toolbar.getToolBar();
        int initZoom = toolbar.getZoomlevel().intValue();

        Button minus = (Button) javafxBar.getItems().get(2);

        Event.fireEvent(minus, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0,
                0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true,
                true, true, true, true, true, null));

        assertEquals(initZoom - 1, toolbar.getZoomlevel().intValue(), 1e-10);
    }

    @Test
    public void scrolloneUpSlider() {
        // Hack because javafx toolkit need to be initialized
        JFXPanel panel = new JFXPanel();
        ZoomToolbar toolbar = new ZoomToolbar(10);

        ToolBar javafxBar = toolbar.getToolBar();
        int initZoom = toolbar.getZoomlevel().intValue();

        Slider slider = (Slider) javafxBar.getItems().get(1);

        slider.valueProperty().set(slider.valueProperty().get() + 1);

        assertEquals(initZoom + 1, toolbar.getZoomlevel().intValue(), 1e-10);
    }

    @Test
    public void scrolloneDownSlider() {
        // Hack because javafx toolkit need to be initialized
        JFXPanel panel = new JFXPanel();
        ZoomToolbar toolbar = new ZoomToolbar(10);

        ToolBar javafxBar = toolbar.getToolBar();
        int initZoom = toolbar.getZoomlevel().intValue();

        Slider slider = (Slider) javafxBar.getItems().get(1);
        slider.valueProperty().set(slider.valueProperty().get() - 1);

        assertEquals(initZoom - 1, toolbar.getZoomlevel().intValue(), 1e-10);
    }
}
