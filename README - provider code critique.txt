~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CODE REVIEW~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Overall the provided code was well designed, with just a few confusing or questionable choices.
The Commands interface should be broken into two interfaces: a listener interface and a
controller interface. The current design is less flexible, because all listeners must also be
controllers and vice versa. If they were separated, the method startProgram() would move to the
controller interface. The listener aspect of this interface was well designed, clear, and very
similar to ours. The view interface had a nice, simple list of functionality, and it was not
cluttered with methods only relevant to text-based views, which was helpful. One confusing part
was how display() handled a lot of Swing initialization that could have been done in the
constructor instead, and this method name could be confused for refresh(), which also displays by
repainting the view. It would be clearer if all display() did was call setVisible(true), so you
can construct and initialize all the components of the JFrame separately from actually displaying
the view (making it visible). Additionally, the arguments for some methods seem redundant, since
setModel(), display(), and setListener() all take in the model. setListener() should especially
only need a listener, to set to a field of the class, and all components that need that listener
should be constructed with a reference to that field, to eliminate the necessity of using the
model in the method as well. It would be cleaner if there was only one method with a reference to
the model, updating the model info for everything in the view that needs it at once. This view's
UI seemed fully capable of everything we would expect, such as adding/removing/selecting shapes
and keyframes, displaying the animation with full playback controls, and even saving/loading
animations. Editing keyframes did not work correctly at first, but the providers fixed this for
us. The purple outline around the currently selected shape is a nice touch.

The model interfaces were easy to work with for the most part, mainly because they were very
similar to ours. The design of splitting them into a mutable and immutable model is very nice.
The name "IEasyAnimatorViewer" for a read-only model is a bit confusing, however, since we first
thought it was a View. Storing the canvas as a Rectangle type referred to as an "array of length
4" called "dimensions" seems confusing, since a java.awt.Rectangle is an Object, not an array,
stores more than just dimensions (also position), and especially because this program is an
animator which stores and draws rectangles in a completely different sense. Making the fields of
the public classes Size and Posn public instead of using getters for them seems to expose more to
the user than necessary, and may not be the best practice in terms of encapsulation. Lastly, the
getAllTimes() method's signature and javadoc was unclear to us because it returns "all the start
times of the shape with the given name", when each shape only has one start time. We believe we
figured out that this method returns the time of each keyframe of the indicated shape, although
this is not intuitive from looking at the javadoc and signature.

The provided code was convenient to reuse as all our functionality except for the view was very
similar. The view adapter took some tinkering to work with their interface/implementation, mainly
due to how often the model was used as a method parameter and display() performing actions that
were done in our view's constructor, but we solved these issues by storing the model and canvas
info as fields of the adapter, and balancing when we just modified these fields vs. actually
delegating to view methods. The listener adapter was extremely straight forward. All but two
methods, which had to convert a type, were one-liners, simply delegating to our listener. The
model adapter was fairly easy as well, although they were not all one-liners since we represented
a read-only model as a list of read-only shapes, instead of a separate interface/object. All of
the provided code was documented extensively and clearly except for the aforementioned confusion
with getAllTimes(), as well as the view interface taking in a model multiple times and separating
display() from the constructor.

Our interaction with the providers in terms of changing code was satisfactory, as they usually
responded promptly and helpfully to our emails. One requested fix was that changeKeyframe() had
inconsistent signatures in the listener and view interfaces. The former listed the parameters in
a different order than the latter, so that when the user tried to edit a keyframe's color it
could effect the dimensions, and vice versa. Similarly, for addShape() the order of the
parameters 'name' and 'type' were switched in the listener and view interfaces. Another bug which
we requested a fix for was exceptions being thrown if the user pressed certain buttons such as
change keyframe or remove shape with nothing selected. The last fix we needed was a change to
CellPanel so that it worked with our timer running a separate thread. Time would change in the
middle of the draw method calling getCurrentState(), so we needed them to temporarily save the
time, otherwise our model would throw an exception saying there is no state at the given time.
The above fixes were all made to our satisfaction, although the providers were unable to fix a
minor bug in which after the user adds/removes/edits keyframes, the shape JList would sometimes list
the wrong shapes at the wrong positions in the list until clicking on them. Additionally, their fix
for catching exceptions when buttons are pressed with nothing selected did not apply when a shape is
selected (but no keyframe) and "delete keyframe" is pressed. This is the only scenario we discovered
in which an exception is still thrown (although it does not break the program). Lastly, they could
not fix an error in which their JList causes an IndexOutOfBoundsException when removing a shape
(although the program continues to run), so we created a workaround by calling setShapes() on the
view again whenever the user presses a button which affects the model.
