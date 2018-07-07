package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.geography.Vector;

public class Vertex extends Vector {

    public Vertex(double x, double y, double z) {
        super(x, y, z);
    }


    // aka move
    //TODO: fix the vertex vector mess, why xyz final? etc.
    public Vertex translate(Vector translation){
        Vector translated = this.add(translation);
        return new Vertex(translated.x, translated.y, translated.z);
    }
}
