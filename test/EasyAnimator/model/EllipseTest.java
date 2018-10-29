package EasyAnimator.model;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class EllipseTest {
  Shape e;

  @Before
  public void setUp() {
     e = new Ellipse("E",1, Color.BLUE, new Position2D(3, 2), 4, 2);
  }

  @Test
  public void faultyShapeConstructionColor() {
    try {
      e = new Ellipse("E", 1,null, new Position2D(3, 2), 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape color cannot be null.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionPos() {
    try {
      e = new Ellipse("E", 1,Color.BLUE, null, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape position must be non-null.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionDimensions() {
    try {
      e = new Ellipse("E", 1,Color.BLUE, new Position2D(3, 2), 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape dimensions and start time must be positive.", e.getMessage());
    }
  }

  @Test
  public void faultyShapeConstructionName() {
    try {
      e = new Ellipse(null, 1,Color.BLUE, new Position2D(3, 2), 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Name of shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void getName() {
    assertEquals("E", e.getName());
  }

  @Test
  public void faultyAddStateParsNoDeltaT() {
    try {
      e.addStatePars("-move 2 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("DeltaT must be set", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateParsBadParams() {
    try {
      e.addStatePars("-deltaT 4 -mov 1 2");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

  @Test
  public void faultyAddStateParsBadParamsDigits() {
    try {
      e.addStatePars("-deltaT 4 -move 1 ");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Specifications must follow the javaDoc guidelines.", e.getMessage());
    }
  }

  @Test
  public void getAllMotionsAndAddStatePars() {
    assertEquals("", e.getAllMotions());
    e.addStatePars("-move 0 0 -deltaT 7 -changeSize 0.5 0.5 -changeColor 0 0 0");
    assertEquals("Shape E ellipse\n" + "motion E   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0", e.getAllMotions());
    e.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("Shape E ellipse\n" + "motion E   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0" +
        "\n" +
        "motion E   8 3 2 2 1 0 0 0    10 4 3 2 1 0 0 0", e.getAllMotions());
  }

  @Test
  public void getAllMotionsAndAddState() {
    assertEquals("", e.getAllMotions());
    e.addState(Color.BLACK, new Position2D(3, 2), 1, 1, 7);
    assertEquals("Shape E ellipse\n" + "motion E   1 3 2 4 2 0 0 255    8 3 2 1 1 0 0 0", e.getAllMotions());
  }

  @Test
  public void getCurrentMotion() {
    assertEquals("", e.getAllMotions());
    e.addStatePars("-move 0 0 -deltaT 7 -changeSize 0.5 0.5 -changeColor 0 0 0");
    assertEquals("Shape E ellipse\n" + "motion E   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0", e.getCurrentMotion(2));
    assertEquals("", e.getCurrentMotion(9));
    e.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("Shape E ellipse\n" + "motion E   1 3 2 4 2 0 0 255    8 3 2 2 1 0 0 0", e.getCurrentMotion(2));
    assertEquals("Shape E ellipse\n" + "motion E   8 3 2 2 1 0 0 0    10 4 3 2 1 0 0 0", e.getCurrentMotion(9));
  }

}