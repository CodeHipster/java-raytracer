package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.UnitVector;

/**
 * https://graphics.stanford.edu/courses/cs148-10-summer/docs/2006--degreve--reflection_refraction.pdf
 */
public class ReflectionService {

    // input ray + triangle (or surface?)

    // if hit from behind
    // 100% is reflected or refracted, calculate factors
    // reflection factor is materialReflection + impactAngleReflection
    // materialReflection = reflection of material
    // impactAngleReflection = reflection based on angle,
    //  when the angle between ray impact and surface comes closer to 0 materials get more reflective
    //  the impactAngle factor of reflection is multiplied by the part of the ray that is not reflected
    // the part that is left is divided by refraction and diffuse.
    // e.g.
    // material reflection 0.4
    // material refraction 0.5
    // material diffuse    0.1
    // e.g. using Schliks approximation the impactAngle factor comes at 0.3
    // reflectionFactor = 0.4 + (0.6 * 0.3) = 0.58
    // refractionFactor = (1 - 0.58) * (0.5 / (0.1 + 0.5)) = 0.35
    // diffuseFactor = 1 - (0.58 + 0.21) = 0.07


    // else
    // part is diffuse, how much?

    // output potentially 3 rays (reflection, refraction, shadow)

    public Scatter scatter(InverseRay ray, Collision collision) {
        UnitVector incident = ray.direction;
        UnitVector normal = collision.target.surfaceNormal;
        double n1 = 1;
        double n2 = collision.target.volumeProperties.refractionIndex;
        double cosI = incident.dot(normal);
        if (cosI > 0) {
            normal = normal.invert();
            n1 = n2;
            n2 = 1;
        }
        UnitVector refraction = null;
        if(collision.target.material.transparent){
           refraction = calculateRefractionVector(ray.direction, normal, n1, n2);
        }
        double reflectionFactor = 1;
        if (refraction != null) {
            //TODO: reuse calculation results from refraction in calculating reflection factor
            reflectionFactor = calculateReflectionFactor(n1, n2, normal, incident);
        }
        UnitVector reflection = null;
        if(collision.target.material.reflectionFactor > 0 || reflectionFactor > 0){ //TODO: material reflectionfactor should not be taken into account when hitting from behind.
            reflection = ray.direction.reflectOn(normal);
        }
        double refractionFactor = 1 - reflectionFactor;
        return new Scatter(reflection, reflectionFactor, refraction, refractionFactor);
    }


    public double calculateReflectionFactor(double n1, double n2, UnitVector normal, UnitVector incident) {
        double densityFactor = n1 / n2;
        double cosI = -incident.dot(normal);
        double squaredSinI = densityFactor * densityFactor * (1 - cosI * cosI);
        double cosT = Math.sqrt(1 - squaredSinI);

        double rO = ((n1 * cosI - n2 * cosT) / (n1 * cosI + n2 * cosT));
        double rP = ((n2 * cosI - n1 * cosT) / (n2 * cosI + n1 * cosT));
        return (rO * rO + rP * rP) / 2;
    }

    public double calculateReflectionFactor(double n1, double n2, double cosI, double cosT) {
        double rO = ((n1 * cosI - n2 * cosT) / (n1 * cosI + n2 * cosT));
        double rP = ((n2 * cosI - n1 * cosT) / (n2 * cosI + n1 * cosT));
        return (rO * rO + rP * rP) / 2;
    }

    public UnitVector calculateRefractionVector(UnitVector incident, UnitVector normal, double densityFrom, double densityTo) {
        double densityFactor = densityFrom / densityTo;
        double cosI = -incident.dot(normal);
        double squaredSinI = densityFactor * densityFactor * (1 - cosI * cosI);
        if (squaredSinI > 1)
            return null; // Total internal reflection
        double cosT = Math.sqrt(1 - squaredSinI);
        return incident.scale(densityFactor).addSelf(normal.scale(densityFactor * cosI - cosT)).unit(); //TODO: we know this ends with a unit vector, avoid costly .unit() calls
    }

    /**
     * Schliks approximation
     *
     * @param surfaceNormal
     * @param view
     * @param densityBelowSurface
     * @param densityAboveSurface
     * @return
     */
    public double calculateReflectionFactorSchlik(UnitVector surfaceNormal, UnitVector view, double densityBelowSurface, double densityAboveSurface) {
        double r0 = (densityAboveSurface - densityBelowSurface) / (densityAboveSurface + densityBelowSurface);
        r0 *= r0;

        double a = 1 - surfaceNormal.dot(view);
        double pow = a * a * a * a * a;
        double factor = r0 + (1 - r0) * pow;
        if (factor > 1) {
            int debug = 0;
        }
        return factor;
    }

    public class Scatter {
        public UnitVector reflection;
        public double reflectionFactor;
        public UnitVector refraction;
        public double refractionFactor;

        public Scatter(UnitVector reflection, double reflectionFactor, UnitVector refraction, double refractionFactor) {
            this.reflection = reflection;
            this.reflectionFactor = reflectionFactor;
            this.refraction = refraction;
            this.refractionFactor = refractionFactor;
        }
    }
}
