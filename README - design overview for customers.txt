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