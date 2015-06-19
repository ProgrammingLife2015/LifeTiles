package nl.tudelft.lifetiles.annotation.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.tudelft.lifetiles.annotation.model.AbstractBookmark;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.Message;

/**
 * Controller for the Bookmark sidebar.
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
     * The list of all bookmarks.
     */
    private ObservableList<AbstractBookmark> listItems;
    /**
     * The filtered list that will wrap the listItems.
     * do not add items directly to this list, instead add them to listItems.
     */
    private FilteredList<AbstractBookmark> filteredItems;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        setVisibility(false);

        // wrap the observable list in a filtered list so we can search it later
        listItems = FXCollections.observableArrayList();
        filteredItems = new FilteredList<AbstractBookmark>(listItems);
        bookmarkList.setItems(filteredItems);

        /*
         * create a new Cell factory so we can use a different method
         * to create the text of each cell,
         * and assign an event handler for mouse clicks.
         */
        bookmarkList.setCellFactory(param -> {
            // cell needs to be an inner class to use shout in its onMouseClick method
            final ListCell<AbstractBookmark> cell = new ListCell<AbstractBookmark>() {
                /**
                 * the ListCell, creates the correct text and adds the right eventhandler.
                 */
                public void updateItem(final AbstractBookmark item,
                            final boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.toCellString());
                        setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2) {
                                shout(Message.GOTO, "" ,
                                        Long.valueOf(item.getUnifiedPosition()));
                            }
                        });
                    }
                }
            };
            return cell;
        });

        initListeners();
    }


    /**
     * Initialize the shout listeners.
     */
    // java doesn't let us typecheck generics so the casts are unchecked
    // checkstyle doesn't like assert without parentheses with generics
    @SuppressWarnings({"checkstyle:genericwhitespace", "unchecked"})
    private void initListeners() {
        listen(Message.BOOKMARKS, (sender, subject, args) ->
            setVisibility(true)
        );

        listen(Message.LOADED, (sender, subject, args) -> {
            if ("known mutations".equals(subject) || "annotations".equals(subject)) {
                assert args[0] instanceof List<?>;
                List<AbstractBookmark> bookmarks = (List<AbstractBookmark>) args[0];
                listItems.addAll(bookmarks);
            }
        });
    }
    /**
     * closes the bookmark sidebar.
     */
    @FXML
    // PMD/findbugs do not work well with javafx. The method IS used.
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private void closeAction() {
        setVisibility(false);
    }

    /**
     * updates the filtering predicate.
     * uses a simple contains check on the text of the listItem.
     */
    @FXML
    // PMD/findbugs do not work well with javafx. The method IS used.
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private void searchAction() {
        filteredItems.setPredicate(annotation -> {
            if (searchBox.textProperty().get().isEmpty()) {
                return true;
            }
           return annotation.toCellString().contains(searchBox.getCharacters());
        });
    }

    /**
     * makes the sidebar (in)visible.
     * @param flag a boolean indicating intended visibility.
     */
    private void setVisibility(final boolean flag) {
        wrapper.visibleProperty().set(flag);
    }

}
