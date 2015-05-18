package nl.tudelft.lifetiles.sequence.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.lifetiles.core.controller.ViewController;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class SequenceControllerTest extends ApplicationTest {

    private static String testGraph = "data/test_set/simple_graph";
    private Scene scene;

    @Test
    public void toggleFiltered() {
        Node label = scene.lookup(".label.filtered:first");
        clickOn(label);

        assertFalse(label.getStyleClass().contains("filtered"));
    }

    @Test
    public void toggleUnfiltered() {
        clickOn(".label.filtered:first").clickOn("#sequenceList .label:first");
        Node label = scene.lookup(".label.filtered:first");

        assertTrue(label.getStyleClass().contains("filtered"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL mainView = getClass().getClassLoader().getResource(
                "fxml/MainView.fxml");
        Parent root = FXMLLoader.load(mainView);
        scene = new Scene(root, 1280, 720);

        stage.setTitle("LifeTiles");
        stage.setScene(scene);
        stage.show();

        ViewController vc = ViewController.getInstance();
        vc.loadGraph(testGraph);
    }

}
