package oostd.am.raytracer.api.scenery;

import java.util.List;

public class Scenery {
    //to be upgraded to a model that partitions
    List<Triangle> triangles;
    List<PointLight> pointLights;

    public Scenery(List<Triangle> triangles, List<PointLight> pointLights){
        this.triangles = triangles;
        this.pointLights = pointLights;
    }
}
