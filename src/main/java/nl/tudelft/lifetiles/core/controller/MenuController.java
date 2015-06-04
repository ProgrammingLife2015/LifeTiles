package nl.tudelft.lifetiles.core.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import nl.tudelft.lifetiles.core.util.FileUtils;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.AbstractNotification;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;
import nl.tudelft.lifetiles.sequence.model.Sequence;

/**
 * The controller of the menu bar.
 *
 * @author Joren Hammudoglu
 *
 */
public class MenuController extends AbstractController {

    /**
     * The initial x-coordinate of the window.
     */
    private double initialX;

    /**
     * The initial y-coordinate of the window.
     */
    private double initialY;

    /**
     * The menu element.
     */
    @FXML
    private MenuBar menuBar;

    /**
     * The notification factory.
     */
    private NotificationFactory nf;

    /**
     * all sequences, to reset the filters.
     */
    private Set<Sequence> sequences;

    /**
     * Handle action related to "Open" menu item.
     *
     * @param event
     *            Event on "Open" menu item.
     */
    @FXML
    private void openAction(final ActionEvent event) {
        try {
            loadDataFiles();
        } catch (IOException e) {
            AbstractNotification notification = nf.getNotification(e);
            shout(NotificationController.NOTIFY, notification);
        }
    }

    /**
     * Handle the click on the "Reset" item in the "Filter" menu.
     *
     * @param event
     *            Event on "Reset" menu item.
     */
    @FXML
    private void resetAction(final ActionEvent event) {
        if (sequences != null) {
            shout(Message.FILTERED, sequences);
        }
    }

    /**
     * Perform functionality associated with opening a file.
     *
     * @throws IOException
     *             throws <code>IOException</code> if any of the files were not
     *             found
     */
    private void loadDataFiles() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open folder containing data files");
        Window window = menuBar.getScene().getWindow();
        File directory = directoryChooser.showDialog(window);

        // user aborted
        if (directory == null) {
            return;
        }

        List<File> dataFiles = new ArrayList<>();
        List<File> annotations = FileUtils.findByExtension(directory, ".txt");
        
        
        List<String> exts = Arrays.asList(".node.graph", ".edge.graph", ".nwk");
        for (String ext : exts) {
            List<File> hits = FileUtils.findByExtension(directory, ext);
            if (hits.size() != 1) {
                throw new IOException("Expected 1 " + ext + " file intead of "
                        + hits.size());
            }

            dataFiles.add(hits.get(0));
        }
        
        shout(Message.OPENED, dataFiles.get(0), dataFiles.get(1),
                dataFiles.get(2));
        if (annotations != null && !annotations.isEmpty()) {
            shout(Message.ANNOTATIONS, annotations.get(0));
        }
    }

    /**
     * Handle action to "Insert Annotations" menu item.
     *
     * @param event
     *            Event on "Insert Annotations" item.
     */
    @FXML
    private void insertAnnotationsAction(final ActionEvent event) {
        try {
            loadAnnotationsFile();
        } catch (IOException e) {
            AbstractNotification notification = nf.getNotification(e);
            shout(NotificationController.NOTIFY, notification);
        }
    }

    /**
     * Perform functionality associated with opening and inserting a annotation
     * file.
     *
     * @throws IOException
     *             throws <code>IOException</code> if any of the files were not
     *             found
     */
    private void loadAnnotationsFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file containing annotations");
        Window window = menuBar.getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);

        // user aborted
        if (file == null) {
            return;
        }

        shout(Message.ANNOTATIONS, file);
    }

    /**
     * Make a node draggable so that when draggin that node, the window moves.
     * Code from <a
     * href="http://stackoverflow.com/a/12961943/1627479">StackOverflow</a>.
     *
     * @param node
     *            the node
     */
    private void addDraggableNode(final Node node) {
        node.setOnMousePressed((mouseEvent) -> {
            if (mouseEvent.getButton() != MouseButton.MIDDLE) {
                initialX = mouseEvent.getSceneX();
                initialY = mouseEvent.getSceneY();
            }
        });

        node.setOnMouseDragged((mouseEvent) -> {
            if (mouseEvent.getButton() != MouseButton.MIDDLE) {
                double x = mouseEvent.getScreenX() - initialX;
                double y = mouseEvent.getScreenY() - initialY;

                node.getScene().getWindow().setX(x);
                node.getScene().getWindow().setY(y);
            }
        });
    }

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        addDraggableNode(menuBar);
        nf = new NotificationFactory();
        // listen to loaded to get the sequence list
        listen(Message.LOADED,
                (controller, args) -> {
                    if (controller instanceof GraphController) {
                        assert args[0] instanceof Graph;
                        assert (args[1] instanceof Map<?, ?>);
                        Map<String, Sequence> sequenceMap = (Map<String, Sequence>) args[1];
                        sequences = new HashSet<Sequence>(sequenceMap.values());
                    }
                });
    }
}
