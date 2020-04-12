# Performance test

The fastest operation will be given unit 1, other operations related to that.
Very crude implementation, but it gives some decent insight.

- performance comparison of basic operations
    - (10) sin & cos
    - (14) Math.pow (^ > 2)
    - (1.4) sqrt //uses dedicated hardware on my laptop?
    - (1) multiplication
    - (3) division
    - (1) addition
    - (1) subtraction
    
## floats vs doubles

floats are slightly faster 10% on basic operations (+-*/)
but a lot slower on sqrt(4 times) and sin/cos(2.5 times)
    
## pub/sub vs streams

unexpectedly the pub/sub seems to be faster than a parallel stream.