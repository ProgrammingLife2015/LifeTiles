package nl.tudelft.lifetiles.core.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

/**
 * The controller of the menu bar.
 *
 * @author Joren Hammudoglu
 *
 */
public class MenuController implements Initializable {
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
     * The view controller.
     */
    private ViewController controller;

    /**
     * Handle action related to "Open" menu item.
     *
     * @param event
     *            Event on "Open" menu item.
     */
    @FXML
    private void openAction(final ActionEvent event) {
        openFunctionality();
    }

    /**
     * Perform functionality associated with opening a file.
     */
    private void openFunctionality() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open folder containing data files");
        Window window = menuBar.getScene().getWindow();
        File directory = directoryChooser.showDialog(window);

        // user aborted
        if (directory == null) {
            return;
        }

        String graphFileName = null;
        try {
            graphFileName = getRelativePath(directory);
            graphFileName += collectGraphFileName(directory);
        } catch (IOException e) {
            ViewController.getInstance().displayError(e.getMessage());
            return;
        }

        controller.loadGraph(graphFileName);
    }

    /**
     * Collect the graph filename from a directory.
     *
     * @param directory
     *            the directory
     * @return the filename of the graph files
     * @throws IOException
     *             throw an exception when files are not found.
     */
    private String collectGraphFileName(final File directory)
            throws IOException {
        assert (directory.isDirectory());

        final String suffixCommon = ".graph";
        final String suffixNodeFile = ".node.graph";
        final String suffixEdgeFile = ".edge.graph";

        // check if there are 2 .graph files
        final File[] listFiles = directory.listFiles((dir, name) -> name
                .endsWith(suffixCommon));
        if (listFiles == null) {
            throw new IOException("No " + suffixCommon + " files found.");
        }

        List<File> graphFiles = Arrays.asList(listFiles);

        // check if these are the .node.graph and .edge.graph files
        List<String> fileNames = new ArrayList<String>();
        for (File file : graphFiles) {
            String fileName = file.getName();
            if (!(fileName.endsWith(suffixNodeFile) || fileName
                    .endsWith(suffixEdgeFile))) {
                throw new IOException("Expected a " + suffixNodeFile
                        + " and a " + suffixEdgeFile + " file.");
            }
            fileName = fileName.replaceAll(suffixNodeFile, "").replaceAll(
                    suffixEdgeFile, "");
            fileNames.add(fileName);
        }

        // check if the filenames are equal
        if (!fileNames.get(0).equals(fileNames.get(1))) {
            throw new IOException(
                    "Expected equal filename of the node and edge files.");
        }

        return fileNames.get(0);
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

    /**
     * Get the path relative to the build path of a file. Workaround for <a
     * href="https://github.com/ProgrammingLife5/LifeTiles/issues/75">#75</a>.
     *
     * @param file
     *            the file
     * @return the relative path of the file
     * @throws IOException
     */
    @Deprecated
    private static String getRelativePath(File file) throws IOException {
        String path = file.getCanonicalPath();
        String base = System.getProperty("user.dir");

        int relLength = "src/main/resources/".length();
        String relativePath = new File(base).toURI()
                .relativize(new File(path).toURI()).getPath();

        if (relLength > relativePath.length()) {
            throw new IOException("Directory not in build path.");
        }

        return relativePath.substring(relLength);
    }

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        addDraggableNode(menuBar);

        controller = ViewController.getInstance();
    }
}
