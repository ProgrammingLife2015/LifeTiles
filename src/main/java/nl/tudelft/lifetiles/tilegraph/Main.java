package nl.tudelft.lifetiles.tilegraph;

import java.util.HashSet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.tudelft.lifetiles.graph.FactoryProducer;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.GraphFactory;
import nl.tudelft.lifetiles.graph.SequenceSegment;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        TileModel model = new TileModel(loadGraphModel());
        TileView view = new TileView();
        TileController controller = new TileController(view, model);
        view.addController(controller);

        Group root = controller.drawGraph();

        VBox box = new VBox();
        VBox vb = new VBox();

        ScrollPane sp = new ScrollPane();
        box.getChildren().addAll(sp);
        vb.getChildren().add(root);

        sp.setContent(vb);

        final Scene scene = new Scene(box, 800, 600, Color.CORNSILK);

        stage.setScene(scene);
        stage.show();
    }

    private Graph<SequenceSegment> loadGraphModel() {

        SequenceSegment v1, v2, v3, v4, v5, v6;
        GraphFactory<SequenceSegment> gf;
        FactoryProducer<SequenceSegment> fp;
        fp = new FactoryProducer<SequenceSegment>();

        gf = fp.getFactory("JGraphT");
        HashSet<String> s1 = new HashSet<String>();
        s1.add("reference");
        HashSet<String> s2 = new HashSet<String>();
        s2.add("mutation");
        HashSet<String> s3 = new HashSet<String>();
        s3.add("reference");
        s3.add("mutation");
        v1 = new SequenceSegment(s3, 1, 2, "G");
        v2 = new SequenceSegment(s1, 2, 4, "TT");
        v3 = new SequenceSegment(s2, 2, 3, "C");
        v4 = new SequenceSegment(s3, 4, 5, "A");
        v5 = new SequenceSegment(s2, 3, 4, "G");
        v6 = new SequenceSegment(s1, 3, 4, "T");

        Graph<SequenceSegment> gr = gf.getGraph();

        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);
        // gr.addVertex(v5);
        // gr.addVertex(v6);

        return gr;
    }

    private Graph<SequenceSegment> loadBigGraphModel() {

        GraphFactory<SequenceSegment> gf;
        FactoryProducer<SequenceSegment> fp;
        fp = new FactoryProducer<SequenceSegment>();

        gf = fp.getFactory("JGraphT");
        HashSet<String> s1 = new HashSet<String>();
        s1.add("reference");

        Graph<SequenceSegment> gr = gf.getGraph();

        for (int i = 1; i < 1000; i++) {
            for (int j = 1; j < 15; j++) {
                SequenceSegment v1 = new SequenceSegment(s1, i, i + 1, "G");
                gr.addVertex(v1);
            }

        }

        return gr;
    }

}
