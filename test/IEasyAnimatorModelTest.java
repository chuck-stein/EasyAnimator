import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;

import cs3500.animator.model.hw05.ShapeType;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the interface representing an Easy Animator model.
 */
public class IEasyAnimatorModelTest {

  IEasyAnimatorModel m1;
  IEasyAnimatorModel m2;

  @Before
  public void setUp() {
    m1 = new EasyAnimatorModel();
    m2 = new EasyAnimatorModel();
  }

  @Test
  public void canvasMethods() {
    m1.setCanvas(1, 2, 3, 4);
    assertEquals(1, m1.getCanvasX());
    assertEquals(2, m1.getCanvasY());
    assertEquals(3, m1.getCanvasWidth());
    assertEquals(4, m1.getCanvasHeight());

    m1.setCanvas(5, 6, 7, 8);
    assertEquals(5, m1.getCanvasX());
    assertEquals(6, m1.getCanvasY());
    assertEquals(7, m1.getCanvasWidth());
    assertEquals(8, m1.getCanvasHeight());
  }

  @Test
  public void testBadCanvasSetting() {
    try {
      m1.setCanvas(2, 2, -2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());

    }

    try {
      m1.setCanvas(2, 2, 2, -2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());
    }
  }


  @Test
  public void addShape() {

    assertEquals(new ArrayList<>(), m1.getShapes());
    assertEquals(new ArrayList<>(), m2.getShapes());
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addShape(ShapeType.ELLIPSE, "E");
    m2.addShape(ShapeType.RECTANGLE, "disk1");
    assertEquals(1, m2.getShapes().size());
    assertEquals(2, m1.getShapes().size());
  }

  @Test
  public void shapeSameNameAdding() {
    m1.addShape(ShapeType.ELLIPSE, "E");
    try {
      m1.addShape(ShapeType.RECTANGLE, "E");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape name already exists.", e.getMessage());
    }
  }

