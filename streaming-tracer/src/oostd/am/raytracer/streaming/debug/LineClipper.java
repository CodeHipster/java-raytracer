package oostd.am.raytracer.streaming.debug;

import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Vector2D;

/**
 * Using Cohen–Sutherland Algorithm
 *
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
public class LineClipper {
    public static byte INSIDE = 0;
    public static byte LEFT = 1;
    public static byte RIGHT = 2;
    public static byte BOTTOM = 4;
    public static byte TOP = 8;

    /**
     * Get a zone code using binary flags represented by a byte.
     */
    private static byte getWindowZoneCode(Vector2D point, double left, double right, double bottom, double top) {
        byte windowZone = INSIDE;

        if (point.x > right) windowZone |= RIGHT;
        if (point.x < left) windowZone |= LEFT;
        if (point.y > top) windowZone |= TOP;
        if (point.y < bottom) windowZone |= BOTTOM;

        return windowZone;
    }

    /**
     * @return if line is clipped, false if it does not intersect with window.
     */
    public static <T extends Line2D> boolean clipLine( T line, Dimension dimension){

        double right = dimension.width / 2;
        double left = right * -1;
        double top = dimension.height / 2;
        double bottom = top * -1;

        // Compute region codes for P1, P2
        byte ZoneCodePoint1 = getWindowZoneCode(line.from, left, right, bottom, top);
        byte ZoneCodePoint2 = getWindowZoneCode(line.to, left, right, bottom, top);

        double x1 = line.from.x;
        double y1 = line.from.y;
        double x2 = line.to.x;
        double y2 = line.to.y;
        // Initialize line as outside the rectangular window
        boolean accept = false;

        while (true)
        {
            if ((ZoneCodePoint1 == 0) && (ZoneCodePoint2 == 0))
            {
                // If both endpoints lie within rectangle
                accept = true;
                break;
            }
            else if ((ZoneCodePoint1 & ZoneCodePoint2) > 0)
            {
                // If both endpoints are outside rectangle,
                // in same region
                break;
            }
            else
            {
                // Some segment of line could lie within the rectangle.
                // if line is completely outside the window, it will eventually be clipped to the same window position.
                int code_out;
                double x = 0, y = 0;

                // At least one endpoint is outside the
                // rectangle, pick it.
                if (ZoneCodePoint1 != 0)
                    code_out = ZoneCodePoint1;
                else
                    code_out = ZoneCodePoint2;

                // Find intersection point;
                // using formulas y = y1 + slope * (x - x1),
                // x = x1 + (1 / slope) * (y - y1)
                if ((code_out & TOP) > 0)
                {
                    // point is above the clip rectangle
                    x = x1 + (x2 - x1) * (top - y1) / (y2 - y1);
                    y = top;
                }
                else if ((code_out & BOTTOM) > 0)
                {
                    // point is below the rectangle
                    x = x1 + (x2 - x1) * (bottom - y1) / (y2 - y1);
                    y = bottom;
                }
                else if ((code_out & RIGHT) > 0)
                {
                    // point is to the right of rectangle
                    y = y1 + (y2 - y1) * (right - x1) / (x2 - x1);
                    x = right;
                }
                else if ((code_out & LEFT) > 0)
                {
                    // point is to the left of rectangle
                    y = y1 + (y2 - y1) * (left - x1) / (x2 - x1);
                    x = left;
                }

                // Now intersection point x,y is found
                // We replace point outside rectangle
                // by intersection point
                if (code_out == ZoneCodePoint1)
                {
                    x1 = x;
                    y1 = y;
                    ZoneCodePoint1 = getWindowZoneCode(new Vector2D(x1, y1), left, right, bottom, top);
                }
                else
                {
                    x2 = x;
                    y2 = y;
                    ZoneCodePoint2 = getWindowZoneCode(new Vector2D(x2, y2), left, right, bottom, top);
                }
            }
        }
        if (accept)
        {
            line.from.x = x1;
            line.from.y = y1;
            line.to.x = x2;
            line.to.y = y2;
            return true;
        }else{
            return false;
        }
    }
}
