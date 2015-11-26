# Jumbo-Engine

A Java 2D engine that uses LWJGL.

# Features
* Fast and easy hardware-accelerated [OpenGL](https://www.opengl.org/) rendering. Don't like OpenGL? Override it, without changing the style of the engine and touching the source code at all!
* Versatile object heirarchy.
* Simple String IO.
* High-quality [OpenAL](https://www.openal.org/) audio player.
* Input handling system to provides fast and easy mouse, key, and joystick support, anywhere.
* Image editing and loading library.
* Built-in drug trip mode!
* Developer tools, like error handling, console logger, and wireframe mode.
* Bitmap text renderer, using [Angelcode's BMFont](http://www.angelcode.com/products/bmfont/), with support for italics and colors.
* Easy and flexible particle generation, with support for anything from fire effects, to rain, to sparkles.

# FAQ
Why is it called ´Jumbo?´
* Jumbo means big, and this library is supposed to handle everything! The idea behind it is that this is a versatile library - and thus it is jumbo.

What dependencies does it have?
* Currently, only [LWJGL 2](http://legacy.lwjgl.org/) and Java 8 (as lambda expressions are heavily used). You will have to include the LWJGL natives in the build path.

How hard is it to use?
* It comes with pre-made classes that are easy to use, with the harder option of you creating your own components.

What is the syntax?
* It is very Swing-like.

How fast is it?
* It is hardware accelerated, so it may vary from system to system. Locked at 60 FPS, using the default renderer, the engine was able to render 10,000 entities at once, only using up 5% of my Intel i7.

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