  @Test
  public void addMotion() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0, 10,
        200, 200, 50, 100, 255, 0, 0);
    assertEquals(1, m1.getShapes().get(0).getMotions().size());
    m1.addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0, 11,
        200, 200, 50, 100, 255, 0, 0);
    assertEquals(2, m1.getShapes().get(0).getMotions().size());
  }

  @Test
  public void addMotionToShapeNotThere() {
    try {
      m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
          200, 200, 50, 100, 255, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

  @Test
  public void addMotionThatOverlaps() {
    m1.addShape(ShapeType.RECTANGLE, "R1");
    m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
        200, 200, 50, 100, 255, 0, 0);

    try {
      m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
          200, 200, 50, 100, 255, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Motions cannot overlap.", e.getMessage());
    }
  }

  @Test
  public void getShapes() {
    assertEquals(new ArrayList<>(), m1.getShapes());
    assertEquals(new ArrayList<>(), m2.getShapes());
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addShape(ShapeType.ELLIPSE, "E");
    m2.addShape(ShapeType.RECTANGLE, "disk1");

    assertEquals("R", m1.getShapes().get(0).getName());
    assertEquals("E", m1.getShapes().get(1).getName());
    assertEquals("disk1", m2.getShapes().get(0).getName());
  }

  @Test
  public void getShapesWithGaps() {
    assertEquals(new ArrayList<>(), m1.getShapes());

    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addShape(ShapeType.ELLIPSE, "E");
    m1.addMotion("R", 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    m1.addMotion("R", 7, 3, 4, 5, 6, 7, 8, 2, 10, 4, 5, 6, 7, 8, 9, 10);

    try {
      m1.getShapes();
      fail();
    } catch (IllegalStateException e) {
      assertEquals(
          "There can be no gaps in a Shapes Motions. "
              + "There is a gap between time 3 and 7 for shape: R",
          e.getMessage());
    }
  }

  @Test
  public void removeShape() {
    assertEquals(new ArrayList<>(), m1.getShapes());

    m1.addShape(ShapeType.RECTANGLE, "R");
    assertEquals(1, m1.getShapes().size());
    m1.removeShape("R");
    assertEquals(0, m1.getShapes().size());

  }

  @Test
  public void removeShapeThatIsntThere() {
    try {
      m1.removeShape("R");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no shape with the given name.", e.getMessage());
    }
  }

  @Test
  public void removeMotion() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    assertEquals("2 3 4 5 6 7 8 2    3 4 5 6 7 8 9 10",
        m1.getShapes().get(0).getMotions().get(0).toString());
    m1.removeMotion("R", 1);
    assertEquals(0, m1.getShapes().get(0).getMotions().size());
  }

  @Test
  public void removeMotionFromNoShape() {
    try {
      m1.removeMotion("R", 1);
      fail();

    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

  @Test
  public void removeMotionFromShapeWithNoMotion() {
    try {
      m1.addShape(ShapeType.RECTANGLE, "R");
      m1.removeMotion("R", 1);
      fail();

    } catch (IllegalArgumentException e) {
      assertEquals("Shape does not contain the indicated motion.", e.getMessage());
    }
  }

  @Test
  public void addKeyFrameToMiddle() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 3, 10, 15, 80, 157, 255, 0, 0, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.addMotion("R", 30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    m1.insertKeyFrame("R", 18);
    m1.editKeyFrame("R", 18, 10, 15, 80, 157, 0, 0, 255);

    assertEquals("[3 10 15 80 157 255 0 0    18 10 15 80 157 0 0 255, "
        + "18 10 15 80 157 0 0 255    30 100 90 80 157 0 255 0, 30 100 90 80 157 0 255 0    "
        + "50 100 90 80 300 0 255 0]", m1.getShapes().get(0).getMotions().toString());
  }

  @Test
  public void addKeyFrameToStart() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.addMotion("R", 30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    m1.insertKeyFrame("R", 3);
    m1.editKeyFrame("R", 3, 10, 15, 80, 157, 255, 0, 0);

    assertEquals("[3 10 15 80 157 255 0 0    18 10 15 80 157 0 0 255, "
            + "18 10 15 80 157 0 0 255    30 100 90 80 157 0 255 0, 30 100 90 80 157 0 255 0   "
            + " 50 100 90 80 300 0 255 0]",
        m1.getShapes().get(0).getMotions().toString());
  }

  @Test
  public void addKeyFrameToEnd() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    m1.addMotion("R", 18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.insertKeyFrame("R", 50);
    m1.editKeyFrame("R", 50, 100, 90, 80, 300, 0, 255, 0);

    assertEquals("[3 10 15 80 157 255 0 0    18 10 15 80 157 0 0 255, "
        + "18 10 15 80 157 0 0 255    30 100 90 80 157 0 255 0, 30 100 90 80 157 0 255 0   "
        + " 50 100 90 80 300 0 255 0]", m1.getShapes().get(0).getMotions().toString());
  }

  @Test
  public void addKeyFrameToEmptyThenAnother() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.insertKeyFrame("R", 1);
    assertEquals("[1 0 0 1 1 0 0 0    1 0 0 1 1 0 0 0]",
        m1.getShapes().get(0).getMotions().toString());
    m1.insertKeyFrame("R", 2);
    assertEquals("[1 0 0 1 1 0 0 0    2 0 0 1 1 0 0 0]",
        m1.getShapes().get(0).getMotions().toString());
  }

  @Test
  public void editStillFrame() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.insertKeyFrame("R", 1);
    assertEquals("[1 0 0 1 1 0 0 0    1 0 0 1 1 0 0 0]",
        m1.getShapes().get(0).getMotions().toString());
    m1.editKeyFrame("R", 1, 2, 2, 2, 2, 2, 2, 2);
    assertEquals("[1 2 2 2 2 2 2 2    1 2 2 2 2 2 2 2]",
        m1.getShapes().get(0).getMotions().toString());

  }

  @Test
  public void addRemoveKeyFrameMiddle() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    m1.addMotion("R", 18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.addMotion("R", 30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    m1.removeKeyFrame("R", 30);

    assertEquals("[3 10 15 80 157 255 0 0    18 10 15 80 157 0 0 255, "
            + "18 10 15 80 157 0 0 255    50 100 90 80 300 0 255 0]",
        m1.getShapes().get(0).getMotions().toString());

  }

  @Test
  public void addRemoveKeyFrameStart() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    m1.addMotion("R", 18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.addMotion("R", 30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    m1.removeKeyFrame("R", 3);

    assertEquals("[18 10 15 80 157 0 0 255    30 100 90 80 157 0 255 0, "
            + "30 100 90 80 157 0 255 0    50 100 90 80 300 0 255 0]",
        m1.getShapes().get(0).getMotions().toString());

  }

  @Test
  public void addRemoveKeyFrameEnd() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    m1.addMotion("R", 18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    m1.addMotion("R", 30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    m1.removeKeyFrame("R", 50);

    assertEquals("[3 10 15 80 157 255 0 0   "
            + " 18 10 15 80 157 0 0 255, 18 10 15 80 157 0 0 255    30 100 90 80 157 0 255 0]",
        m1.getShapes().get(0).getMotions().toString());

  }

  @Test
  public void doesNotContainRemove() {
    try {
      m1.addShape(ShapeType.RECTANGLE, "R");
      m1.removeKeyFrame("R", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("This shape does not contain a keyframe at the given time.", e.getMessage());
    }
  }

  @Test
  public void lastTick() {
    assertEquals(0, m1.finalAnimationTime());
  }

  @Test
  public void lastTick1() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.insertKeyFrame("R", 1);

    assertEquals(1, m1.finalAnimationTime());
  }

  @Test
  public void editKeyframeWithAngle() {
    m1.addShape(ShapeType.RECTANGLE, "rect");
    m1.insertKeyFrame("rect", 10);
    m1.editKeyFrame("rect", 10, 10, 15, 80, 157, 0, 0, 255, 0);
    m1.insertKeyFrame("rect", 20);
    assertEquals(0, m1.getShapes().get(0).getCurrentState(20).getAngle(), 0.0001);

    assertEquals("20 10 15 80 157 0 255 0 0",
            m1.getShapes().get(0).getCurrentState(20).getStateWithRotation());

    m1.editKeyFrame("rect", 20, 11, 16, 81, 158, 300, 1, 254, 1);
    assertEquals("20 11 16 81 158 1 254 1 300",
            m1.getShapes().get(0).getCurrentState(20).getStateWithRotation());


    assertEquals(150, m1.getShapes().get(0).getCurrentState(15).getAngle(), 0.0001);
  }

}