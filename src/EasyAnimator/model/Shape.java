package EasyAnimator.model;

import java.util.List;

public abstract class Shape {
  protected final String name;
  protected final List<State> states;

  String getName() {
    return name;
  }
}
