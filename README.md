# expr

Support for partially bound functional expressions with a nice string representation.

Example:

    val add = FunExpr("+") { a: Int, b: Int -> a + b }.combinable
    val increment = add - Value(1)
    assertEquals(3, increment(2))
    assertEquals(5, (add - Value(3) - Value(2))())
    assertEquals("+-3-5", (add - Value(3) - Value(2)).toString())

For real life examples, see the straightway.testing.flow implementation.

## Status

This software is in pre-release state. Every aspect of it may change without announcement or
notification or downward compatibility. As soon as version 1.0 is reached, all subsequent changes
for sub versions will be downward compatible. Breaking changes will then only occur with a new
major version with according deprecation marking.

## Include in gradle builds

To include this library in a gradle build, add

    repositories {
        ...
        maven { url "https://straightway.github.io/repo" }
    }

Then you can simply configure in your dependencies:

    dependencies {
        compile "straightway:expr:<version>"
    }
