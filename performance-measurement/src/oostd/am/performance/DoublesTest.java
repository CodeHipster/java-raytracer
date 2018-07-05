package oostd.am.performance;

import java.util.Random;

public class DoublesTest {
    public void testDoubles(Random random){

        //addition
        int amount = 100000;
        //create set to work with
        double[] doubles1 = new double[amount];
        for(int i = 0; i < doubles1.length; i++){
            doubles1[i] = random.nextDouble();
        }

        long start = System.nanoTime();
        int runs = 10000;
        double result = 0;
        for(int x = 0; x < runs; x++) {
            for (int i = 0; i < doubles1.length; i++) {
                result += doubles1[i];
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
            for (int i = 0; i < doubles1.length; i++) {
                result -= doubles1[i];
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
            for (int i = 0; i < doubles1.length; i++) {
                result *= doubles1[i];
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
            for (int i = 0; i < doubles1.length; i++) {
                result /= doubles1[i];
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
            for (int i = 0; i < doubles1.length; i++) {
                result += Math.pow(doubles1[i], 0.5);
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
            for (int i = 0; i < doubles1.length; i++) {
                result += Math.sqrt(doubles1[i]);
            }
        }
        end = System.nanoTime();
        System.out.println("result = " + result);
        System.out.println("total time in ms " + (end - start)/1000000);
        System.out.println("sqrt takes " + ((double)(end - start))/(amount*runs) + " nanoseconds on average");

        //sinus
        //create set to work with
        double[] angles = new double[amount];
        for(int i = 0; i < angles.length; i++){
            double v = random.nextDouble()*360;
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
