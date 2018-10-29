package cs3500.easyAnimator.model.hw05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class RectangleTest {

  Shape r;

  @Before
  public void setUp() {
    r = new Rectangle("R", 1, Color.BLUE, new Position2D(3, 2), 4, 2);
  }

  @Test
  public void faultyShapeConstructionColor() {
    try {
      r = new Rectangle("R", 1, null, new Position2D(3, 2), 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape color cannot be null.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionPos() {
    try {
      r = new Rectangle("R", 1, Color.BLUE, null, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape position must be non-null.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionDimensions() {
    try {
      r = new Rectangle("R", 1, Color.BLUE, new Position2D(3, 2), 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape dimensions and start time must be positive.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionName() {
    try {
      r = new Rectangle(null, 1, Color.BLUE, new Position2D(3, 2), 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Name of shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void getName() {
    assertEquals("R", r.getName());
  }

  @Test
  public void faultyAddStateParsNoDeltaT() {
    try {
      r.addStatePars("-move 2 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("DeltaT must be set", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateParsBadParams() {
    try {
      r.addStatePars("-deltaT 4 -mov 1 2");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateParsBadParamsDigits() {
    try {
      r.addStatePars("-deltaT 4 -move 1 ");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

  @Test
  public void getAllMotionsAndAddStatePars() {
    assertEquals("", r.getAllMotions());
    r.addStatePars("-move 0 0 -deltaT 7 -changeSize 0.5 0.5 -changeColor 0 0 0");
    assertEquals("Shape R rectangle\n" + "motion R   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0",
        r.getAllMotions());
    r.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("Shape R rectangle\n" + "motion R   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0" +
        "\n" +
        "motion R   8 3 2 2 1 0 0 0    10 4 3 2 1 0 0 0", r.getAllMotions());
  }

  @Test
  public void getAllMotionsAndAddState() {
    assertEquals("", r.getAllMotions());
    r.addState(Color.BLACK, new Position2D(3, 2), 1, 1, 7);
    assertEquals("Shape R rectangle\n" + "motion R   1 3 2 4 2 0 0 255    8 3 2 1 1 0 0 0",
        r.getAllMotions());
  }

  @Test
  public void getCurrentMotion() {
    assertEquals("", r.getAllMotions());
    r.addStatePars("-move 0 0 -deltaT 7 -changeSize 0.5 0.5 -changeColor 0 0 0");
    assertEquals("Shape R rectangle\n" + "motion R   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0",
        r.getCurrentMotion(2));
    assertEquals("", r.getCurrentMotion(9));
    r.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("Shape R rectangle\n" + "motion R   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0",
        r.getCurrentMotion(2));
    assertEquals("Shape R rectangle\n" + "motion R   8 3 2 2 1 0 0 0    10 4 3 2 1 0 0 0",
        r.getCurrentMotion(9));
  }
}