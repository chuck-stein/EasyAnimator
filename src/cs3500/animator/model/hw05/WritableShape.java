package cs3500.animator.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of {@link IShape} whose list of motions can be added to.
 */
class WritableShape extends AShape {

  /**
   * Constructs a new WritableShape with the given type and name, and an empty list of motions.
   * @param type the type of the shape being created
   * @param name the name of the shape being created
   */
  WritableShape (ShapeType type, String name) {
    super(type, name, new ArrayList<IMotion>());
  }

  /**
   *
   * @param t1
   * @param x1
   * @param y1
   * @param w1
   * @param h1
   * @param r1
   * @param g1
   * @param b1
   * @param t2
   * @param x2
   * @param y2
   * @param w2
   * @param h2
   * @param r2
   * @param g2
   * @param b2
   * @throws IllegalArgumentException
   */
  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    if (overlaps(t1, t2)) {
      throw new IllegalArgumentException("Motions cannot overlap.");
    }
    int i = findNewIndex(t1);
    motions.add(i, new Motion(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2));
  }

  private boolean overlaps(int newStartT, int newEndT) {
    for (IMotion m : motions) {
      if (m.getStartTime() < newEndT && m.getEndTime() > newStartT) {
        return true;
      }
    }
    return false;
  }

  private int findNewIndex(int newStartT) {
    for (int i = 0; i < motions.size(); i++) {
      if (newStartT < motions.get(i).getStartTime()) {
        return i;
      }
    }
    return motions.size();
  }

}

