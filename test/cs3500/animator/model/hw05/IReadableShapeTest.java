package cs3500.animator.model.hw05;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the interface representing readable shapes.
 */
public class IReadableShapeTest {

  private IReadableShape rect;
  private IReadableShape ellipse;
  private IReadableShape emptyEllipse;
  private IState s1;
  private IState s2;
  private IState s3;
  private List<IMotion> motions1;
  private List<IMotion> motions2;

  @Before
  public void init() {
    motions1 = new ArrayList<IMotion>();
    motions2 = new ArrayList<IMotion>();
    s1 = new State(0,Color.RED, new Position2D(60, 60), 50, 60, 1);
    s2 = new State(0,Color.RED, new Position2D(100, 100), 50, 60, 41);
    s3 = new State(0,Color.RED, new Position2D(100, 100), 100, 100, 100);
    IMotion m1 = new Motion(s1, s2);
    IMotion m2 = new Motion(s2, s3);
    motions1.add(m1);
    motions1.add(m2);
    motions2.add(m1);
    rect = new ReadableShape(ShapeType.RECTANGLE, "R", motions1);
    ellipse = new ReadableShape(ShapeType.ELLIPSE, "E", motions2);
    emptyEllipse = new ReadableShape(ShapeType.ELLIPSE, "empty", new ArrayList<IMotion>());
  }

  @Test
  public void testGetName1() {
    assertEquals("E", ellipse.getName());
  }

  @Test
  public void testGetName2() {
    assertEquals("empty", emptyEllipse.getName());
  }

  @Test
  public void testGetName3() {
    assertEquals("R", rect.getName());
  }

  @Test
  public void testGetType1() {
    assertEquals(ShapeType.RECTANGLE, rect.getType());
  }

  @Test
  public void testGetType2() {
    assertEquals(ShapeType.ELLIPSE, ellipse.getType());
  }

  @Test
  public void testGetType3() {
    assertEquals(ShapeType.ELLIPSE, emptyEllipse.getType());
  }

  @Test
  public void testGetMotions1() {
    assertEquals(motions1, rect.getMotions());
  }

  @Test
  public void testGetMotions2() {
    assertEquals(motions2, ellipse.getMotions());
  }

  @Test
  public void testGetMotions3() {
    assertEquals(new ArrayList<IMotion>(), emptyEllipse.getMotions());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadGetCurrentState1() {
    emptyEllipse.getCurrentState(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadGetCurrentState2() {
    ellipse.getCurrentState(75);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadGetCurrentState3() {
    rect.getCurrentState(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadGetCurrentState4() {
    rect.getCurrentState(-5);
  }

  @Test
  public void testGetCurrentState1() {
    assertEquals(s2, rect.getCurrentState(41));
  }

  @Test
  public void testGetCurrentState2() {
    assertEquals(s1, ellipse.getCurrentState(1));
  }

  @Test
  public void testGetCurrentState3() {
    assertEquals(s3, rect.getCurrentState(100));
  }

  @Test
  public void testGetCurrentState4() {
    assertEquals(new State(0,Color.RED, new Position2D(84, 84), 50, 60, 25),
        ellipse.getCurrentState(25));
  }

}
