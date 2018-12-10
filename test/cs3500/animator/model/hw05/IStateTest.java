package cs3500.animator.model.hw05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Test;

/**
 * Tests for the interface representing instantaneous states of shapes.
 */
public class IStateTest {


  Position2D p1 = new Position2D(2.0, 4.0);
  Position2D p2 = new Position2D(3.0, 1.0);
  IState s1 = new State(0,Color.BLACK, p1, 5, 10, 1);
  IState s2 = new State(0,Color.BLUE, p2, 2, 1, 5);


  @Test
  public void faultyStateNullColor() {
    try {
      s1 = new State(0,null, p1, 2, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have a null position or color.", e.getMessage());
    }
  }

  @Test
  public void faultyStateNullPos() {
    try {
      s1 = new State(0,Color.BLACK, null, 2, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have a null position or color.", e.getMessage());
    }
  }

  @Test
  public void faultyStateInvalidWidth() {
    try {
      s1 = new State(0,Color.BLACK, p1, 0, 2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height, width, and tick cannot be less than 1.", e.getMessage());
    }
  }

  @Test
  public void faultyStateInvalidHeight() {
    try {
      s1 = new State(0,Color.BLACK, p1, 2, 0, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Height, width, and tick cannot be less than 1.", e.getMessage());
    }
  }

  @Test
  public void faultyStateInvalidTick() {
    try {
      s1 = new State(0,Color.BLACK, p1, 2, 2, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height, width, and tick cannot be less than 1.", e.getMessage());
    }
  }

  @Test
  public void getPosition() {
    assertEquals(s1.getPositionX(), 2.0, 0.01);
    assertNotEquals(s1.getPositionX(), p1);
  }

  @Test
  public void getColor() {
    assertEquals(s1.getColorB(), 0);
    assertEquals(s2.getColorB(), 255);
  }

  @Test
  public void getWidth() {
    assertEquals(s1.getWidth(), 5, 0.01);
    assertEquals(s2.getWidth(), 2, 0.01);
  }

  @Test
  public void getTick() {
    assertEquals(s1.getTick(), 1);
    assertEquals(s2.getTick(), 5);
  }

  @Test
  public void getHeight() {
    assertEquals(s1.getHeight(), 10, 0.01);
    assertEquals(s2.getHeight(), 1, 0.01);
  }

  @Test
  public void getState() {
    assertEquals("1 2 4 5 10 0 0 0", s1.getState());
    assertEquals("5 3 1 2 1 0 0 255", s2.getState());
  }

}