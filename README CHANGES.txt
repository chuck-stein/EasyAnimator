Changes made to code for customers. 

Class EditPanel:

line 216  changed 
if (e.getValueIsAdjusting()) -> if (!e.getValueIsAdjusting())

This change was needed for one of our customers to update the list of key frames. Their keyframe list would not update when paused if they selected new shapes so they needed this fix.

Class KeyFrameEditorPanel:

line 96 changed
keyframe.getPositionY()), true); -> keyframe.getPositionX()), true);
This was an error in our code that was fetching the Y value where it needed to fetch the X value so it was changed. 


