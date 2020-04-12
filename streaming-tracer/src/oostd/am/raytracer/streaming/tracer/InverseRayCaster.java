package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.scenery.SceneObject;
import oostd.am.raytracer.api.scenery.Sphere;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Cast rays based on the collision of a ray with an object from the scene.
 * This casts reflection and/or refraction rays
 */
public class InverseRayCaster {

    public List<InverseRay> castRay(Collision<InverseRay> collision) {
        List<InverseRay> rays = new ArrayList<>();
        if (collision == null)
            return rays; // We did not hit anything. No light comes from the void.

        SceneObject target = collision.target;
        InverseRay ray = collision.ray;

        UnitVector normal;
        if (target instanceof Triangle) {
            Triangle t = (Triangle) target;
            normal = t.surfaceNormal;
        } else {
            Sphere s = (Sphere) target;
            normal = collision.impactPoint.subtract(s.positon).unit();
        }

        boolean hitFromBehind = (collision.ray.direction.dot(normal) > 0);
        Scatter scatter = scatter(collision.ray, collision, normal);
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
            rays.add(new InverseRay(ray.depth + 1, ray.intensity * reflectionFactor, scatter.reflection, collision.impactPoint, ray.pixelPosition));
        }
        if (scatter.refraction != null) {
            rays.add(new InverseRay(ray.depth + 1, ray.intensity * refractionFactor, scatter.refraction, collision.impactPoint, ray.pixelPosition));
        }
        return rays;
    }

    private Scatter scatter(InverseRay ray, Collision<InverseRay> collision, UnitVector normal) {
        UnitVector incident = ray.direction;
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
            reflectionFactor = calculateReflectionFactor(n1, n2, normal, incident);
        }
        UnitVector reflection = null;
        if (reflectionFactor > 0) {
            reflection = ray.direction.reflectOn(normal);
        }
        double refractionFactor = 1 - reflectionFactor;
        return new Scatter(reflection, reflectionFactor, refraction, refractionFactor);
    }

    /**
     * Calculate the intensity of the reflection vector based on its angle and material density.
     * The rest of the intensity goes to the refracted vector
     *
     * @param n1       density 'outside'
     * @param n2       density 'inside'
     * @param normal   unit vector pointing to the 'outside'
     * @param incident incident unit vector comming from the 'outside'
     * @return amount of light that is reflected, a value between 0 and 1
     */
    public double calculateReflectionFactor(double n1, double n2, UnitVector normal, UnitVector incident) {
        double densityFactor = n1 / n2;
        double cosI = -incident.dot(normal);
        double squaredSinI = densityFactor * densityFactor * (1 - cosI * cosI);
        double cosT = Math.sqrt(1 - squaredSinI);

        double rO = ((n1 * cosI - n2 * cosT) / (n1 * cosI + n2 * cosT));
        double rP = ((n2 * cosI - n1 * cosT) / (n2 * cosI + n1 * cosT));
        return (rO * rO + rP * rP) / 2;
    }

    /**
     * Calculate the direction of a refraction vector based on an incident vector and material densities.
     *
     * @param incident    incident unit vector comming from the 'outside'
     * @param normal      unit vector pointing to the 'outside'
     * @param densityFrom density 'outside'
     * @param densityTo   density 'inside'
     * @return UnitVector as the direction of the refracted ray
     */
    public UnitVector calculateRefractionVector(UnitVector incident, UnitVector normal, double densityFrom, double densityTo) {
        double densityFactor = densityFrom / densityTo;
        double cosI = -incident.dot(normal);
        double squaredSinI = densityFactor * densityFactor * (1 - cosI * cosI);
        if (squaredSinI > 1)
            return null; // Total internal reflection
        double cosT = Math.sqrt(1 - squaredSinI);
        return incident.scale(densityFactor).addSelf(normal.scale(densityFactor * cosI - cosT)).unit();
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
