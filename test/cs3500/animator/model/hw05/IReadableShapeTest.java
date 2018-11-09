package cs3500.animator.model.hw05;

import org.junit.Before;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the IReadableShape interface.
 */
public class IReadableShapeTest {

  private IReadableShape rect;
  private IReadableShape ellipse;
  private List<IMotion> motions1;
  private List<IMotion> motions2;

  @Before
  public void init() {
    motions1 = new ArrayList<IMotion>();
    motions2 = new ArrayList<IMotion>();
    IState s1 = new State(Color.RED, new Position2D(75, 33), 50, 60, 1);
    IState s2 = new State(Color.RED, new Position2D(100, 100), 50, 60, 50);
    IState s3 = new State(Color.RED, new Position2D(100, 100), 100, 100, 100);
    motions1.add(new Motion(s1, s2));
    motions1.add(new Motion(s2, s3));
    rect = new ReadableShape(ShapeType.RECTANGLE, "R", motions1);
    ellipse = new ReadableShape(ShapeType.ELLIPSE, "E", motions2);

  }

//  String getName();
//  ShapeType getType();
//  List<IMotion> getMotions();
//  IState getCurrentState(int t) throws IllegalArgumentException;

}
