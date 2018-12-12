~~~~~~~~~~~~~~~~~~~~~HW9 FEATURES EXPLANATION~~~~~~~~~~~~~~~~~~~~~

We implemented all three tiers of extra credit features to our EasyAnimator.

-----TIER 1: SCRUBBING-----
-AFFECTED FILES: EditPanel, AnimationEditorView

-Added a JSlider to EditPanel, communicating with the AnimationEditorView to tell the controller
 which time to set


-----TIER 2: ROTATIONS-----
-AFFECTED FILES: IState, State, IWritableShape, WritableShape, IEasyAnimatorModel, ShapePanel
 KeyFrameEditorPanel, TextEasyAnimatorView, SvgEasyAnimatorView, AnimationReader, AnimationBuilder

-Added an angle (theta) field to States (keyframes), and a getter in the interface (represents
 clockwise angle of rotation from its original orientation)
-IWritableShape's editKeyframe() method now also takes in an angle value to set
-Overloaded addMotion() in the model and writable shape interfaces to also take in angle1 and angle2
 parameters
-Used Graphics2D and transformations to rotate shapes for display
-Added a box to edit the angle in KeyFrameEditorPanel
-Added rotation support for the SVG view
-Changed AnimationReader, AnimationBuilder, and TextEasyAnimatorView to support our new text
 input/output for rotations


-----TIER 3: LAYERS-----
-AFFECTED FILES: IEasyAnimatorModel, EditorListener, EasyAnimatorController, AnimationEditorView,
 EditPanel, TextEasyAnimatorView, SvgEasyAnimatorView ASwingAnimatorView, ATextAnimatorView,
 IEasyAnimatorView, AnimationReader, AnimationBuilder