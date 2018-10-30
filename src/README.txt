DESIGN DECISIONS:

Since the goal of this model is to be able to output a description of an animation as a list of
shapes and their motions, we decided to create classes for Shapes and States, as our two main
entities contained within the model. The model has a list of all Shapes in the animation, where a
Shape is an abstract class with two current extensions: rectangle and ellipse. All Shapes have a
name as identifier and a list of States. A State is a description of a Shape's appearance at a
certain time in ticks, based on its color, dimensions, and position. We made a class for positions
called Position2D to encapsulate the x and y values, and so that helpful methods such as
finding the distance between two positions can be implemented in the future if necessary. X and Y
values as well as shape dimensions are stored as doubles so that animation can be more fluid and
calculations can be more precise.

Our model interface has six methods. There are two overloaded versions of createShape(), which adds
a Shape of specified characteristics to the model's list of Shapes, where one version sets the
time of the Shape's first State to the beginning of the animation, and the other lets the user
customize this time. There are also two versions of createState(), one which adds a State to a
specified Shape with all the given characteristics, and the other (called createStatePars()) uses a
builder pattern to add a State to the Shape with only the specified characteristics in a parsable
String changing from the previous State, and unspecified characteristics remaining the same. The
last two methods are ways of outputting a description of the animation. The first, getAllMotions(),
outputs a String representation of all the shapes and their motions throughout the entire animation,
whereas the second, getCurrentMotions(), takes in a time in ticks and returns all the motions
occurring at the given time as a String. Once we have more information about how the controller and
view will function, we may want to change the output format of these last two methods from a String
to something else, such as a list of labeled States.


Everything contained in the model except Position2D and the model itself is package-private, because
they are only needed in the scope of the model. Position2D is not package-private so that the
controller can pass in positions as arguments to the model, and possibly the view down the line.
