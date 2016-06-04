#Jumbo Engine Changelog
This file will get updated with every update.

#####Upcoming
* Fixed particle system
* Updated audio system
* Wireframe render mode
* Solid color only texture handler
* OpenGL 3 2D render mode
* 3D support
* More modularity support

##Version Alpha 1.0.1
###Added
* Null checking for JumboMathHandler.calculatePostiion(). You can now have bounds = null
* JumboButton got some more overloaded getters and setters for hover icon
* JumboGraphicsGroup can now change the maintain ratio values of all of it's children

##Changed
* JumboButton's hovericon getters and setters now correctly camel cased.

##Version Alpha 1.0.0
###Removed
* GPUCalculator class, it's OpenCL kernel, and all references to it in JumboMathHandler
* IOManager class
* collisionPixelPerfect() in JumboMathHandler
* Values remaining from trip mode in JumboMathHandler

###Added
* Changelog, and thus it is version 1.0.0
* Perspective matrix calculation method to JumboMathHandler

###Changed
* Some documentation