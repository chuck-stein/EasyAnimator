import cs3500.animator.controller.MockEditorListener;
import cs3500.animator.model.hw05.IState;
import cs3500.animator.view.IEasyAnimatorView;

import cs3500.animator.view.MockChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.AnimationEditorView;


import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;


public class ListenerTest {

  protected StringBuilder output;
  protected IEasyAnimatorModel m;
  protected EditorListener editorListener;
  protected ActionListener actionListener;
  protected ChangeListener changeListener;

  @Before
  public void init() {
    output = new StringBuilder();
    m = new EasyAnimatorModel();
    IEasyAnimatorView v = new AnimationEditorView(100, 100, 500, 500);
    editorListener = new EasyAnimatorController(v, m, 60);
    actionListener = new AnimationEditorView(200, 200, 300, 300);
    EditorListener mock = new MockEditorListener(output);
    ((AnimationEditorView) actionListener).setListener(mock);
    changeListener = new MockChangeListener();
    ((MockChangeListener) changeListener).setListener(mock);
  }

  @Test
  public void testAddShape() {
    assertEquals(new ArrayList<IReadableShape>(), m.getShapes());
    editorListener.addShape("a pretty rectangle", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    editorListener.addShape("a nice little ellipse", ShapeType.ELLIPSE);
    assertEquals(2, m.getShapes().size());
  }

  @Test // ensure that trying to add a pre-existing shape name has no effect
  public void testAddingDuplicateShape() {
    editorListener.addShape("shapes cannot appear twice", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    editorListener.addShape("shapes cannot appear twice", ShapeType.ELLIPSE);
    assertEquals(1, m.getShapes().size());
  }


  @Test
  public void testRemoveShape() {
    editorListener.addShape("please dont delete me i dont wanna commence", ShapeType.ELLIPSE);
    assertEquals(1, m.getShapes().size());
    editorListener.removeShape("please dont delete me i dont wanna commence");
    assertEquals(0, m.getShapes().size());
  }

  @Test // ensure that trying to delete a non-existent shape has no effect
  public void testRemoveNonExistentShape() {
    editorListener.addShape("a rectangle", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    editorListener.removeShape("that rectangle i just added");
    assertEquals(1, m.getShapes().size());
  }

  @Test
  public void testInsertKeyframe() {
    editorListener.addShape("my beautiful four-sided beast", ShapeType.RECTANGLE);
    editorListener.addShape("a round boi", ShapeType.ELLIPSE);

    assertEquals(0, m.getShapes().get(0).getMotions().size());
    editorListener.insertKeyframe("my beautiful four-sided beast", 5);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    assertTrue(onlyOneKeyframe());
    editorListener.insertKeyframe("my beautiful four-sided beast", 30);
    assertFalse(onlyOneKeyframe());
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    editorListener.insertKeyframe("my beautiful four-sided beast", 1);
    assertEquals(2, m.getShapes().get(0).getMotions().size());

    assertEquals(0, m.getShapes().get(1).getMotions().size());
    editorListener.insertKeyframe("a round boi", 19);
    assertEquals(1, m.getShapes().get(1).getMotions().size());

    assertEquals(2, m.getShapes().get(0).getMotions().size());
    editorListener.insertKeyframe("my beautiful four-sided beast", 19);
    assertEquals(3, m.getShapes().get(0).getMotions().size());
  }

  @Test // ensure that trying to add a keyframe to a non-existent shape has no effect
  public void testInsertKeyframeToNonExistentShape() {
    m.addShape(ShapeType.ELLIPSE, "a shape that will not be affected", 0);
    assertTrue(noKeyframesExist());
    editorListener.insertKeyframe("this shape does not exist", 70);
    assertTrue(noKeyframesExist());
  }

  @Test // ensure that trying to add a keyframe at a tick less than 1 has no effect
  public void testInsertKeyframeAtNonPositiveTimes() {
    m.addShape(ShapeType.RECTANGLE, "a rectangle that will not be affected", 0);
    m.addShape(ShapeType.ELLIPSE, "an ellipse that will not be affected", 0);
    assertTrue(noKeyframesExist());
    editorListener.insertKeyframe("a rectangle that will not be affected", 0);
    assertTrue(noKeyframesExist());
    editorListener.insertKeyframe("an ellipse that will not be affected", -4);
    assertTrue(noKeyframesExist());
  }

  @Test // ensure that trying to add a keyframe that already exists has no effect
  public void testInsertPreExistingKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "i only want one keyframe at t=6", 0);
    editorListener.insertKeyframe("i only want one keyframe at t=6", 6);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    editorListener.insertKeyframe("i only want one keyframe at t=6", 6);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
  }

  @Test
  public void testRemoveKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "curvy", 0);
    m.insertKeyFrame("curvy", 15);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    editorListener.removeKeyframe("curvy", 15);
    assertEquals(0, m.getShapes().get(0).getMotions().size());
  }

  @Test // ensure that trying to remove a keyframe with no matching shape name or tick has no effect
  public void testRemoveNonExistentKeyframe() {
    m.addShape(ShapeType.RECTANGLE, "rectosaurus", 0);
    m.insertKeyFrame("rectosaurus", 1);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    editorListener.removeKeyframe("rectosaurus", 2); // invalid time
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    editorListener.removeKeyframe("rectosourus", 2); // invalid name
    assertEquals(1, m.getShapes().get(0).getMotions().size());
  }

  @Test
  public void testEditKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "Lipsy", 0);
    m.insertKeyFrame("Lipsy", 25);
    m.insertKeyFrame("Lipsy", 50);
    assertTrue(bothKeyframesAreIdentical());
    editorListener.editKeyframe("Lipsy", 50, 123, 456, 789, 10, 0, 11, 12, 13);
    assertFalse(bothKeyframesAreIdentical());
  }

  @Test // ensure that trying to edit a keyframe with no matching shape name or tick has no effect
  public void testEditNonExistentKeyframe() {
    m.addShape(ShapeType.RECTANGLE, "get rect", 0);
    m.insertKeyFrame("get rect", 85);
    m.insertKeyFrame("get rect", 200);
    assertTrue(bothKeyframesAreIdentical());
    // invalid time:
    editorListener.editKeyframe("get rect", 199, 3, 3, 3, 3, 0, 3, 3, 3);
    assertTrue(bothKeyframesAreIdentical());
    // invalid name:
    editorListener.editKeyframe("get wrecked", 85, 4, 5, 23, 60, 0, 100, 200, 150);
    assertTrue(bothKeyframesAreIdentical());
  }

  @Test // ensure that trying to edit a keyframe to invalid specifications has no effect
  public void testInvalidEditKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "Ellie", 0);
    m.insertKeyFrame("Ellie", 34);
    m.insertKeyFrame("Ellie", 162);
    assertTrue(bothKeyframesAreIdentical());
    // invalid dimensions:
    editorListener.editKeyframe("Ellie", 162, 50, 300, -60, 0, 0, 20, 36, 180);
    assertTrue(bothKeyframesAreIdentical());
    // invalid color values:
    editorListener.editKeyframe("Ellie", 34, 90, 240, 100, 100, 0, 50, -1, 300);
    assertTrue(bothKeyframesAreIdentical());
  }

  /**
   * Return true if there are no keyframes in any of the test model's shapes.
   *
   * @return true if there are no keyframes in any of the test model's shapes
   */
  private boolean noKeyframesExist() {
    for (IReadableShape s : m.getShapes()) {
      if (!s.getMotions().equals(new ArrayList<IMotion>())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns true if the test model's first shape's first motion's start and end time are the same,
   * which means its start and end states are the same according to the way motions are constructed,
   * which means the motion really only consists of one keyframe.
   *
   * @return whether or not the first shape's first motion is only one keyframe
   */
  private boolean onlyOneKeyframe() {
    // the first shape's first motion:
    IMotion motion = m.getShapes().get(0).getMotions().get(0);

    IState start = motion.getIntermediateState(motion.getStartTime());
    IState end = motion.getIntermediateState(motion.getEndTime());
    return start.getTick() == end.getTick();
  }

  /**
   * Returns true if the test model's first shape's first motion is connecting two identical
   * keyframes (with potentially different ticks).
   *
   * @return whether or not the first two keyframes in the test model are identical
   */
  private boolean bothKeyframesAreIdentical() {
    // the first shape's first motion:
    IMotion motion = m.getShapes().get(0).getMotions().get(0);
    IState start = motion.getIntermediateState(motion.getStartTime());
    IState end = motion.getIntermediateState(motion.getEndTime());
    boolean sameColor = start.getColorR() == end.getColorR()
            && start.getColorG() == end.getColorG()
            && start.getColorB() == end.getColorB();
    boolean samePosition = start.getPositionX() == end.getPositionX()
            && start.getPositionY() == end.getPositionY();
    boolean sameDimensions = start.getWidth() == end.getWidth()
            && start.getHeight() == end.getHeight();
    return sameColor && samePosition && sameDimensions;
  }

  @Test
  public void testTogglePlaybackTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle playback"));
    assertEquals("pause\n", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle playback"));
    assertEquals("pause\nplay\n", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle playback"));
    assertEquals("pause\nplay\npause\n", output.toString());
  }

  @Test
  public void testRestartTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "restart"));
    assertEquals("restart\n", output.toString());
  }

  @Test
  public void testToggleLoopingTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle looping"));
    assertEquals("looping on\n", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle looping"));
    assertEquals("looping on\nlooping off\n", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "toggle looping"));
    assertEquals("looping on\nlooping off\nlooping on\n", output.toString());
  }

  @Test
  public void testSlowDownTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "slow down"));
    assertEquals("slow down\n", output.toString());
  }

  @Test
  public void testSpeedUpTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "speed up"));
    assertEquals("speed up\n", output.toString());
  }

  @Test
  public void testAddShapeTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "add shape"));
    assertEquals("", output.toString());
  }

  @Test
  public void testSaveFileTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "save"));
    assertEquals("save file\n", output.toString());
  }

  @Test
  public void testLoadFileTrigger() {
    assertEquals("", output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object", 0, "load"));
    assertEquals("load file\n", output.toString());
  }

  @Test
  public void testAddLayerTrigger() {
    assertEquals("",output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object",0,"add layer"));
    assertEquals("add layer\n", output.toString());
  }

  @Test
  public void testRemoveLayerTrigger() {
    assertEquals("",output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object",0,"remove layer"));
    assertEquals("", output.toString());
  }

  @Test
  public void testMoveLayerTrigger() {
    assertEquals("",output.toString());
    actionListener.actionPerformed(new ActionEvent("dummy object",0,"layer forward"));
    assertEquals("", output.toString());
  }

  @Test
  public void testAddLayer() {
    assertEquals(new ArrayList<ArrayList<IReadableShape>>(), m.getShapeLayers());
    editorListener.addLayer();
    assertEquals(1, m.getShapeLayers().size());
    editorListener.addLayer();
    assertEquals(2, m.getShapeLayers().size());
  }

  @Test
  public void testRemoveLayer() {
    assertEquals(new ArrayList<ArrayList<IReadableShape>>(), m.getShapeLayers());
    editorListener.addLayer();
    assertEquals(1, m.getShapeLayers().size());
    editorListener.addLayer();
    assertEquals(2, m.getShapeLayers().size());
    editorListener.removeLayer(1);
    assertEquals(1, m.getShapeLayers().size());
    editorListener.removeLayer(0);
    assertEquals(new ArrayList<ArrayList<IReadableShape>>(), m.getShapeLayers());
  }

  @Test
  public void tryToRemoveLayer() {
    assertEquals(new ArrayList<ArrayList<IReadableShape>>(), m.getShapeLayers());
    try {
      editorListener.removeLayer(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no layer at the given index.", e.getMessage());
    }
    editorListener.addLayer();
    assertEquals(1, m.getShapeLayers().size());
    try {
      editorListener.removeLayer(6);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no layer at the given index.", e.getMessage());
    }

  }

  @Test
  public void testLayers() {
    //add Layers
    assertEquals("[]", m.getShapeLayers().toString());
    editorListener.addLayer();

    assertEquals("[[]]", m.getShapeLayers().toString());
    editorListener.addLayer();

    assertEquals("[[], []]", m.getShapeLayers().toString());
    editorListener.addLayer();

    assertEquals("[[], [], []]", m.getShapeLayers().toString());



    //add Shapes
    editorListener.addShape("R", ShapeType.RECTANGLE, 2);
    assertEquals("[[], [], [R]]", m.getShapeLayers().toString());

    editorListener.addShape("E", ShapeType.ELLIPSE, 0);
    assertEquals("[[E], [], [R]]", m.getShapeLayers().toString());

    editorListener.addShape("C", ShapeType.ELLIPSE, 1);
    assertEquals("[[E], [C], [R]]", m.getShapeLayers().toString());



    //move Layers
    editorListener.moveLayerBack(2);
    assertEquals("[[E], [R], [C]]", m.getShapeLayers().toString());

    editorListener.moveLayerBack(1);
    assertEquals("[[R], [E], [C]]", m.getShapeLayers().toString());

    editorListener.moveLayerBack(0);
    assertEquals("[[R], [E], [C]]", m.getShapeLayers().toString());

    editorListener.moveLayerForward(0);
    assertEquals("[[E], [R], [C]]", m.getShapeLayers().toString());

    editorListener.moveLayerForward(1);
    assertEquals("[[E], [C], [R]]", m.getShapeLayers().toString());

    editorListener.moveLayerForward(2);
    assertEquals("[[E], [C], [R]]", m.getShapeLayers().toString());

    //delete Layers
    editorListener.removeLayer(2);
    assertEquals("[[E], [C]]", m.getShapeLayers().toString());

    editorListener.removeLayer(1);
    assertEquals("[[E]]", m.getShapeLayers().toString());

    editorListener.removeLayer(0);
    assertEquals("[]", m.getShapeLayers().toString());
  }

  @Test
  public void testingSetTime() {
    assertEquals("", output.toString());
    changeListener.stateChanged(new ChangeEvent("nummy"));
    assertEquals("set time\n", output.toString());
  }


}
