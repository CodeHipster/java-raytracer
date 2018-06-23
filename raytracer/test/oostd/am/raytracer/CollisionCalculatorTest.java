package oostd.am.raytracer;


import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.ColorFilter;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollisionCalculatorTest {

    @Test
    public void calculateCollision() {
        //simple triangle 3 units away. Triangle vertices have to go clockwise.
        Triangle t = new Triangle(
                new Vertex[]{new Vertex(-1.0, -1.0, 3.0),new Vertex(0.0, 2.0, 3.0),new Vertex(1.0, -1.0, 3.0) },
                new ColorFilter(1,1,1));

        // ray pointing towards triangle
        Ray r = new Ray(UnitVector.construct(0.0, 0.0, 1.0), new Vector(0.0, 0.0, 0.0), new PixelPosition(0,0));

        Vector collisionPoint = CollisionCalculator.calculateCollision(t, r);
        // should be 0,0,3, center of the triangle.

        Assertions.assertEquals(0, collisionPoint.x);
        Assertions.assertEquals(0, collisionPoint.y);
        Assertions.assertEquals(3, collisionPoint.z);


    }
}
