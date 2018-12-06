~~~~~~~~~~CHANGES MADE TO CODE FOR CUSTOMERS~~~~~~~~~~~~

EditPanel.java, line 216:
if (e.getValueIsAdjusting()) -> if (!e.getValueIsAdjusting())

This change was needed for one of our customers to update the list of key frames. Their keyframe
list would not update when paused if they selected new shapes so they needed this fix.

------

KeyFrameEditorPanel.java, line 96:
keyframe.getPositionY()), true); -> keyframe.getPositionX()), true);

This was an error in our code that was fetching the Y value where it needed to fetch the X value,
so it was fixed.