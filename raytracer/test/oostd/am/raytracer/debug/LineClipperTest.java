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
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, 7), new Vector2D(3, 8)),
                new Window(null, null, null, new Dimension(10, 10))); //-5 to 5

        assertNull(line2D);
    }

    @Test
    public void reject2And4() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, 7), new Vector2D(-8, 4)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertNull(line2D);
    }

    @Test
    public void rejectZone8And3() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(3, -7), new Vector2D(20, 6)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertNull(line2D);
    }

    @Test
    public void rejectZone3And8() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(20, 6), new Vector2D(3, -7)),
                new Window(null, null, null, new Dimension(10, 10)));

        assertNull(line2D);
    }

    @Test
    public void acceptZone8And3() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(-3, -7), new Vector2D(6, 9)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(line2D);
        assertNotNull(line2D);
    }

    @Test
    public void acceptZone4And6() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(-6, 2), new Vector2D(6, -3)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(line2D);
        assertNotNull(line2D);
    }

    @Test
    public void acceptZone6And4() {
        Line2D line2D = LineClipper.clipLine(
                new Line2D(new Vector2D(6, -3), new Vector2D(-6, 2)),
                new Window(null, null, null, new Dimension(10, 10)));

        System.out.println(line2D);
        assertNotNull(line2D);
    }
}