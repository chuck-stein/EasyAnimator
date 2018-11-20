import cs3500.animator.model.hw05.IState;
import cs3500.animator.view.IEasyAnimatorView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.AnimationEditorView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EditorListenerTest {

  private IEasyAnimatorModel m;
  private IEasyAnimatorView v;
  private EditorListener listener;

  @Before
  public void init() {
    m = new EasyAnimatorModel();
    v = new AnimationEditorView(100, 100, 500, 500);
    listener = new EasyAnimatorController(v, m, 60);
  }

  @Test
  public void testAddShape() {
    assertEquals(new ArrayList<IReadableShape>(), m.getShapes());
    listener.addShape("a pretty rectangle", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    listener.addShape("a nice little ellipse", ShapeType.ELLIPSE);
    assertEquals(2, m.getShapes().size());
  }

  @Test // ensure that trying to add a pre-existing shape name has no effect
  public void testAddingDuplicateShape() {
    listener.addShape("shapes cannot appear twice", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    listener.addShape("shapes cannot appear twice", ShapeType.ELLIPSE);
    assertEquals(1, m.getShapes().size());
  }


  @Test
  public void testRemoveShape() {
    listener.addShape("please dont delete me i dont wanna go", ShapeType.ELLIPSE);
    assertEquals(1, m.getShapes().size());
    listener.removeShape("please dont delete me i dont wanna go");
    assertEquals(0, m.getShapes().size());
  }

  @Test // ensure that trying to delete a non-existent shape has no effect
  public void testRemoveNonExistentShape() {
    listener.addShape("a rectangle", ShapeType.RECTANGLE);
    assertEquals(1, m.getShapes().size());
    listener.removeShape("that rectangle i just added");
    assertEquals(1, m.getShapes().size());
  }

  @Test
  public void testInsertKeyframe() {
    listener.addShape("my beautiful four-sided beast", ShapeType.RECTANGLE);
    listener.addShape("a round boi", ShapeType.ELLIPSE);

    assertEquals(0, m.getShapes().get(0).getMotions().size());
    listener.insertKeyframe("my beautiful four-sided beast", 5);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    assertTrue(onlyOneKeyframe());
    listener.insertKeyframe("my beautiful four-sided beast", 30);
    assertFalse(onlyOneKeyframe());
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    listener.insertKeyframe("my beautiful four-sided beast", 1);
    assertEquals(2, m.getShapes().get(0).getMotions().size());

    assertEquals(0, m.getShapes().get(1).getMotions().size());
    listener.insertKeyframe("a round boi", 19);
    assertEquals(1, m.getShapes().get(1).getMotions().size());

    assertEquals(2, m.getShapes().get(0).getMotions().size());
    listener.insertKeyframe("my beautiful four-sided beast", 19);
    assertEquals(3, m.getShapes().get(0).getMotions().size());
  }

  @Test // ensure that trying to add a keyframe to a non-existent shape has no effect
  public void testInsertKeyframeToNonExistentShape() {
    m.addShape(ShapeType.ELLIPSE, "a shape that will not be affected");
    assertTrue(noKeyframesExist());
    listener.insertKeyframe("this shape does not exist", 70);
    assertTrue(noKeyframesExist());
  }

  @Test // ensure that trying to add a keyframe at a tick less than 1 has no effect
  public void testInsertKeyframeAtNonPositiveTimes() {
    m.addShape(ShapeType.RECTANGLE, "a rectangle that will not be affected");
    m.addShape(ShapeType.ELLIPSE, "an ellipse that will not be affected");
    assertTrue(noKeyframesExist());
    listener.insertKeyframe("a rectangle that will not be affected", 0);
    assertTrue(noKeyframesExist());
    listener.insertKeyframe("an ellipse that will not be affected", -4);
    assertTrue(noKeyframesExist());
  }

  @Test // ensure that trying to add a keyframe that already exists has no effect
  public void testInsertPreExistingKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "i only want one keyframe at t=6");
    listener.insertKeyframe("i only want one keyframe at t=6", 6);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    listener.insertKeyframe("i only want one keyframe at t=6", 6);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
  }

  @Test
  public void testRemoveKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "curvy");
    m.insertKeyFrame("curvy", 15);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    listener.removeKeyframe("curvy", 15);
    assertEquals(0, m.getShapes().get(0).getMotions().size());
  }

  @Test // ensure that trying to remove a keyframe with no matching shape name or tick has no effect
  public void testRemoveNonExistentKeyframe() {
    m.addShape(ShapeType.RECTANGLE, "rectosaurus");
    m.insertKeyFrame("rectosaurus", 1);
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    listener.removeKeyframe("rectosaurus", 2); // invalid time
    assertEquals(1, m.getShapes().get(0).getMotions().size());
    listener.removeKeyframe("rectosourus", 2); // invalid name
    assertEquals(1, m.getShapes().get(0).getMotions().size());
  }

  @Test
  public void testEditKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "Lipsy");
    m.insertKeyFrame("Lipsy", 25);
    m.insertKeyFrame("Lipsy", 50);
    assertTrue(bothKeyframesAreIdentical());
    listener.editKeyframe("Lipsy", 50, 123, 456, 789, 10, 11, 12, 13);
    assertFalse(bothKeyframesAreIdentical());
  }

  @Test // ensure that trying to edit a keyframe with no matching shape name or tick has no effect
  public void testEditNonExistentKeyframe() {
    m.addShape(ShapeType.RECTANGLE, "get rect");
    m.insertKeyFrame("get rect", 85);
    m.insertKeyFrame("get rect", 200);
    assertTrue(bothKeyframesAreIdentical());
    // invalid time:
    listener.editKeyframe("get rect", 199, 3, 3, 3, 3, 3, 3, 3);
    assertTrue(bothKeyframesAreIdentical());
    // invalid name:
    listener.editKeyframe("get wrecked", 85, 4, 5, 23, 60, 100, 200, 150);
    assertTrue(bothKeyframesAreIdentical());
  }

  @Test // ensure that trying to edit a keyframe to invalid specifications has no effect
  public void testInvalidEditKeyframe() {
    m.addShape(ShapeType.ELLIPSE, "Ellie");
    m.insertKeyFrame("Ellie", 34);
    m.insertKeyFrame("Ellie", 162);
    assertTrue(bothKeyframesAreIdentical());
    // invalid dimensions:
    listener.editKeyframe("Ellie", 162, 50, 300, -60, 0, 20, 36, 180);
    assertTrue(bothKeyframesAreIdentical());
    // invalid color values:
    listener.editKeyframe("Ellie", 34, 90, 240, 100, 100, 50, -1, 300);
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

}
