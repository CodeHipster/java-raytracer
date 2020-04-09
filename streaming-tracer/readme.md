A streaming pipeline implementation of the raytracer
=====================================================

this raytracer is build up from processors that each do work for the raytracer independently and on multiple threads.

Using Java Flow (pub/sub), this solution is fully reactive. Buffers help with balancing the load

inverseRayPublisher -> depth -> collision   -> inverseRayCaster -> depth (loop back)
                                            -> lightRayCaster -> shadow -> render -> other system
                                            
TODO:
-----

- introduce a load balancer 
    - it can prioritize subscribers (block input from initializer when at 50% capacity, but still accept from other subscribers)
    - it decides to go broad(trace rays with lowest depth) or narrow(trace rays with highest depth)
- add debug output