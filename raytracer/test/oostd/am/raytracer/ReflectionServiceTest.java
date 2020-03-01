package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import org.junit.jupiter.api.Test;

class ReflectionServiceTest {

    @Test
    void calculateReflectionFactor4() {
        UnitVector incident = new UnitVector(4, -1, 0);

        ReflectionService reflectionService = new ReflectionService();
        UnitVector normal = new UnitVector(0, 1, 0);
        Vector t1 = reflectionService.calculateRefractionVector(
                incident,
                normal,
                1,
                1.5);
        System.out.println(" done ");

    }
}