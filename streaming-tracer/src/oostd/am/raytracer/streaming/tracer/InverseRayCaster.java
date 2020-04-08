package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;

//TODO: refractor
public class InverseRayCaster {

    public List<InverseRay> castRay(Collision<InverseRay> collision) {
        List<InverseRay> rays = new ArrayList<>();
        if (collision == null)
            return rays; // We did not hit anything. No light comes from the void.

        Triangle target = collision.target;
        InverseRay ray = collision.ray;

        boolean hitFromBehind = (collision.ray.direction.dot(target.surfaceNormal) > 0);
        Scatter scatter = scatter(collision.ray, collision);
        double reflectionFactor = scatter.reflectionFactor;
        double refractionFactor = scatter.refractionFactor;
        if (hitFromBehind) {
            // ray reflects and or refracts
            // use values from Scatter object
        } else {
            // Part of light is not refracted or reflected, but "absorbed"
            // adjust factors for that.
            double materialDiffuse = target.material.diffuseFactor;
            double materialReflection = target.material.reflectionFactor;
            double materialRefraction = 1 - materialReflection - materialDiffuse;
            reflectionFactor = materialReflection + (materialRefraction * scatter.reflectionFactor);
            refractionFactor = materialRefraction * scatter.refractionFactor;
        }

        if (scatter.reflection != null) {
            rays.add(new InverseRay(ray.depth + 1, ray.intensity * reflectionFactor, scatter.reflection, collision.impactPoint, ray.pixelPosition, collision.target));
        }
        if (scatter.refraction != null) {
            rays.add(new InverseRay(ray.depth + 1, ray.intensity * refractionFactor, scatter.refraction, collision.impactPoint, ray.pixelPosition, collision.target));
        }
        return rays;
    }

    private Scatter scatter(InverseRay ray, Collision<InverseRay> collision) {
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
        if (collision.target.material.transparent) {
            refraction = calculateRefractionVector(ray.direction, normal, n1, n2);
        }
        double reflectionFactor = 1;
        if (refraction != null) {
            //TODO: reuse calculation results from refraction in calculating reflection factor
            reflectionFactor = calculateReflectionFactor(n1, n2, normal, incident);
        }
        UnitVector reflection = null;
        if (collision.target.material.reflectionFactor > 0 || reflectionFactor > 0) { //TODO: material reflectionfactor should not be taken into account when hitting from behind.
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

    public UnitVector calculateRefractionVector(UnitVector incident, UnitVector normal, double densityFrom, double densityTo) {
        double densityFactor = densityFrom / densityTo;
        double cosI = -incident.dot(normal);
        double squaredSinI = densityFactor * densityFactor * (1 - cosI * cosI);
        if (squaredSinI > 1)
            return null; // Total internal reflection
        double cosT = Math.sqrt(1 - squaredSinI);
        return incident.scale(densityFactor).addSelf(normal.scale(densityFactor * cosI - cosT)).unit(); //TODO: we know this ends with a unit vector, avoid costly .unit() calls
    }

    public static class Scatter {
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
