package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.SceneObject;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * https://en.wikipedia.org/wiki/Phong_reflection_model
 *
 * ( Kd( L.N )Id + Ks( R.V )^a*Is)
 */
public class PhongReflection {

    public static Color calculatePhong(UnitVector viewRay, PointLight light, Vector intersection, SceneObject surface, UnitVector surfaceNormal){
        // Kd = diffuse factor (together with reflection and refraction adds up to 1)
        // L = unitvector from point to light
        // N = unitvector surface normal
        // Id = color(RGB) for diffuse
        // Ks = specular factor (between 0 and 1, depending how bright you want the spot to be. 0.5 is nice)
        // R = unitvector reflected ray of light to intersection point
        // V = unitvector ray from point to camera
        // a = shinyness factor, between 1 & 256 (1 is a big spot, 256 is a tiny spot, use this together with the specular factor)
        // Is = color(RGB) for specular

        double Kd = surface.material.diffuseFactor;
        UnitVector L = light.position.subtract(intersection).unit();
        UnitVector N = surfaceNormal;
        // light filtered by surface, the diffuse part is absorbed by the material
        Color Id = surface.material.colorFilter.filter(light.color);
        double Ks = surface.material.specularFactor;
        UnitVector R = L.reflectOn(N).invert();
        UnitVector V = viewRay.invert();
        double a = surface.material.shininess;
        // specular is not filtered by material, because it is reflection (color is not absorbed by the surface of the material.
        Color Is = light.color;

        Color diffuse = Id.scale(Kd * L.dot(N));
        double dot = R.dot(V);
        if(dot > 0) { //Do not add specular when the intersection point is behind the light.
            Color specular = Is.scale(Ks * Math.pow(dot, a));
            return diffuse.add(specular);
        }else{
            return diffuse;
        }
    }
}
