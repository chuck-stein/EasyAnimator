import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.Position2D;
import cs3500.animator.model.hw05.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the model of an Easy Animator.
 */
public class EasyAnimatorModelTest {

  IEasyAnimatorModel m;
  IEasyAnimatorModel m2;

  /**
   * Sets up tests.
   */
  @Before
  public void init() {
    m = new EasyAnimatorModel();
    m.addShape(ShapeType.RECTANGLE, "R1", Color.BLUE, new Position2D(340, 155), 10, 17);
    m.addShape(ShapeType.RECTANGLE, "R2", Color.RED, new Position2D(512, 400), 91, 36);
    m.addShape(ShapeType.ELLIPSE, "E1", Color.ORANGE, new Position2D(110, 246), 50, 50);
    m.createState("R1", 40, Color.BLUE, new Position2D(400, 100), 10, 17);
    m.createState("R1", 9, Color.GREEN, new Position2D(400, 100), 10, 17);
    m.createState("R1", 17, Color.BLACK, new Position2D(430, 120), 10, 17);
    m.createState("R2", 26, Color.YELLOW, new Position2D(590, 483), 91, 36);
    m.createState("R2", 31, Color.YELLOW, new Position2D(590, 483), 30, 10);
    m.createState("E1", 50, Color.GREEN, new Position2D(110, 246), 100, 150);

    m2 = new EasyAnimatorModel();
  }

  // Tests getting all motions from a model with multiple shapes.
  @Test
  public void testGetAllMotions() {
    assertEquals(
            "Shape R1 rectangle\n"
                    + "motion R1   1 340 155 10 17 0 0 255    41 400 100 10 17 0 0 255\n"
                    + "motion R1   41 400 100 10 17 0 0 255    50 400 100 10 17 0 255 0\n"
                    + "motion R1   50 400 100 10 17 0 255 0    67 430 120 10 17 0 0 0\n\n"
                    + "Shape R2 rectangle\n"
                    + "motion R2   1 512 400 91 36 255 0 0    27 590 483 91 36 255 255 0\n"
                    + "motion R2   27 590 483 91 36 255 255 0    58 590 483 30 10 255 255 0\n\n"
                    + "Shape E1 ellipse\n"
                    + "motion E1   1 110 246 50 50 255 200 0    51 110 246 100 150 0 255 0",
            m.getAllMotions());
  }

  //Tests getting all motions from a model with no shapes.
  @Test
  public void testGetAllMotions2() {
    assertEquals("", m2.getAllMotions());
  }

  // Tests grabbing motions at different points in time, one shape sometimes has nothing,
  // one time all shapes have nothing, one time all shapes have something
  @Test
  public void testGetCurrentMotions() {
    assertEquals(
            "Shape R1 rectangle\n"
                    + "motion R1   1 340 155 10 17 0 0 255    41 400 100 10 17 0 0 255\n"
                    + "Shape R2 rectangle\n"
                    + "motion R2   1 512 400 91 36 255 0 0    27 590 483 91 36 255 255 0\n"
                    + "Shape E1 ellipse\n"
                    + "motion E1   1 110 246 50 50 255 200 0    51 110 246 100 150 0 255 0",
            m.getCurrentMotions(10)
    );
    assertEquals(
            "Shape R1 rectangle\n"
                    + "motion R1   50 400 100 10 17 0 255 0    67 430 120 10 17 0 0 0\n"
                    + "Shape R2 rectangle\n"
                    + "motion R2   27 590 483 91 36 255 255 0    58 590 483 30 10 255 255 0\n",
            m.getCurrentMotions(56)
    );
    assertEquals("\n\n", m.getCurrentMotions(200));
  }

  @Test
  public void testAddShapesAndAddMotions() {
    assertEquals("", m2.getAllMotions());
    m2.addShape(ShapeType.RECTANGLE, "R", 6, Color.BLACK, new Position2D(1, 1), 2, 2);
    assertEquals("", m2.getAllMotions());
    m2.createStatePars("R", "-deltaT 10 -move 1 0");
    assertEquals("Shape R rectangle\n" + "motion R   1 1 1 2 2 0 0 0    11 2 1 2 2 0 0 0",
            m2.getAllMotions());
  }

  @Test
  public void faultyAddShapesEnum() {
    try {
      m2.addShape(null, "R", 6, Color.BLACK, new Position2D(1, 1), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape type cannot be null.", e.getMessage());
    }
  }

  @Test
  public void faultyAddShapesName() {
    try {
      m.addShape(ShapeType.RECTANGLE, "R1", 6, Color.BLACK, new Position2D(1, 1), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape name already exists.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateTime() {
    try {
      m.createState("R1", -11, Color.BLACK, new Position2D(1, 1), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Delta T must be 1 or greater.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateName() {
    try {
      m.createState("R", 1, Color.BLACK, new Position2D(1, 1), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateColor() {
    try {
      m.createState("R1", 1, null, new Position2D(1, 1), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have a null position or color.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStatePos() {
    try {
      m.createState("R1", 1, Color.BLACK, null, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have a null position or color.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateWidth() {
    try {
      m.createState("R1", 1, Color.BLACK, new Position2D(1, 1), 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height, width, or tick cannot be less than 1.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateHeight() {
    try {
      m.createState("R1", 1, Color.BLACK, new Position2D(1, 1), 1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height, width, or tick cannot be less than 1.", e.getMessage());
    }
  }

  @Test
  public void faultyParsStateName() {
    try {
      m.createStatePars("R", "-deltaT 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

  @Test
  public void faultyParsStateNoDelta() {
    try {
      m.createStatePars("R1", "-move 1 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("DeltaT must be set", e.getMessage());
    }
  }

  @Test
  public void faultyParsStateBadCMD() {
    try {
      m.createStatePars("R1", "-deltaT 1 -mov 2 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

  @Test
  public void faultyParsStateBadCMDDigits() {
    try {
      m.createStatePars("R1", "-deltaT 1 -move 2 ");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

}
