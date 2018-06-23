package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class Engine implements Runnable{
    private List<PointLight> pointLights;
    private List<InverseRay> inverseRays = new ArrayList<>();
    private List<LightRay> lightRays = new ArrayList<>();
    private List<Triangle> objects;
    private SubmissionPublisher<Pixel> pixelSink;

    public Engine(Camera camera, Scene scene, SubmissionPublisher<Pixel> pixelSink) {
        this.pixelSink = pixelSink;

        this.pointLights = scene.getPointLights();
        this.objects = scene.getTriangles();

        int pixelsX = camera.lens.width;
        int pixelsY = camera.lens.height;
        //TODO: use camera direction as well.
        //TODO: lens position and size in units
        //for now using 1 by 1 unit.
        double width = 1;
        double height = 1;
        for (int y = 0; y < pixelsY; ++y) {
            double ypos = ((double) y / pixelsY) * height - height / 2.0;
            for (int x = 0; x < pixelsX; ++x) {
                double xpos = ((double) x / pixelsX) * width - width / 2.0;
                UnitVector rayDirection = UnitVector.construct(xpos, ypos, camera.lens.offset);
                inverseRays.add(new InverseRay(1, rayDirection, camera.positioning.position, new PixelPosition(x, y)));
            }
        }
    }

    @Override
    public void run() {

        for (InverseRay ray : inverseRays) {
            //check collision with triangles;
            for (Triangle triangle : objects) {
                Vector collision = CollisionCalculator.calculateCollision2(triangle, ray);
                if(collision != null){
                    //add ray to lightRays
                    for(PointLight light: pointLights){
                        Vector direction = light.getPosition().subtract(collision); //useless, can be calculated when needed?
                        lightRays.add(new LightRay(light, triangle, UnitVector.construct(direction), collision, ray.getDestination(),ray));
//collision is incorrent?
                    }
                }
            }
        }
        for(LightRay ray: lightRays){
                    //calculate distance to intersection point, if closer then light, then add no color.

            //check collision with triangles;
            boolean hitLight = true;
            for (Triangle triangle : objects) {
                if(triangle == ray.triangle){
                    continue;
                }
                double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, ray);
                Vector rayToLight = ray.position.subtract(ray.light.getPosition());
                double distanceToLight = Math.sqrt(rayToLight.x * rayToLight.x + rayToLight.y * rayToLight.y + rayToLight.z * rayToLight.z);
                if(collisionDistance < distanceToLight){
                    hitLight = false;
                    break;
                }
            }
            if(hitLight){
                //calculate color

                //add phong?
                //diffuse
                //specular
                //ambient?

                UnitVector lightNormal = UnitVector.construct(ray.position.subtract(ray.light.getPosition()));
                UnitVector inverseLightNormal = lightNormal.inverse();
                UnitVector surfaceNormal = ray.triangle.surfaceNormal;
                UnitVector reflectNormal = lightNormal.reflectOn(surfaceNormal);
                UnitVector inverseViewNormal = ray.predecessor.direction.inverse();

                double diffuseFactor1 = surfaceNormal.dot(inverseLightNormal);

                double specularFactor1 = reflectNormal.dot(inverseViewNormal);
                specularFactor1 = Math.pow(specularFactor1,20);
                // L = vector from surface to light
                Vector L = UnitVector.construct(ray.position.subtract(ray.light.getPosition()));
                // N = normal of triangle        Vector vert0 = triangle.vertices[0];

                Vector edge1 = ray.triangle.vertices[1].subtract(ray.triangle.vertices[0]);
                Vector edge2 = ray.triangle.vertices[2].subtract(ray.triangle.vertices[0]);

                Vector n = edge1.cross(edge2);
                Vector N = UnitVector.construct(n);
                // R = the normalized reflection vector for L about the surface normal,
                //r=d−2(d⋅n)n
                Vector R = UnitVector.construct(L.subtract(N.multiply(ray.direction.dot(N) * 2)));
                // V = the normalized view vector.
                Vector V = ray.predecessor.direction;
                // diffuse = (L . N) * lightColor
                double diffuseFactor = L.dot(N) *-1;
                Color diffuse = new Color((int)(diffuseFactor * ray.light.color.r),(int)(diffuseFactor * ray.light.color.g),(int)(diffuseFactor * ray.light.color.b));
                // specular = ((R . V) ^ specularPower) * lightColor
                double specularFactor = Math.pow(R.dot(V), 1);
                Color specular = new Color((int)(specularFactor1 * ray.light.color.r),(int)(specularFactor1 * ray.light.color.g),(int)(specularFactor1 * ray.light.color.b));

                Color color = diffuse.filter(ray.triangle.colorFilter).add(specular); //we should not filter specular?
                pixelSink.submit(new Pixel(ray.getDestination(), specular));
            }
        }
        pixelSink.close();
    }
}

//    vec3 VertexToEye = normalize(gEyeWorldPos - WorldPos0);
//    vec3 LightReflect = normalize(reflect(gDirectionalLight.Direction, Normal));
//    float SpecularFactor = dot(VertexToEye, LightReflect);
//        if (SpecularFactor > 0) {
//                SpecularFactor = pow(SpecularFactor, gSpecularPower);
//                SpecularColor = vec4(gDirectionalLight.Color * gMatSpecularIntensity * SpecularFactor, 1.0f);
//                }