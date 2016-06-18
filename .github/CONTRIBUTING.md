**Before commiting to the repository, look over this document to ensure you follow standards**

#Style guide for Jumbo Engine
*All code made after this document's creation must follow the style guide*

##Meta

###Terminology
* A utility is a class where every method is static and manipulates other objects.
* A component has no purpose on it's own, but can me used by other classes.
* A handler is a class that directly deals with entities
* An entity is any class that extends JumboEntity.
* A class refers to any interface, enum, class, or annotation.
* The client is any programmer that uses Jumbo Engine.
* The engine refers the the Jumbo Engine as a whole

###Git
* Pushing a code change to the 'master' branch must have a version number and be marked in the changelog.
* The 'master' branch must contain code that is ready to be used and passes all unit tests.
* The 'snapshot' branch contains experimental or unfinished code. Updates to this branch does not have to be recorded in a changelog. Once this branch passes all unit testing and is deemed finished, it may be merged into master.
	* When 'snapshot' gets merged into 'master,' the changelog must be updated with a new version number and all changes made.
* Commits **must** follow this style guide

###Versioning
* A versioning phase is one of the following:
	* Unstable
	* Alpha
	* Beta
	* Stable
* The Alpha phase is where core features are added and changed. In this phase, the engine will be very unstable and every feature will be subject to change. Javadoc and unit testing is not guarenteed.
* The Beta phase is where the features will be refined, bug fixed, and documented.
* Versions are formatted by [Phase] x.x.x
	* The first number will change in a major rewrite.
	* The second number is called a 'full release,' and will change when a major new feature is added.
	* The third number is called a 'partial release,' and will be much more frequent. Partial releases usually have bug fixes and set up infrastructure for a later full release.

###Changelog
* All new versions will be recorded in a changelog called CHANGELOG.md, located in the root directory.
* Each version will start with the version number, in a double header (2 pound signs)
* Next, it will have 4 categories, labelled Deprecated, Modified, Removed, and Added.
	* Categories must start with a triple header (3 pound signs) specifying it's name.
	* If a category is empty, it does not need to be listed.
	* Actual changes must be bullet pointed
* If the update will break previous functionality, it must say in italics under the header *This update will break previous code*

###Styleguide
* Categories will start with double headers
* Subcategories will start with triple headers
* Topics will be bullet pointed
	* There may be subtopics that can have embedded bullet points
* It doesn't matter if a bullet point doesn't end in a period
* Examples may be given as long as they start with EXAMPLE:

##Code Style

###Deprecation
* While the engine is in Alpha, classes will not be deprecated, and will be moved around, deleted, and changed without warning. Almost every update will break previous functionality.
* After Alpha, classes will be deprecated before removal or modification. The Javadoc will specify the replacement and why it was deprecated.
* The classes will be removed in an arbritrary number of releases later.
	* Always assume that it will be removed next release.
	* It will only be removed in a full release.

###Static vs not static
* Entities and components must be mutable, meaning they have a constructor.
	* They may contain static utility methods that are optional use by the client.
* Handlers and utlities must only have static methods, have a private constructor, and be final.

###Testing
* Unit tests are to be done with JUnit, whose jar is located in /jars
* Every unit test must extends TestCase
* Every unit test must specifically test 1 class.
	* The unit test may use other classes to assist testing
* The unit test's name is to be the name of the class that it is testing followed by the word 'Test.'
	* A unit test's name is exempt from other naming rules, as long as it the follows the rule above.
	* It is okay if a unit test has a naming conflict
	* EXAMPLE: A unit test for *JumboEntity* will be called *JumboEntityTest* and a test for *Capsule* will be called *CapsuleTest*
* EXAMPLE: *JumboTest* tests methods found in the *Jumbo* class. It may use the *Display* class from LWJGL to test *Jumbo.* The *JumboColorTest* tests methods found in *JumboColor*
* Unit tests must be located in the source folder /test
* The package for a unit test must mirror the package for the class it is testing, except it starts with *test.jumbo* instead of *com.jumbo*
	* EXAMPLE: A unit test for *JumboScene,* which is located in *com.jumbo.core,* would be placed in *test.jumbo.core* 
* For every testing package, there must be a testing suite which runs all of the tests in that package.
	* The suite's name must be the word 'Jumbo' plus the package name followed by the word 'Suite.'
		* The name only needs to include the lowest level package
	* EXAMPLE: A suite for the test.jumbo.tools.calculations would be called JumboCalculationsSuite
* The test.jumbo package contains a *testing runner* and a *testing suite.*
* The *testing suite* is a testing suite which runs all of the individual package suites.
* The *testing runner* has a main() method which runs the *testing suite.*
	* The runner can contain other methods to assist testing
* The *testing suite* and *testing runner* are to be called JumboTestingSuite and JumboTestingRunner respectively.
* No other class may go into test.jumbo

##Files

###Encoding
* All files are encoded in UTF-8, for maximum compatibility.

###Names
* To prevent naming conflicts, all non-component or utility classes must start with 'Jumbo.'
	* Utility classes that have to directly deal with Jumbo classes must be prefixed with Jumbo. If the utility class has 1 or less references to any class in Jumbo Engine, it may go unprefixed.
	* If a utility class would have a naming conflict with another class without a prefix, it may get a prefix.
* If a component or utility class has a naming conflict in the JDK or LWJGL, there are multiple options:
	* Rename it to a synonomous word without a conflict
	* Add Jumbo to the start, but this is unrecommended.
* Classes must be named in PascalCase.
	* Class names must not exceed 3 words + Jumbo.
		* If this occurs, the class name must be shortened or be turned into an acronym
* Class names may be acronyms, however they must follow the following guidelines:
	* They start with 'Jumbo'
	* Every letter in the acronym must be capitalized
	* There may not be any full words after an acronym. There may be words before an acronym.
* Utility classes may end in 'Utility'
* Handler classes must end in 'Handler'
* Non-entity classes that extend other classes must start with the class name they are extending, and then an acryonym describing what changed.
	* For example, Position3 extends Position, and it has a 3 at the end because it has 3 values instead of 2.
		* Position3f is also like Position, but has 3 values that are floats
