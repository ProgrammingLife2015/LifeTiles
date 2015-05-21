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
     * The font size used at font load.
     */
    private static final double FONT_SIZE = 12;

    /**
     * The directory of the GUI font.
     */
    private static final String GUI_FONT_DIRECTORY = "fonts/Open_Sans/OpenSans-Light.ttf";

    /**
     * The directory of the mono space nucleotide font.
     */
    private static final String MONO_FONT_DIRECTORY = "fonts/Oxygen_Mono/OxygenMono-Regular.ttf";

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
            loadFonts();
            stage.initStyle(StageStyle.UNDECORATED);

            URL mainView = getClass().getClassLoader().getResource(
                    "fxml/MainView.fxml");

            Parent root = FXMLLoader.load(mainView);
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            stage.setTitle("LifeTiles");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads Open Sans and Oxygen Mono font into the application.
     */
    private void loadFonts() {
        URL openSansFontFile = getClass().getClassLoader().getResource(
                GUI_FONT_DIRECTORY);
        URL oxygenFontFile = getClass().getClassLoader().getResource(
                MONO_FONT_DIRECTORY);

        Font.loadFont(openSansFontFile.toExternalForm(), FONT_SIZE);
        Font.loadFont(oxygenFontFile.toExternalForm(), FONT_SIZE);
    }
}
