package oostd.am.raytracer.api.geography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void project() {
        Plane plane = new Plane(new Vector(0, 0, -3),
                new UnitVector(1, 1, 1),
                new UnitVector(0, 1, 0));

        Vector2D project = plane.project(new Vector(9, 4, 9));
        assertEquals(new Vector2D(-2.121320343559643, -5.30722777603022),project);
    }

    @Test
    void project2() {
        Plane plane = new Plane(new Vector(0, 0, 0),
                new UnitVector(0, 1, 0),
                new UnitVector(1, 0, 0));

        Vector2D project = plane.project(new Vector(2, 10, 3));
        assertEquals(project, new Vector2D(2, 3));
    }

    @Test
    void positionOf() {
        Plane plane = new Plane(new Vector(0, 0, 0),
                new UnitVector(1, 1, 1),
                new UnitVector(0, 1, 0));

        Vector positionOf = plane.positionOf(new Vector2D(1,0));
        assertEquals(new Vector(0.7071067811865476,0,-0.7071067811865476), positionOf);
    }

    @Test
    void positionOf2() {
        Plane plane = new Plane(new Vector(0, 3, 0),
                new UnitVector(1, 1, 1),
                new UnitVector(0, 1, 0));

        Vector positionOf = plane.positionOf(new Vector2D(1,0));
        assertEquals(new Vector(0.7071067811865476,3,-0.7071067811865476), positionOf);
    }
}