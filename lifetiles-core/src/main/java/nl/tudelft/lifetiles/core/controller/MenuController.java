package nl.tudelft.lifetiles.core.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.AbstractNotification;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;

/**
 * The controller of the menu bar.
 *
 * @author Joren Hammudoglu
 *
 */
public class MenuController extends AbstractController {

    /**
     * Constant annotation extension, currently as defined by client: '.txt'.
     */
    private static final String ANNOTATION_EXTENSION = ".txt";
    /**
     * Extension of the node file.
     */
    private static final String NODE_EXTENSION = ".node.graph";
    /**
     * Extension of the edge file.
     */
    private static final String EDGE_EXTENSION = ".edge.graph";

    /**
     * Extension of the tree file.
     */
    private static final String TREE_EXTENSION = ".nwk";

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
            shout(NotificationController.NOTIFY, "", notification);
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
        shout(Message.RESET, "");
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
        loadGraph(directory);
        loadTree(directory);
        loadAnnotations(directory);
    }

    /**
     * Loads the graph from files in the specified directory.
     *
     * @param directory
     *            The directory in which to locate the files.
     * @throws IOException
     *             When the directory does not contain exactly one graph file
     *             and one node file.
     */
    private void loadGraph(final File directory) throws IOException {
        File nodeFile = FileUtils.getSingleFileByExtension(directory,
                NODE_EXTENSION);
        File edgeFile = FileUtils.getSingleFileByExtension(directory,
                EDGE_EXTENSION);
        shout(Message.OPENED, "graph", nodeFile, edgeFile);
    }

    /**
     * Loads the annotations from a file in the specified directory.
     *
     * @param directory
     *            The directory from which to load annotations.
     */
    private void loadAnnotations(final File directory) {
        File annotationFile = loadOrWarn(directory, ANNOTATION_EXTENSION);
        if (annotationFile != null) {
            shout(Message.OPENED, "annotations", annotationFile);
        }
    }

    /**
     * Loads the tree from a file in the specified directory.
     *
     * @param directory
     *            The directory in which to search for the tree file.
     */
    private void loadTree(final File directory) {
        File treeFile = loadOrWarn(directory, TREE_EXTENSION);
        if (treeFile != null) {
            shout(Message.OPENED, "tree", treeFile);
        }
    }

    /**
     * Loads a file from the specified directory, with the specified extension,
     * and give a warning.
     *
     * @param directory
     *            The directory in which to search for the file.
     * @param extension
     *            The extension to search for.
     * @return The found file.
     */
    private File loadOrWarn(final File directory, final String extension) {
        try {
            File file = FileUtils
                    .getSingleFileByExtension(directory, extension);
            return file;
        } catch (IOException e) {
            shout(NotificationController.NOTIFY,
                    "",
                    nf.getNotification(
                            extension
                                    + " file could not be found or multiple files found ",
                            NotificationFactory.WARNING));
        }
        return null;
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
            shout(NotificationController.NOTIFY, "", notification);
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

        shout(Message.OPENED, "annotations", file);
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
    }
}