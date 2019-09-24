package cs3500.animator.model.hw05;

import cs3500.animator.util.AnimationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The implementation of the model for an Easy Animator, which contains layers of shapes with
 * motions, and can output readable forms of those shapes. INVARIANT: None of the shapes in the
 * animation have the same name.
 */
public final class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<List<IWritableShape>> shapeLayers;
  private int canvasWidth;
  private int canvasHeight;
  private int canvasX;
  private int canvasY;

  /**
   * Constructs an EasyAnimatorModel with an empty list of shapes and the default canvas settings.
   */
  public EasyAnimatorModel() {
    shapeLayers = new ArrayList<List<IWritableShape>>();
    canvasWidth = 500; // default width
    canvasHeight = 500; // default height
    canvasX = 0; // default X
    canvasY = 0; // default Y
  }

  @Override
  public void setCanvas(int x, int y, int w, int h) throws IllegalArgumentException {
    if (w < 1 || h < 1) {
      throw new IllegalArgumentException("Width and Height must be positive numbers");
    }
    canvasX = x;
    canvasY = y;
    canvasWidth = w;
    canvasHeight = h;
  }

  @Override
  public int getCanvasWidth() {
    return canvasWidth;
  }

  @Override
  public int getCanvasHeight() {
    return canvasHeight;
  }

  @Override
  public int getCanvasX() {
    return this.canvasX;
  }

  @Override
  public int getCanvasY() {
    return canvasY;
  }

  @Override
  public void addShape(ShapeType type, String name) throws IllegalArgumentException {
    addShape(type, name, 0);
  }

  @Override
  public void addShape(ShapeType type, String shapeName, int layer)
          throws IllegalArgumentException {
    if (duplicateShapeName(shapeName)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("Shape type cannot be null.");
    }
    if (layer < 0) {
      throw new IllegalArgumentException("Layer number cannot be negative.");
    }
    while (layer > shapeLayers.size() - 1) {
      addLayer();
    }
    shapeLayers.get(layer).add(new WritableShape(type, shapeName));
  }

  @Override
  public void addLayer() {
    shapeLayers.add(new ArrayList<IWritableShape>());
  }

  @Override
  public void removeLayer(int i) throws IllegalArgumentException {
    if (i < 0 || i >= shapeLayers.size()) {
      throw new IllegalArgumentException("There is no layer at the given index.");
    }
    shapeLayers.remove(i);
  }

  /**
   * Checks to see if this model already has a shape with the given name.
   *
   * @param name the name to check for prior existence
   * @return whether or not there is already a shape with the given name
   */
  private boolean duplicateShapeName(String name) {
    try {
      findShape(name);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public void addMotion(String shapeName, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
                        int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    addMotion(shapeName, t1, x1, y1, w1, h1, r1, g1, b1, 0,
            t2, x2, y2, w2, h2, r2, g2, b2, 0);
  }

  @Override
  public void addMotion(String shapeName, int t1, int x1, int y1, int w1, int h1, int r1,
                        int g1, int b1, int a1, int t2, int x2, int y2, int w2, int h2,
                        int r2, int g2, int b2, int a2) throws IllegalArgumentException {
    findShape(shapeName).addRotationMotion(t1, x1, y1, w1, h1, r1, g1, b1, a1,
            t2, x2, y2, w2, h2, r2, g2, b2, a2);
  }

  @Override
  public void removeMotion(String shapeName, int motionNum) throws IllegalArgumentException {
    findShape(shapeName).removeMotion(motionNum);
  }

  @Override
  public void removeShape(String name) throws IllegalArgumentException {
    for (List<IWritableShape> layer : shapeLayers) {
      for (int s = 0; s < layer.size(); s++) {
        if (layer.get(s).getName().equals(name)) {
          layer.remove(s);
          return;
        }
      }
    }
    throw new IllegalArgumentException("There is no shape with the given name.");
  }

  /**
   * Returns the shape in this model with the given name.
   *
   * @param name the name of the shape being searched for
   * @return the shape in this model with the given name
   * @throws IllegalArgumentException if there is no shape with the given name in this model
   */
  private IWritableShape findShape(String name) throws IllegalArgumentException {
    for (int layer = 0; layer < shapeLayers.size(); layer++) {
      for (IWritableShape s : shapeLayers.get(layer)) {
        if (s.getName().equals(name)) {
          return s;
        }
      }
    }
    throw new IllegalArgumentException("There are no shapes with the given name.");
  }

  @Override
  public List<IReadableShape> getShapes() {
    List<IReadableShape> readableShapes = new ArrayList<IReadableShape>();
    for (List<IWritableShape> layer : shapeLayers) {
      for (IWritableShape s : layer) {
        readableShapes.add(new ReadableShape(s));
      }
    }
    return readableShapes;
  }

  @Override
  public List<List<IReadableShape>> getShapeLayers() {
    List<List<IReadableShape>> readableShapeLayers = new ArrayList<List<IReadableShape>>();
    for (int layer = 0; layer < shapeLayers.size(); layer++) {
      readableShapeLayers.add(new ArrayList<IReadableShape>());
      for (IWritableShape s : shapeLayers.get(layer)) {
        readableShapeLayers.get(layer).add(new ReadableShape(s));
      }
    }
    return readableShapeLayers;
  }

  @Override
  public void moveLayerBack(int i) throws IllegalArgumentException {
    if (i < 0 || i >= shapeLayers.size()) {
      throw new IllegalArgumentException("There is no layer at the given index.");
    }
    if (i > 0) {
      Collections.swap(shapeLayers, i, i - 1);
    }
  }

  @Override
  public void moveLayerForward(int i) {
    if (i < 0 || i >= shapeLayers.size()) {
      throw new IllegalArgumentException("There is no layer at the given index.");
    }
    if (i < shapeLayers.size() - 1) {
      Collections.swap(shapeLayers, i, i + 1);
    }
  }

  @Override
  public void insertKeyFrame(String shapeName, int t) throws IllegalArgumentException {
    findShape(shapeName).insertKeyFrame(t);
  }

  @Override
  public void editKeyFrame(String shapeName, int t, int x, int y, int w, int h, int r, int g, int b)
          throws IllegalArgumentException {
    editKeyFrame(shapeName, t, x, y, w, h, 0, r, g, b);
  }

  @Override
  public void editKeyFrame(String shapeName, int t, int x, int y, int w, int h, int a, int r, int g,
                           int b) throws IllegalArgumentException {
    findShape(shapeName).editKeyFrame(t, x, y, w, h, r, g, b, a);
  }

  @Override
  public void removeKeyFrame(String shapeName, int t) throws IllegalArgumentException {
    findShape(shapeName).removeKeyFrame(t);

  }

  @Override
  public int finalAnimationTime() {
    int lastTick = 0;
    for (int layer = 0; layer < shapeLayers.size(); layer++) {
      for (IWritableShape shape : shapeLayers.get(layer)) {
        int newTick = shape.finalTick();
        if (lastTick < newTick) {
          lastTick = newTick;
        }
      }
    }
    return lastTick;
  }


  /**
   * A builder that will build the model. Is used by an animation reader to create the model and set
   * the shapes and motions.
   */
  public static final class EasyAnimatorModelBuilder implements
          AnimationBuilder<EasyAnimatorModel> {

    EasyAnimatorModel model;

    /**
     * Constructs the builder.
     */
    public EasyAnimatorModelBuilder() {
      model = new EasyAnimatorModel();
    }

    @Override
    public EasyAnimatorModel build() {
      return model;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> setBounds(int x, int y, int width, int height) {
      model.setCanvas(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> declareShape(String name, String type) {
      model.addShape(determineShapeType(type), name);
      return null;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> declareShape(String name, String type, int layer) {
      model.addShape(determineShapeType(type), name, layer);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> addMotion(String name, int t1, int x1, int y1,
                                                         int w1, int h1, int r1, int g1, int b1,
                                                         int t2, int x2, int y2, int w2, int h2,
                                                         int r2, int g2, int b2) {

      model.addMotion(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);

      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> addRotationMotion(String name, int t1, int x1,
                                                                 int y1, int w1, int h1, int r1,
                                                                 int g1, int b1, int a1, int t2,
                                                                 int x2, int y2, int w2, int h2,
                                                                 int r2, int g2, int b2, int a2) {
      model.addMotion(name, t1, x1, y1, w1, h1, r1, g1, b1, a1, t2, x2, y2, w2, h2, r2, g2, b2, a2);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
                                                           int h, int r, int g, int b) {
      model.insertKeyFrame(name, t);
      model.editKeyFrame(name, t, x, y, w, h, 0, r, g, b);
      return this;
    }

    /**
     * Determines the shape type from the given String.
     *
     * @param string what to decide shape type from.
     * @return the shapetype.
     */
    private ShapeType determineShapeType(String string) {
      switch (string) {
        case ("rectangle"):
          return ShapeType.RECTANGLE;
        case ("ellipse"):
          return ShapeType.ELLIPSE;
        default:
          throw new IllegalArgumentException(string + " is not a supported shape.");
      }
    }
  }

}
