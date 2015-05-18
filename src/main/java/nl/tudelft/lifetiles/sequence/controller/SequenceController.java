package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.core.controller.ViewController;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.view.SequenceColor;

/**
 * The controller of the data view.
 *
 * @author Joren Hammudoglu
 *
 */
public class SequenceController implements Initializable, Observer {

    /**
     * The wrapper element.
     */
    @FXML
    private AnchorPane wrapper;
    /**
     * Contains the sequences.
     */
    @FXML
    private ListView<Label> sequenceList;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        ViewController vc = ViewController.getInstance();
        vc.addObserver(this);
        repaint();
    }

    /**
     * Format the color into r,g,b,a format.
     *
     * @param color
     *            the color
     * @return the web color code of the color
     */
    public static String rgbaFormat(final Color color) {
        final int colorRange = 255;
        return String.format("%d,%d,%d,%f",
                (int) (color.getRed() * colorRange),
                (int) (color.getGreen() * colorRange),
                (int) (color.getBlue() * colorRange), color.getOpacity());
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        repaint();
    }

    /**
     * Fills the sequence view and removes the old content.
     */
    private void repaint() {
        final ViewController vc = ViewController.getInstance();

        if (vc.isLoaded()) {
            sequenceList.setItems(generateLabels());
        }

    }

    /**
     * Generates the sequence labels.
     *
     * @return a list of the labels
     */
    private ObservableList<Label> generateLabels() {
        ViewController vc = ViewController.getInstance();

        ObservableList<Label> sequenceItems = FXCollections
                .observableArrayList();
        for (final Sequence sequence : vc.getSequences().values()) {
            String id = sequence.getIdentifier();
            Label label = new Label(id);
            Color color = (new SequenceColor(sequence)).getColor();

            label.setStyle("-fx-background-color: rgba(" + rgbaFormat(color)
                    + ")");

            String styleClassFilter = "filtered";
            if (vc.getVisible().contains(sequence)) {
                label.getStyleClass().add(styleClassFilter);
            }

            label.setOnMouseClicked((mouseEvent) -> {
                Set<Sequence> visibleSequences = vc.getVisible();

                if (label.getStyleClass().contains(styleClassFilter)) {
                    // remove from filter
                    visibleSequences.remove(sequence);
                    label.getStyleClass().remove(styleClassFilter);
                } else {
                    // add to filter
                    visibleSequences.add(sequence);
                    label.getStyleClass().add(styleClassFilter);
                }

                vc.setVisible(visibleSequences);
            });

            sequenceItems.add(label);
        }

        return sequenceItems;
    }

}
