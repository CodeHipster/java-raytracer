package oostd.am.raytracer.api.scenery;

import java.util.List;

public class Scene {
    //to be upgraded to a model that partitions
    private List<Triangle> triangles;
    private List<PointLight> pointLights;

    public Scene(List<Triangle> triangles, List<PointLight> pointLights){
        this.triangles = triangles;
        this.pointLights = pointLights;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public List<PointLight> getPointLights() {
        return pointLights;
    }
}
