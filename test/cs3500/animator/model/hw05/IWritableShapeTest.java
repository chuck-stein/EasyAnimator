package cs3500.animator.model.hw05;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IWritableShapeTest {

  private IWritableShape wRect;
  private IWritableShape wEllipse;

  private IMotion m1;
  private IMotion m2;
  private IMotion m3;

  @Before
  public void init() {
    IState s1;
    IState s2;
    IState s3;
    IState s4;
    wRect = new WritableShape(ShapeType.RECTANGLE, "R");
    wEllipse = new WritableShape(ShapeType.ELLIPSE, "E");
    s1 = new State(Color.RED, new Position2D(10, 15), 80, 157, 3);
    s2 = new State(Color.BLUE, new Position2D(10, 15), 80, 157, 18);
    s3 = new State(Color.GREEN, new Position2D(100, 90), 80, 157, 30);
    s4 = new State(Color.GREEN, new Position2D(100, 90), 80, 300, 50);
    m1 = new Motion(s1, s2);
    m2 = new Motion(s2, s3);
    m3 = new Motion(s3, s4);

  }

  // ensure an exception is thrown when trying to create a motion that would end before it starts
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion() {
    wRect.addMotion(3, 40, 50, 10, 10, 0, 255, 100, 2, 70, 55, 10, 10, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive t1
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion2() {
    wEllipse.addMotion(-6, 40, 50, 10, 10, 0, 255, 100, 2, 70, 55, 10, 10, 0, 0, 0);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive t2
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion3() {
    wRect.addMotion(15, 40, 50, 10, 10, 0, 255, 100, -1, 70, 55, 10, 10, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive w1
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion4() {
    wEllipse.addMotion(23, 40, 50, 0, 10, 0, 255, 100, 50, 70, 55, 10, 10, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive w2
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion5() {
    wRect.addMotion(23, 40, 50, 12, 10, 0, 0, 0, 50, 70, 55, -2, 10, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive h1
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion6() {
    wRect.addMotion(23, 40, 50, 65, -8739, 0, 255, 100, 50, 70, 55, 10, 10, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion with a non-positive h2
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion7() {
    wEllipse.addMotion(23, 40, 50, 22, 10, 0, 255, 100, 50, 70, 55, 10, -49, 0, 255, 100);
  }

  // ensure an exception is thrown when trying to create a motion that would overlap with a
  // previous motion
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion8() {
    wRect.addMotion(8, 40, 50, 30, 20, 180, 12, 210, 32, 70, 55, 10, 10, 180, 12, 210);
    wRect.addMotion(26, 70, 55, 10, 10, 180, 12, 210, 40, 300, 150, 10, 10, 180, 12, 210);
  }

  // ensure an exception is thrown when trying to create a motion whose start state would not
  // match the end state of the adjacent previous motion
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion9() {
    wEllipse.addMotion(5, 40, 50, 30, 20, 180, 12, 210, 10, 70, 55, 10, 10, 180, 12, 210);
    wEllipse.addMotion(10, 1, 1, 1, 1, 1, 1, 1, 35, 300, 150, 10, 10, 180, 12, 210);
  }

  // ensure an exception is thrown when trying to create a motion whose end state would not
  // match the start state of the adjacent next motion
  @Test(expected = IllegalArgumentException.class)
  public void testBadAddMotion10() {
    wRect.addMotion(20, 40, 50, 30, 20, 180, 12, 210, 70, 70, 55, 10, 10, 180, 12, 210);
    wRect.addMotion(10, 15, 30, 50, 50, 100, 100, 106, 20, 41, 50, 30, 20, 180, 12, 210);
  }

  // test adding several motions, to ensure the motions list updates as expected
  @Test
  public void testAddMotion() {
    List<IMotion> expectedMotions = new ArrayList<IMotion>();
    assertEquals(expectedMotions, wEllipse.getMotions());
    wEllipse.addMotion(3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    wEllipse.addMotion(30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    wEllipse.addMotion(18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    expectedMotions.add(m1);
    expectedMotions.add(m2);
    expectedMotions.add(m3);
    assertEquals(expectedMotions.toString(), wEllipse.getMotions().toString());
  }

  // ensure an exception is thrown when trying to remove a motion from a shape with no motions
  @Test(expected = IllegalArgumentException.class)
  public void testBadRemoveMotion() {
    wRect.removeMotion(1);
  }

  // ensure an exception is thrown when trying to remove the 0th motion from a shape (because the
  // first motion should be specified with motionNum=1)
  @Test(expected = IllegalArgumentException.class)
  public void testBadRemoveMotion2() {
    wRect.addMotion(1, 500, 340, 50, 60, 100, 250, 130, 32, 300, 493, 50, 60, 100, 250, 130);
    wRect.removeMotion(0);
  }

  // ensure an exception is thrown when trying to remove the 2nd motion from a shape with only
  // one motion
  @Test(expected = IllegalArgumentException.class)
  public void testBadRemoveMotion3() {
    wEllipse.addMotion(1, 500, 340, 50, 60, 100, 250, 130, 32, 300, 493, 50, 60, 100, 250, 130);
    wEllipse.removeMotion(2);
  }

  // ensure an exception is thrown when trying to remove a motion with a negative motionNum
  @Test(expected = IllegalArgumentException.class)
  public void testBadRemoveMotion4() {
    wRect.addMotion(1, 500, 340, 50, 60, 100, 250, 130, 32, 300, 493, 50, 60, 100, 250, 130);
    wRect.removeMotion(-3);
  }

  // test removing several motions, to ensure the motions list updates as expected
  @Test
  public void testRemoveMotion() {
    wEllipse.addMotion(3, 10, 15, 80, 157, 255, 0, 0, 18, 10, 15, 80, 157, 0, 0, 255);
    wEllipse.addMotion(18, 10, 15, 80, 157, 0, 0, 255, 30, 100, 90, 80, 157, 0, 255, 0);
    wEllipse.addMotion(30, 100, 90, 80, 157, 0, 255, 0, 50, 100, 90, 80, 300, 0, 255, 0);
    List<IMotion> expectedMotions = new ArrayList<IMotion>();
    expectedMotions.add(m1);
    expectedMotions.add(m2);
    expectedMotions.add(m3);
    assertEquals(expectedMotions.toString(), wEllipse.getMotions().toString());
    wEllipse.removeMotion(3);
    expectedMotions.remove(2);
    assertEquals(expectedMotions.toString(), wEllipse.getMotions().toString());
    wEllipse.removeMotion(1);
    expectedMotions.remove(0);
    assertEquals(expectedMotions.toString(), wEllipse.getMotions().toString());
    wEllipse.removeMotion(1);
    expectedMotions.remove(0);
    assertEquals(expectedMotions.toString(), wEllipse.getMotions().toString());
  }


}
