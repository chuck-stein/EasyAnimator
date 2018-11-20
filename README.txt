~~~~~~~~~~DESIGN CHANGES~~~~~~~~~~
-added methods to the model interface for inserting, removing, and editing keyframes, as well as a
 method to find the final tick of the animation so that the controller knows when to restart while
 looping.
-controller changed to implement new methods from our EditorListener interface
-controller's main "commence" method is now more advanced, looping until the view tells it that it
 is done animating, which depends on the particular view type
-timer moved from visual view to the controller, so that it can manage playback controls
 (implemented from the EditorListener interface)
-abstracted similarities between visual view and editor view to a common swing-based view abstract
 class, and similarities between SVG and text to a common text-based view abstract class.
-added more methods to view interface, including setTime() for the Swing-based views so that they
 know which states to animate based on the current tick number (and since visual views no longer
 keep track of time themselves), and setTicksPerSecond() for the SVG view because it no longer
 takes in speed in the constructor, since its constructor has been abstracted, and setListener()
 for the editor view so it can callback to the EditorListener upon receiving user input, and
 doneAnimating() to tell the controller when to stop telling the view to display, and resizeCanvas()
 for when we load in new animations with a differently sized canvas than what is currently
 displaying

~~~~~~~~~~DESIGN DECISIONS~~~~~~~~~~
-We created an EditorListener interface implemented by the controller, and passed the EditorListener
 to the editing view so that it can callback the relevant methods when on actionPerformed (because
 this view implements ActionListener). EditorListener deals with higher order changes, with no
 reference to buttons/other GUI elements. All that is handled by the view and calls the
 corresponding EditorListener method.

-We designed our AnimationEditorView to contain the shapePanel that we used in the regular visual
 view, as well as an editPanel, which itself consists of many sub-panels such as
 KeyFrameEditorPanel, KeyFrameListPanel, ShapeListBox, etc, some with their own sub-panels,
 therefore making use of the decorator pattern.

-We designed our GUI so that it is mostly displaying the animation, with an
 editing interface to the left with a list of the shapes in the animation, and a list of that
 shape's keyframes next to it. Below that is a box with editable values for the selected keyframe.
 Above the list boxes are playback controls for displaying the animation, such as restart, slow
 down, speed up, toggle looping, and toggle playback, so that play/pause are one button. There are
 also buttons for adding and removing keyframes/shapes and saving/loading animations. We chose this
 layout because it clearly displays the animation, as well as all of that animation's information,
 and includes easy to understand buttons and editing controls, along with visual cues for when the
 user interacts with the animation.