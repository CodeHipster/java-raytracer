package oostd.am.raytracer.debug;

import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Using Cohen–Sutherland Algorithm
 * Zones
 *      |       |
 *   1  |   2   |   3
 *      |  top  |
 * --------------------
 *      |       |
 *   4  |   5   |   6
 * left |inside | right
 * --------------------
 *      |       |
 *   7  |   8   |   9
 *      |bottom |
 */
class LineClipperTest {

    @Test
    public void rejectSameZone2(){
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, 7), new Vector2D(3, 8)),
                //make the line clipper work with dimension and not the entire window.
                new Window(null, null, null, new Dimension(10, 10))); //-5 to 5

        assertFalse(clipped);
    }

    @Test
    public void reject2And4() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, 7), new Vector2D(-8, 4)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertFalse(clipped);
    }

    @Test
    public void rejectZone8And3() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(3, -7), new Vector2D(20, 6)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertFalse(clipped);
    }

    @Test
    public void rejectZone3And8() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(20, 6), new Vector2D(3, -7)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertFalse(clipped);
    }

    @Test
    public void acceptZone8And3() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, -7), new Vector2D(6, 9)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(clipped);
        assertTrue(clipped);
    }

    @Test
    public void acceptZone4And6() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(-6, 2), new Vector2D(6, -3)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(clipped);
        assertTrue(clipped);
    }

    @Test
    public void acceptZone6And4() {
        boolean clipped = LineClipper.clipLine(
                new Line2D(new Vector2D(6, -3), new Vector2D(-6, 2)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(clipped);
        assertTrue(clipped);
    }
}