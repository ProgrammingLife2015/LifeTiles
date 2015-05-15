package nl.tudelft.lifetiles.tree.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;


public class SunburstRing extends SunburstUnit {

    public SunburstRing(PhylogeneticTreeItem v, int layer, double degreeStart,
            double degreeEnd, double centerX, double centerY) {
        value = v;
        name = new Text(value.getName());
        display = createRing(layer, degreeStart, degreeEnd, centerX , centerY);
        getChildren().addAll(display, name);
    }

    /**
     *
     * @param layer
     *            first layer is 0
     * @param size
     * @return
     */
    private Shape createRing(int layer, double degreeStart, double degreeEnd, double centerX, double centerY) {
        Path result = new Path();

        result.setFill(Color.RED);
        result.setFillRule(FillRule.EVEN_ODD);

        double arcSize = SunburstUnit.calculateAngle(degreeStart, degreeEnd);
        boolean largeArc = arcSize > 180;

        double innerRadius = CENTER_RADIUS + (layer * RING_WIDTH);
        double outerRadius = innerRadius + RING_WIDTH;

        double angleAlpha = degreeStart * (Math.PI / 180);
        double angleAlphaNext = degreeEnd * (Math.PI / 180);

        double point1X = centerX  + innerRadius * Math.sin(angleAlpha);
        double point1Y = centerY - (innerRadius * Math.cos(angleAlpha));

        double point2X = centerX + outerRadius * Math.sin(angleAlpha);
        double point2Y = centerY - (outerRadius * Math.cos(angleAlpha));

        double point3X = centerX + outerRadius * Math.sin(angleAlphaNext);
        double point3Y = centerY - (outerRadius * Math.cos(angleAlphaNext));

        double point4X = centerX + innerRadius * Math.sin(angleAlphaNext);
        double point4Y = centerY - (innerRadius * Math.cos(angleAlphaNext));

        MoveTo move1 = new MoveTo(point1X, point1Y);
        LineTo line12 = new LineTo(point2X, point2Y);

        ArcTo arc23 = new ArcTo();
        arc23.setRadiusX(outerRadius);
        arc23.setRadiusY(outerRadius);
        arc23.setX(point3X);
        arc23.setY(point3Y);
        arc23.setSweepFlag(true);
        arc23.setLargeArcFlag(largeArc);

        LineTo line34 = new LineTo(point4X, point4Y);

        ArcTo arc41 = new ArcTo();
        arc41.setRadiusX(innerRadius);
        arc41.setRadiusY(innerRadius);
        arc41.setX(point1X);
        arc41.setY(point1Y);
        arc41.setSweepFlag(false);
        arc41.setLargeArcFlag(largeArc);

        result.getElements().add(move1);
        result.getElements().add(line12);
        result.getElements().add(arc23);
        result.getElements().add(line34);
        result.getElements().add(arc41);

        return result;
    }

}
