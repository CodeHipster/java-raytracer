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

}