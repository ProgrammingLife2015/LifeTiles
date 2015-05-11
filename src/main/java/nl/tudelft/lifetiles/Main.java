package nl.tudelft.lifetiles;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main class / launcher.
 *
 * @author Joren Hammudoglu
 *
 */
public class Main extends Application {

    /**
     * The minimal height of the window, in pixels.
     */
    private static final int WINDOW_HEIGHT = 720;

    /**
     * The minimal width of the window, in pixels.
     */
    private static final int WINDOW_WIDTH = 1280;

    /**
     * Launch LifeTiles.
     *
     * @param args
     *            The program arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public final void start(final Stage stage) {
        try {
            stage.initStyle(StageStyle.UNDECORATED);

            URL mainView = getClass().getClassLoader().getResource(
                    "fxml/MainView.fxml");
            URL fontFile = getClass().getClassLoader().getResource(
                    "fonts/Open_Sans/OpenSans-Light.ttf");

            Parent root = FXMLLoader.load(mainView);

            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            final int fontSize = 12;
            Font.loadFont(fontFile.toExternalForm(), fontSize);

            stage.setTitle("LifeTiles");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
