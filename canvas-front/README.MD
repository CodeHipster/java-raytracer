# desktop-visualizer
Module that visualizes the ray-tracer output using java swing.

It has a menu to start the rendering and then creates new windows depending on the amount of camera's in the scene.

## menu
The menu makes it possible to:
- select a scene (TODO)
- configure a camera (TODO)
- start rendering

## render output
The render frame shows the result of the rendering. 
This window and the ray-tracer output are coupled through a shared ImageBuffer.
The pixels generated from the ray tracers are placed on the image buffer.
The buffer is painted at an interval on the panel.

# java modules
This module depends on the raytracer-api during compile time.
But also on a RayTracerService & SceneService implementations during runtime. 
Make sure to add the implementations when running the program. (Add a runtime dependency in Intellij)