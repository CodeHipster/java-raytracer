package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.scenery.PointLight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LightRayCaster {

    private List<PointLight> lights;

    public LightRayCaster(List<PointLight> lights) {
        this.lights = lights;
    }

    public List<LightRay> cast(final Collision<InverseRay> collision) {
        double lightIntensity = collision.target.material.diffuseFactor * collision.ray.intensity;

        //for each light create a lightray
        if (lightIntensity > 0.001) { //no need to add the light when it has to little impact on the scene.
            return lights.stream()
                    .map(light -> new LightRay(light, collision.target, collision.impactPoint, collision.ray.pixelPosition, lightIntensity, collision.ray.direction))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
