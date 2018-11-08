package cs3500.animator.model.hw05;

import static org.junit.Assert.*;

import cs3500.animator.model.hw05.IMotion;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class IMotionTest {

  Position2D p1 = new Position2D(2.0, 4.0);
  Position2D p2 = new Position2D(3.0, 1.0);
  IState s1 = new State(Color.BLACK, p1, 5, 10, 1);
  IState s2 = new State(Color.BLUE, p2, 2, 1, 5);
  IState s3 = new State(Color.BLUE, p2, 2, 1, 8);
  IMotion m1 = new Motion(s1,s2);
  IMotion m2 = new Motion(s2, s3);


  @Test
  public void getStartTime() {
    assertEquals(1,m1.getStartTime());
    assertEquals(5,m2.getStartTime());
  }

  @Test
  public void getEndTime() {
    assertEquals(5, m1.getEndTime());
    assertEquals(8, m2.getEndTime());
  }

  @Test
  public void getIntermediateState() {
    assertEquals(s1,m1.getIntermediateState(1));
    assertEquals(s2, m1.getIntermediateState(5));
    assertEquals(new State(Color.BLUE, p2, 2, 1, 7), m2.getIntermediateState(7));
    assertEquals(m1.getIntermediateState(5),m2.getIntermediateState(5));
  }

  @Test
  public void failIntermediateStateWithTimeOutaBounds() {
    try {
      m1.getIntermediateState(10);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given tick does not occur during this motion.", e.getMessage());
    }
  }

  @Test
  public void startEquals() {
    assertTrue(m1.startEquals(s1));
    assertTrue(m2.startEquals(s2));
    assertFalse(m1.startEquals(s2));
  }

  @Test
  public void endEquals() {
    assertTrue(m1.endEquals(s2));
    assertTrue(m2.endEquals(s3));
    assertFalse(m1.endEquals(s1));
  }
}