package oostd.am.raytracer.api.geography;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitVectorTest {

    @Test
    public void test(){
        UnitVector unitVector = UnitVector.construct(1.0, 2.0, 4.0);
        Assertions.assertEquals(0.2182178902359924, unitVector.x);
        Assertions.assertEquals(0.4364357804719848, unitVector.y);
        Assertions.assertEquals(0.8728715609439696, unitVector.z);
    }

    @Test
    public void testDot(){
        UnitVector unitVector1 = UnitVector.construct(1, 1, 0); //45 degrees
        UnitVector unitVector2 = UnitVector.construct(1, 0, 0); //0 degrees
        double dot = unitVector1.dot(unitVector2);
        double dot2 = unitVector2.dot(unitVector1);
        double sin = Math.sin(Math.PI / 4);
        double cos = Math.cos(Math.PI / 4);

        int test = 0;
    }

}