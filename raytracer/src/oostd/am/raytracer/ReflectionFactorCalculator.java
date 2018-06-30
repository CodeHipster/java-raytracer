package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.UnitVector;


/**
 * using Schlick's approximation
 *
 * TODO: should we use the halfway normal?
 */
public class ReflectionFactorCalculator {

    public double calculateReflectionFactor(UnitVector surfaceNormal, UnitVector view, double densityBelowSurface, double densityAboveSurface){
        double r0 = (densityAboveSurface - densityBelowSurface) / (densityAboveSurface + densityBelowSurface);
        r0 *= r0;

        double a = 1 - surfaceNormal.dot(view);
        double pow = a*a*a*a*a;
        return r0 + (1-r0)* pow;
    }
}
