package cs3500.animator.view;

public class EasyAnimatorViewBuilder {
 private int canvasX;
  private int canvasY;
  private int canvasWidth;
  private int canvasHeight;
  private int ticksPerSecond;
  private String type;
  private Appendable output;

  public EasyAnimatorViewBuilder() {

  }

  public IEasyAnimatorView build() {
    switch (type) {
      case ("text"):
        return new SimpleTextBasedEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond, output);
      case ("visual"):
        return new SwingBasedEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond);
      case ("svg"):
        return new SvgEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond, output);
      default:
        throw new IllegalArgumentException("Unsupported View, please use a supported version.");

    }
  }

  public EasyAnimatorViewBuilder setViewType(String type) {
    this.type = type;
    return this;
  }

  public EasyAnimatorViewBuilder setTicksPerSecond(int ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
    return this;
  }

  public EasyAnimatorViewBuilder setCanvas(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;

    return this;
  }

  public EasyAnimatorViewBuilder setOutput(Appendable output) {
    this.output = output;
    return this;
  }

}
