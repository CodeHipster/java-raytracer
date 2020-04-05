package oostd.am.raytracer.engine.debug;

import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.List;

class LineDrawerTest {

    @Test
    public void rightFlatUp(){
        Vector2D from = new Vector2D(2, 1);
        Vector2D to = new Vector2D(15, 11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }

    @Test
    public void rightFlatDown(){
        Vector2D from = new Vector2D(-2, 1);
        Vector2D to = new Vector2D(15, -11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }


    @Test
    public void rightSteepUp(){
        Vector2D from = new Vector2D(2, 1);
        Vector2D to = new Vector2D(9, 11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }

    @Test
    public void rightSteepDown(){
        Vector2D from = new Vector2D(-2, 1);
        Vector2D to = new Vector2D(9, -11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }


    @Test
    public void leftFlatUp(){
        Vector2D from = new Vector2D(15, 1);
        Vector2D to = new Vector2D(2, 11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }

    @Test
    public void leftFlatDown(){
        Vector2D from = new Vector2D(15, 1);
        Vector2D to = new Vector2D(-2, -11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }


    @Test
    public void leftSteepUp(){
        Vector2D from = new Vector2D(9, 1);
        Vector2D to = new Vector2D(2, 11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }

    @Test
    public void leftSteepDown(){
        Vector2D from = new Vector2D(9, 1);
        Vector2D to = new Vector2D(-2, -11);
        Line2D line = new Line2D(from, to);
        List<PixelPosition> draw = LineDrawer.draw(line);
    }
}