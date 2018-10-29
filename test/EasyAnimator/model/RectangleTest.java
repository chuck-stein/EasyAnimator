package EasyAnimator.model;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class RectangleTest {

  Rectangle r = new Rectangle("R", Color.BLUE, new Position2D(3, 2), 2, 2);

  @Before
  public void setUp() {
    Rectangle r = new Rectangle("R", Color.BLUE, new Position2D(3, 2), 2, 2);
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
    assertEquals("motion R   1 3 2 2 2 0 255 0    8 3 2 1 1 0 0 0", r.getAllMotions());
    r.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("motion R   1 3 2 2 2 0 255 0    8 3 2 1 1 0 0 0" +
        "\n" +
        "motion R   8 3 2 1 1 0 0 0    10 4 3 1 1 0 0 0", r.getAllMotions());
  }

  @Test
  public void getAllMotionsAndAddState() {
    assertEquals("", r.getAllMotions());
    r.addState(Color.BLACK, new Position2D(3, 2), 1, 1, 7);
    assertEquals("motion R   1 3 2 2 2 0 255 0    8 3 2 1 1 0 0 0", r.getAllMotions());
  }

  @Test
  public void getCurrentMotion() {
    assertEquals("", r.getAllMotions());
    r.addStatePars("-move 0 0 -deltaT 7 -changeSize 0.5 0.5 -changeColor 0 0 0");
    assertEquals("motion R   1 3 2 2 2 0 255 0    8 3 2 1 1 0 0 0", r.getCurrentMotion(2));
    assertEquals("", r.getCurrentMotion(9));
    r.addStatePars("-move 1 1 -deltaT 2");
    assertEquals("motion R   1 3 2 2 2 0 255 0    8 3 2 1 1 0 0 0", r.getCurrentMotion(2));
    assertEquals("motion R   8 3 2 1 1 0 0 0    10 4 3 1 1 0 0 0", r.getCurrentMotion(9));
  }
}