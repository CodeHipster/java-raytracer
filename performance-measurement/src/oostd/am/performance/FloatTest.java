package oostd.am.performance;

import java.util.Random;

public class FloatTest {
    public void testFloats(Random random){

        //addition
        int amount = 100000;
        //create set to work with
        float[] floats = new float[amount];
        for(int i = 0; i < floats.length; i++){
            floats[i] = random.nextFloat();
        }

        long start = System.nanoTime();
        int runs = 10000;
        float result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result += floats[i];
            }
        }
        long end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("addition takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //subtraction
        start = System.nanoTime();
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result -= floats[i];
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("subtraction takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //multiplication
        start = System.nanoTime();
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result *= floats[i];
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("multiplication takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");


        //division
        start = System.nanoTime();
        result = 0;
        runs /= 2;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result /= floats[i];
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("division takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //pow
        start = System.nanoTime();
        runs = 1000;
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result += Math.pow(floats[i], 20);
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("power takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");


        //sqrt
        start = System.nanoTime();
        runs = 10000;
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < floats.length; i++) {
                result += Math.sqrt(floats[i]);
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("sqrt takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //sinus
        //create set to work with
        float[] angles = new float[amount];
        for(int i = 0; i < angles.length; i++){
            float v = random.nextFloat()*360;
            angles[i] = v;
        }

        start = System.nanoTime();
        runs = 1000;
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < angles.length; i++) {
                result += Math.sin(angles[i]);
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("sinus takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //cosinus

        start = System.nanoTime();
        runs = 1000;
        result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < angles.length; i++) {
                result += Math.cos(angles[i]);
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("cosinus takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

    }
}
