# Jumbo-Engine
[![GPL Licence](https://badges.frapsoft.com/os/gpl/gpl.svg?v=103)](https://github.com/liavt/Jumbo-Engine/blob/master/LICENSE)
[![Java version 8 update 45](https://img.shields.io/badge/java-8.45-blue.svg)](http://www.oracle.com/technetwork/java/javase/8u45-relnotes-2494160.html)
[![GitHub stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star&maxAge=2592000)](https://github.com/liavt/Jumbo-Engine/stargazers)

A Java engine that uses LWJGL.

**Note: this engine is in alpha. Updates may break previous functionality. Use at your own risk.**

## Features
* Fast and easy hardware-accelerated [OpenGL](https://www.opengl.org/) rendering.
* Completely modular core, allowing you to write your own implementation of texture binding, entity rendering, audio playback, and more.
* Versatile object heirarchy.
* Simple String IO.
* High-quality [OpenAL](https://www.openal.org/) audio player.
* Input handling system to provides fast and easy mouse, key, and joystick support, anywhere.
* Image editing and loading library.
* Developer tools, like error handling, commands, and logger.
* Bitmap text renderer, using [Angelcode's BMFont](http://www.angelcode.com/products/bmfont/), with support for italics, colors, kerning, multi-lines, and different sizes.
* Easy and flexible particle generation, with support for anything from fire effects, to rain, to sparkles.
* Optional texture generator for dynamic sizing.

##Installation
Later, there will be a .jar file you need to add. Right now, as it is in heavy development, you will have to add the project as specified below.

##How can I contribute?
Clone the project through git, and add everything in the /src/ folder to your build path. The only dependency is LWJGL 2 and it's natives, which can be found on their [website.](http://wiki.lwjgl.org/wiki/Downloading_and_Setting_Up_LWJGL) The LWJGL jar files are located in the /jars/ folder. You will need to download natives.

Contributions you make need to follow the [style guide.](https://github.com/liavt/Jumbo-Engine/wiki/Styleguide)

## FAQ
Why is it called Jumbo?
* Jumbo means big, and this library is supposed to handle everything! The idea behind it is that this is a versatile library - and thus it is jumbo.

What dependencies does it have?
* Currently, only [LWJGL 2](http://legacy.lwjgl.org/) and Java 8 (as lambda expressions are heavily used). You will have to include the LWJGL natives in the build path.

How hard is it to use?
* It comes with pre-made classes that are easy to use, with the harder option of you creating your own components.

What is the syntax?
* It is very Swing-like.

How fast is it?
* It is hardware accelerated, so it may vary from system to system.

Documentation?
* Very little. However, we are slowly adding that.

What version of OpenGL does it use?
* Currently 1.1. This means no shaders. However, it is possible for you to implement your own OpenGL implementation. All OpenGL code in the engine can be changed without touching the source code at all!

What FPS does it run at?
* You can control that!

What are the specifications of the audio player?
* Has surround sound support, can play 16 different sounds at once, and can store any number in memory (by default it allocates space for 256.)

Is it multi-platform?
* Of course! It is written in Java and uses OpenGL, so it would be a waste not to make it multi-platform. The IO operations are also multi-platform as well.

I hate OpenGL/OpenAL code. Do I have to do any of that?
* None at all! It comes with pre-made implementations of both! You can rewrite them entirely without touching any source code!

Do you have any plans to switch the default implentations to Vulkan when it comes out?
* Not currently.

How long have you been working on it?
* Since May 2015.

Why use the old LWJGL 2.0 and not the new and improved LWJGL 3.0?
* Well, LWJGL 3.0 is currently in alpha. We don't find it worth the work to rewrite a lot of the engine to an alpha build, and change it every month. When it finally releases, of course we will change to it, as by then, all the major bugs will be sorted out, the main API will be decided, and we will have access to all of it's major features, like VR support, Vulkan, GLFW, and other cool APIs.
