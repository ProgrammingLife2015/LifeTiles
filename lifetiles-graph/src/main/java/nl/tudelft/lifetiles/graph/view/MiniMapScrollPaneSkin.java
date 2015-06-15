package nl.tudelft.lifetiles.graph.view;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

/**
 * ScrollPane skin capable of customizing the scrollbar.
 *
 * @author Joren Hammudoglu
 */
@SuppressWarnings("restriction")
public class MiniMapScrollPaneSkin extends ScrollPaneSkin {

    /**
     * Create a new MiniMap ScrollPane skin.
     *
     * @param scrollpane
     *            the scrollpane
     */

    public MiniMapScrollPaneSkin(final ScrollPane scrollpane) {
        super(scrollpane);
    }

    /**
     * @return the horizontal scrollbar
     */
    public final ScrollBar getHorizontalScrollBar() {
        return super.hsb;
    }

    /**
     * Set the horizontal scrollbar.
     *
     * @param scrollbar
     *            the scrollbar
     */
    public final void setHorizontalScrollbar(final ScrollBar scrollbar) {
        super.hsb = scrollbar;
    }

}
