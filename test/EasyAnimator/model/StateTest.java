package EasyAnimator.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import org.junit.Test;

public class StateTest {

  Position2D p1 = new Position2D(2.0, 4.0);
  Position2D p2 = new Position2D(3.0, 1.0);
  State s1 = new State(Color.BLACK, p1, 5, 10, 1);
  State s2 = new State(Color.BLUE, p2, 2, 1, 5);

  @Test
  public void getPosition() {
    assertEquals(s1.getPosition().getX(), 2.0, 0.01);
    assertNotEquals(s1.getPosition(), p1);
  }

  @Test
  public void getColor() {
    assertEquals(s1.getColor(), Color.BLACK);
    assertEquals(s2.getColor(), Color.BLUE);
  }

  @Test
  public void getWidth() {
    assertEquals(s1.getWidth(), 10, 0.01);
    assertEquals(s2.getWidth(), 1, 0.01);
  }

  @Test
  public void getTick() {
    assertEquals(s1.getTick(), 1);
    assertEquals(s2.getTick(), 5);
  }

  @Test
  public void getHeight() {
    assertEquals(s1.getHeight(), 5, 0.01);
    assertEquals(s2.getHeight(), 2, 0.01);
  }

  @Test
  public void getState() {
    assertEquals("1 2 4 10 5 0 0 0", s1.getState().toString());
    assertEquals("5 3 1 1 2 0 255 0", s2.getState().toString());
  }
}