#Jumbo Engine Changelog
This file will get updated with every update.

*Make sure new code follows the [style guide.](https://github.com/liavt/Jumbo-Engine/wiki/Style-Guide)*

#####Upcoming
* Fixed particle system
* Updated audio system
* New preset modules
* OpenGL 3 2D render mode
* 3D support
* More modularity support

##Version Alpha 1.2.0
*Lots of refactoring to make names more understandable and start on JUnit testing*

###Added
* getTexture(String) to JumboImageHandler, which returns JumboTexture instead of BufferedImage. Part of the eventual goals of this engine is to stop relying on AWT classes. However, BufferedImage and ImageIO.read() will stay, as they are extremely complex to implement and unneccessary to replace.
* JUnit to classpath, and test.jumbo packages. Currently, there is only one unit test to make sure it works. As time goes on, more tests will be added.

###Modified
* JumboPainter.loadPainter has been changed to simply be a constructor
* All references to java.awt.Rectangle have been changed to Quad, a new class in the components package. This is part of the migration away from AWT classes. Quad extends Position and adds the width/height parameters. All functionality is the same as java.awt.Rectangle.
* FloatRectangle is now called QuadF
* FloatPosition is now called PositionF
* Position3D now extends Position
* MinMaxVector is now called Range
* LambdaObject is now called Wrapper
* LambdaInteger is now called IntWrapper
* TripleFloat is now called Position3DF

##Version Alpha 1.1.5
###Added
* JumboColor is now Serializable
* The JumboPainter class, which allows you to do direct rendering to a JumboTexture, similar to the Graphics class found in Java AWT. No implementations have been built with it yet.
* Wireframe render modules, named JumboRenderModuleWireframe
* Installation instructions to README.md

###Modified
* JumboColor now stores data as a series of shorts to save memory.
* JumboRenderMode has been renamed to JumboRenderModule, and has been moved to core.modules.
* JumboTextureBinder has been renamed to JumboTextureModule, and has been moved to core.modules.
* Classes that previously started with JumboRenderMode or JumboTextureBinder have been renamed to their corresponding module names (IE: JumboRenderModeGL11 has been renamed to JumboRenderModuleGL11)
* JumboTexture's *solidcolor* and *fade* have been created final for increased speed
* Premade modules have been moved to core.modules.presets

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
