package oostd.am.raytracer.engine.debug;

import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class LineDrawer {
    public static List<PixelPosition> draw(Line2D line) {
        List<PixelPosition> pixels = new ArrayList<>();
        Vector2D diff = line.to.subtract(line.from);
        double slope = Math.abs(diff.y / diff.x);
        double step, a, b, end;
        if (slope > 1) {
            //Step over y
            //bottom to top
            if (line.from.y > line.to.y) {
                swap(line);
            }
            step = diff.x / diff.y;
            a = line.from.y + 0.5; //to compensate for int casting
            b = line.from.x + ((step < 0) ? -0.5 : 0.5);//to compensate for int casting
            end = line.to.y + 0.5;
            //1 size steps over a, step increments on b, until a > end
            while (a < end) {
                pixels.add(new PixelPosition((int) b, (int) a));
                a += 1;
                b += step;
            }
        } else {
            //left to right
            if (line.from.x > line.to.x) {
                swap(line);
            }
            //step over x
            step = diff.y / diff.x;
            a = line.from.x + 0.5;//to compensate for int casting
            b = line.from.y + ((step < 0) ? -0.5 : 0.5);//to compensate for int casting
            end = line.to.x + 0.5;//to compensate for int casting
            //1 size steps over a, step increments on b, until a > end
            while (a < end) {
                pixels.add(new PixelPosition((int) a, (int) b));
                a += 1;
                b += step;
            }
        }
        return pixels;
    }

    private static void swap(Line2D line) {
        Vector2D store = line.to;
        line.to = line.from;
        line.from = store;
    }
}
