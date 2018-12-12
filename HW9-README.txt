~~~~~~~~~~~~~~~~~~~~~HW9 FEATURES EXPLANATION~~~~~~~~~~~~~~~~~~~~~

We implemented all three tiers of extra credit features to our EasyAnimator.

-----TIER 1: SCRUBBING-----
-AFFECTED FILES: EditPanel, AnimationEditorView

-Added a JSlider to EditPanel, communicating with the AnimationEditorView to tell the controller
 which time to set the tick to


-----TIER 2: ROTATIONS-----
-AFFECTED FILES: IState, State, IWritableShape, WritableShape, IEasyAnimatorModel, ShapePanel
 KeyFrameEditorPanel, TextEasyAnimatorView, SvgEasyAnimatorView, AnimationReader, AnimationBuilder

-Added an angle (theta) field to States (keyframes), and a getter in the interface (represents
 clockwise angle of rotation from its original orientation, where negative values are
 counterclockwise)
-IWritableShape's editKeyframe() method now also takes in an angle value to set
-Overloaded addMotion() in the model and writable shape interfaces to also take in angle1 and angle2
 parameters
-Used Graphics2D and transformations to rotate shapes for display
-Added a box to edit the angle in KeyFrameEditorPanel
-Added rotation support for the SVG view
-Changed AnimationReader, AnimationBuilder, and TextEasyAnimatorView to support our new text
 input/output for rotations: "rotation-motion" instead of "motion", with angle value after blue
 value


-----TIER 3: LAYERS-----
-AFFECTED FILES: IEasyAnimatorModel, EditorListener, EasyAnimatorController, AnimationEditorView,
 EditPanel, TextEasyAnimatorView, SvgEasyAnimatorView ASwingAnimatorView, ATextAnimatorView,
 IEasyAnimatorView, AnimationReader, AnimationBuilder

-The model now stores a 2D list of shapes, instead of a regular list of shapes. Each list is a layer
 of shapes. It also has a getLayers() method to get the 2D list
-Overloaded addShape() in the model to also take in a layer number to put it in. Default layer is 0.
-Added functionality for adding, removing, or re-ordering layers in EditPanel, AnimationEditorView,
 and EditorListener
-Added setLayers() method in the view interface to support getting a 2D list and interpreted it as
 layers, depending on view implementation
-Changed AnimationReader, AnimationBuilder, and TextEasyAnimatorView to support our new text
 input/output for layers: "shape-at-layer" instead of "shape", with layer number after shape type
