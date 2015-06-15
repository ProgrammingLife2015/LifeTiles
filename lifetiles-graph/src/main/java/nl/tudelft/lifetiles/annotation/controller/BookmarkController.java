package nl.tudelft.lifetiles.annotation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.Message;

public class BookmarkController extends AbstractController {

    @FXML
    private VBox wrapper;

    @FXML
    private ListView<String> bookmarkList;

    @FXML
    private TextField searchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        listen(Message.BOOKMARKS, (sender, subject, args) -> {
            show();
        });


    }

    @FXML
    private void closeAction() {
        hide();
    }

    @FXML
    private void searchAction() {
        System.out.println("Searching");
    }

    private void hide() {
        wrapper.visibleProperty().set(false);
    }

    private void show() {
        wrapper.visibleProperty().set(true);
    }

}
