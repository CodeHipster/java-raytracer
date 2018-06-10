# Raytracer

## modules

### desktop visualizer
desktop interface for the raytracer

### raytracer api
api module defining the interface for a raytracer
- input: scenery, camera(pixels wide, pixels high, position, direction)
- output: stream of pixels (x,y, color)

### raytracer
implementation of the api  
- first phase: rays without reflection
