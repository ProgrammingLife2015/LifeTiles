package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

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

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        ObservableList<String> sequences = FXCollections.observableList(Arrays
                .asList("TKK-REF", "TKK-01-0066", "TKK-01-0058", "TKK-01-0026",
                        "TKK-01-0029", "TKK-01-0015"));
        sequenceList.setItems(label(sequences));
    }

    /**
     * Helper method, embeds the strings in a list in labels.
     *
     * @param strings the list of strings
     * @return a list of labels
     */
    private ObservableList<Label> label(final ObservableList<String> strings) {
        ObservableList<Label> res = FXCollections.observableArrayList();
        for (String s : strings) {
            res.add(new Label(s));
        }
        return res;
    }

}
