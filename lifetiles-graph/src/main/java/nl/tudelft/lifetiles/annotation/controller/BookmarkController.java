package nl.tudelft.lifetiles.annotation.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nl.tudelft.lifetiles.annotation.model.AbstractBookmark;
import nl.tudelft.lifetiles.annotation.model.KnownMutation;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.Message;

/**
 *
 * @author Albert Smit
 *
 */
public class BookmarkController extends AbstractController {

    /**
     * The wrapper element for the control.
     */
    @FXML
    private VBox wrapper;

    /**
     * A listView to show all annotations.
     */
    @FXML
    private ListView<AbstractBookmark> bookmarkList;

    /**
     * the searchbox to search through all annotations.
     */
    @FXML
    private TextField searchBox;

    /**
     * The list of known mutations.
     */
    private List<KnownMutation> knownMutations;
    /**
     * The list of all bookmarks.
     */
    private ObservableList<AbstractBookmark> listItems;
    /**
     * The filtered list that will wrap the listItems.
     */
    private FilteredList<AbstractBookmark> filteredItems;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        listItems = FXCollections.observableArrayList();
        filteredItems = new FilteredList<AbstractBookmark>(listItems);
        bookmarkList.setItems(filteredItems);

        bookmarkList.setCellFactory(new Callback<ListView<AbstractBookmark>,
                ListCell<AbstractBookmark>>() {
            @Override
            public ListCell<AbstractBookmark> call(ListView<AbstractBookmark> param) {
                final ListCell<AbstractBookmark> cell = new ListCell<AbstractBookmark>() {
                    public void updateItem(AbstractBookmark item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toCellString());

                            setOnMouseClicked(event -> {
                                if (event.getClickCount() == 2) {
                                    shout(Message.GOTO, "" ,
                                            new Long(item.getGenomePosition()));
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
        //Initialize shout listeners
        listen(Message.BOOKMARKS, (sender, subject, args) -> {
            show();
        });

        listen(Message.LOADED, (sender, subject, args) -> {
            if (!"known mutations".equals(subject)) {
                return;
            }
            assert (args[0] instanceof List<?>);
            knownMutations = (List<KnownMutation>) args[0];

            listItems.addAll(knownMutations);
        });


    }

    /**
     * closes the bookmark sidebar.
     */
    @FXML
    private void closeAction() {
        hide();
    }

    /**
     * updates the filtering predicate.
     */
    @FXML
    private void searchAction() {
        System.out.println("Searching");
        System.out.println(searchBox.textProperty().get());
        filteredItems.setPredicate(annotation -> {
            if (searchBox.textProperty().get().isEmpty()) {
                return true;
            }
           return annotation.toString().contains(searchBox.getCharacters());
        });
    }

    /**
     * makes the sidebar invisible.
     */
    private void hide() {
        wrapper.visibleProperty().set(false);
    }

    /**
     * makes the sidebar visible.
     */
    private void show() {
        wrapper.visibleProperty().set(true);
    }

}
