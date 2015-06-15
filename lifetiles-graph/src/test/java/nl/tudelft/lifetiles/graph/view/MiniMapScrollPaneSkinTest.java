package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import org.junit.Before;
import org.junit.Test;

public class MiniMapScrollPaneSkinTest {

    private ScrollBar scrollBar;
    private MiniMapScrollPaneSkin skin;

    @Before
    public void setUp() {
        new JFXPanel(); // force to initialize Toolkit

        scrollBar = new ScrollBar();
        scrollBar.setStyle("test");
        skin = new MiniMapScrollPaneSkin(new ScrollPane());
    }

    @Test
    public void setGetTest() {
        skin.setHorizontalScrollbar(scrollBar);
        assertEquals("test", skin.getHorizontalScrollBar().getStyle());
    }

}
