Design Changes from Hw5:

The first change was shapes no longer just had keyframe states. Shapes now had a list of motions which have a start and end state. The model now not only contains a list of shapes but also contains information about the canvas they will be displayed on. Specifily the x,y origin of the canvas and its width and height. Motions, Shapes, and States all use an interface now so specific implementation is never revealed.

Model interface changes:
The model now has methods to set the canvas values of the animation model and get each of them through specific getters. AddShape() has replaced createShape and now adds a shape with a name and type, but no defined motions to acomidate defining a shape before its motions. addMotion() relaced createState as we now add motions instead of key frames to our model. This method insures that a shapes motions are stored in the correct order and that only valid motions can be added, start and end the same, and no time overlap. removeMotion() has been added to allow a user to take motions away from a shape if they want to be edited. This enforeces that the motion that is trying to be removed exsists. removeShape() has also been added which removes a shape from the model. The shape must exsist to be removed. getallMotions and getCurrentMotion have been removed from the model. Instead now to get data from the model a list of IReadableShapes is passed out. This list contains the shapes with all their motion data in them, however there is no way to mutate this data. This method to get this shapes will only succeed if there are no gaps in the motions of any shapes, otherwise the model with tell you which shape has a gap and from what time this gap exsists. 

Only interfaces are public, and the model class. All other classes used are package private and so they cannot be accessed from an outside source. 

Design Choices for Hw6:

The goal of Hw6 was to design a set of views. A simple view interface was created with two methods. setShapes() which takes in a list of IReadableShapes, and animate() which animates the view. This very simple interface was chosen as the views we needed to design had very simplified duties. They were to display the information and do nothing else. setShapes obtains all the information a view needs and animate shows whatever the view needs to show.However for Hw7 the views need to comunicate back to the controller with human input so the interface will need more functionality, to send action events. While not part of the interface the three views we implemented required canvas information and ticks persecond, as a result these are all part of the constructor for everyview.

TextViews:
These views took in the information from set shapes which gave them the list. These view both then used animate to before string concatenation on the list of shapes using methods like getMotions(), and getIntermediateState() allowing them to work with all the informaion in the shapes to generate the proper text documents. 

VisualView:
This view like the text view took in a list of shapes as its information. The visual view uses a ShapePanel to display its information. Using the getIntermideateState function of shapes it is able to see what each shape looks like at a given tick, and then draw them on the canvas. A timer is used, based on the ticks persecond inputed from the main arguments, to advance the image in time and display. When the window of the image is closed it exits the program. 

Controller:
A very simple controller was created that contatined a model and a view. The controller passed the info from the model to the view and then told the view to animate. This controller will need to have functionality added to it in the future to allow handeling of information from the view back to the model. 

MainClass Excellence:
The Excellence class runs the program. Based off a given file path it will read a file and create a model from the given file using AnimationReader. It will then use a viewbuilder to construct the desired view type with the desired ticks persecond, or a default speed if left blank. For textual views an output file can be specified as well, allowing the view to write to and create a file. If any input in the arugments for the main class are invalid an error window will pop up with the given disription of why things went wrong. 