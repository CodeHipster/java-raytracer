# desktop-visualizer
Module that visualizes the raytracer output using java swing.

It shows 2 windows
- menu
- render output

## menu
The menu makes it possible to:
- select a scene
- configure a camera
- start rendering

### scene
yet to figure out

### camera
Camera consists of a position, orientation and a lens
The lens is specified in width and height in integers, which corresponds with the window size.
For each pixel on the lens a ray is cast. The lens is 1 unit in front of the camera position.
`TODO: make the distance between the camera and lens configurable?`     

## render output
The render output window shows the result of the rendering. 
This window is continuously updated as raytracer renders more pixels.

# java modules
This module depends on the raytracer-api during compile time.
But also on a raytracer-api implementation during runtime. 
Make sure to add an implementation when running the program. (Add a runtime dependency in Intellij)