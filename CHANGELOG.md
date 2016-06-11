#Jumbo Engine Changelog
This file will get updated with every update.

#####Upcoming
* Fixed particle system
* Updated audio system
* Wireframe and 'solid' render mode
* OpenGL 3 2D render mode
* 3D support
* More modularity support

##Version Alpha 1.1.0
###Added
* JumboRenderModes now have a resize() method that gets called when the screen is resized
* You can now get and set the disabled icon for JumboButtons
* JumboRenderModeNG now has a resize() method which doesn't do anything, as intended.

###Removed
* Jumbo.setGraphicsEnabled(boolean b) - resets the JumboRenderMode and JumboTextureBinder, which may hurt many projects

###Modified
* JumboTexture and JumboTextureBinder has been moved to a subpackage called *texture.* This is to set up for an upcoming feature to allow direct rendering to textures.
* The core.ng package has been removed, and it's classes move to core.modules. All preset implementations of JumboTextureBinders and JumboRenderModes go here.
* JumboTextureBinder has become an abstract class, and it's previous method has been moved to a new class called JumboTextureBinderGL11. JumboTextureBinderGL11 is also in core.modules.
* JumboRenderMode has become an abstract class, and it's previous method has been moved to a new class called JumboRenderModeGL11. JumboRenderModeGL11 is also in core.modules.
* JumboNGTextureBinder and JumboNGRenderMode have been respectively renamed to JumboTextureBinderNG and JumboRenderModeNG

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