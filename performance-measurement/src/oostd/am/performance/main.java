package oostd.am.performance;

import java.util.Random;

public class main {

    public static void main(String[] args){
        Random random = new Random(42);
        new DoublesTest().testDoubles(random);
        new FloatTest().testFloats(random);
    }
}
