package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceGenerator;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * The controller of the data view.
 *
 * @author Joren Hammudoglu
 *
 */
public class SequenceController implements Initializable {

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

    /**
     * The sequences.
     */
    private ObservableList<Sequence> sequences;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {

        ObservableList<Label> sequenceItems = FXCollections
                .observableArrayList();
        for (Sequence item : getSequences()) {
            String id = item.getIdentifier();
            Label label = new Label(id);

            Color color = getSequenceColor(item);

            label.setStyle("-fx-background-color: rgba(" + rgbaFormat(color)
                    + ")");

            sequenceItems.add(label);
        }

        sequenceList.setItems(sequenceItems);
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

    /**
     * Get the color of the sequence based on its identifier's hash-code. Helper
     * method. TODO: move to appropriate class.
     *
     * @param sequence
     *            the sequence
     * @return the color of the sequence
     */
    public static Color getSequenceColor(final Sequence sequence) {
        String id = sequence.getIdentifier();

        // TODO: improve hash convertion
        List<Integer> colors = Arrays.asList(
                // first half
                id.substring(0, id.length() / 2).hashCode(),
                // second half
                id.substring(id.length() / 2).hashCode(),
                // whole string
                id.hashCode());
        colors = colors.stream().map(color -> ubyteValue((byte) (int) (color)))
                .collect(Collectors.toList());

        System.out.println("colors(" + id + ") = " + colors);

        final double opacity = 0.5;
        return Color.rgb(colors.get(0), colors.get(1), colors.get(2), opacity);
    }

    /**
     * Returns the value of an unsigned byte.
     *
     * @param b
     *            the unsigned byte
     * @return the integer value of the unsigned byte
     */
    public static int ubyteValue(final byte b) {
        final int mask = 0xFF;
        final int value = b & mask;
        return value;
    }

    /**
     * Gets or creates the sequences.
     *
     * @return a list of the sequences
     */
    private List<Sequence> getSequences() {
        if (sequences == null) {
            FactoryProducer<SequenceSegment> fp;
            fp = new FactoryProducer<SequenceSegment>();
            GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
            Graph<SequenceSegment> graph = gf.getGraph();
            SequenceGenerator sg = new SequenceGenerator(graph);
            sequences = FXCollections.observableList(new ArrayList<Sequence>(sg
                    .generateSequences().values()));

            sequences.addAll(getDummyData()); // TODO: read actual data
        }
        return sequences;
    }

    /**
     * Generates dummy data for testing purposes. Obsolete.
     *
     * @return collection of dummy sequences
     */
    @Deprecated
    private Collection<Sequence> getDummyData() {
        List<String> dummyStrings = Arrays.asList("TKK-REF", "TKK-01-0066",
                "TKK-01-0058", "TKK-01-0026", "TKK-01-0029", "TKK-01-0015");
        return dummyStrings.stream().map(DefaultSequence::new)
                .collect(Collectors.toList());
    }

}
