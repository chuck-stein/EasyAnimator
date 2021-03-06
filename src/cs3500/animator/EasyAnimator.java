package cs3500.animator;


import cs3500.animator.adapter.ViewAdapter;
import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel.EasyAnimatorModelBuilder;


import cs3500.animator.view.AnimationEditorView;
import cs3500.animator.view.TextEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;
import cs3500.animator.view.VisualEasyAnimatorView;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.StringReader;

import cs3500.animator.controller.IEasyAnimatorController;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


import static cs3500.animator.util.AnimationReader.parseFile;

/**
 * Represents a 2D animation editor program. Reads a text file as input and displays/outputs
 * the data based on view type specified. The view can either be text output describing the
 * animation, an SVG file of the animation, a visual playback of the animation, a GUI for viewing
 * and editing the animation, or an alternate editor GUI view made by other classmates and
 * adapted to fit our model. Can display at different speeds as well.
 */
public final class EasyAnimator {

  /**
   * The method that runs the animation. Parameters excepted are -in filename -view text visual or
   * svg -speed positive int -out filename. Any other parameters will cause an error.
   */
  public static void main(String[] args) {
    EasyAnimatorViewBuilder viewBuilder = new EasyAnimatorViewBuilder();
    Appendable output = System.out;
    Readable input = new StringReader("Dummy");
    int ticksPerSecond = 50;
    boolean hasInFile = false;
    boolean hasView = false;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case ("-in"):
          if (i + 1 >= args.length) {
            errorPopup("-in Must be followed by a valid file name.");
          }
          input = createReadFrom(args[i + 1]);
          i++;
          hasInFile = true;
          break;
        case ("-view"):
          if (i + 1 >= args.length) {
            errorPopup("-view Must be followed by a view type (edit, provider, text, visual).");
          }
          viewBuilder = decideView(viewBuilder, args[i + 1]);
          i++;
          hasView = true;

          break;
        case ("-out"):
          if (i + 1 >= args.length) {
            errorPopup("-out Must be followed by an output file name.");
          }
          output = createWriteTo(args[i + 1]);

          i++;
          break;
        case ("-speed"):
          if (i + 1 >= args.length) {
            errorPopup("-speed Must be followed by an integer for speed.");
          }
          if (Integer.parseInt(args[i + 1]) < 1) {
            errorPopup("Ticks per second must be an integer greater than 0");
          }
          ticksPerSecond = Integer.parseInt(args[i + 1]);

          i++;
          break;
        default:
          errorPopup("Invalid command specification");

      }
    }
//    if (!(hasInFile && hasView)) {
//      errorPopup("You need to specify an In file and a View.");
//    }

    if (!hasInFile) {
      input = createReadFrom("vivid.txt");
      viewBuilder = decideView(viewBuilder, "edit");
    }

    viewBuilder.setOutput(output);
    viewBuilder.setTicksPerSecond(ticksPerSecond);

    IEasyAnimatorModel m = parseFile(input, new EasyAnimatorModelBuilder());
    viewBuilder.setCanvas(m.getCanvasX(), m.getCanvasY(), m.getCanvasWidth(), m.getCanvasHeight());

    IEasyAnimatorView v = viewBuilder.build();
    IEasyAnimatorController c = new EasyAnimatorController(v, m, ticksPerSecond);
    c.commence();

    finishFile(output);
    System.exit(-1);
  }

  /**
   * Returns the builder with a view decided upon.
   *
   * @param viewBuilder the builder that needs its view type set.
   * @param s the string that tells the builder what view type to set.
   * @return the builder with its view type set.
   */
  private static EasyAnimatorViewBuilder decideView(EasyAnimatorViewBuilder viewBuilder, String s) {
    try {
      return viewBuilder.setViewType(s);
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
      throw new IllegalStateException("Something went wrong and the program did not quit.");
    }
  }

  /**
   * Creates a file to write to named by a message.
   *
   * @param filename what to name the file.
   * @return a file that can be written.
   */
  private static FileWriter createWriteTo(String filename) {
    try {
      return new FileWriter("animations/svg/" + filename);
    } catch (IOException e) {
      errorPopup("Could not create or write to file " + filename
              + " in directory /animations/svg.");
      throw new IllegalStateException("Failed to write to file.");
    }
  }

  /**
   * Finds and returns a file named by a message.
   *
   * @param filename the name of the file to be read.
   * @return the file to be read.
   */
  private static FileReader createReadFrom(String filename) {
    try {
      return new FileReader("animations/text/" + filename);
    } catch (IOException e) {
      errorPopup("Could not read from file " + filename
              + ". Ensure file is in /animations/text directory.");
      throw new IllegalStateException("Failed to read from file.");
    }
  }

  /**
   * Closes the text file or output being written.
   *
   * @param output the file or output to be closed.
   */
  private static void finishFile(Appendable output) {
    try {
      ((Flushable) output).flush();
    } catch (IOException e) {
      errorPopup("File cannot be closed, File not properly created.");
    }
  }

  /**
   * Displays an error window when something goes wrong, with the message of why it went wrong.
   *
   * @param msg the message that the error displays.
   */
  private static void errorPopup(String msg) {
    JOptionPane.showMessageDialog(new JPanel(), msg, "ERROR", JOptionPane.ERROR_MESSAGE);
    System.exit(-1);
  }

  /**
   * A builder for constructing a view with customizable specifications such as canvas settings,
   * speed, output, and view type (e.g. visual, svg, text, editor).
   */
  private static final class EasyAnimatorViewBuilder {

    private int canvasX;
    private int canvasY;
    private int canvasWidth;
    private int canvasHeight;
    private int ticksPerSecond;
    private String type;
    private Appendable output;

    /**
     * Constructs a viewBuilder to start building a view. Also sets some default values.
     */
    EasyAnimatorViewBuilder() {
      this.canvasX = 0;
      this.canvasY = 0;
      this.canvasWidth = 1;
      this.canvasHeight = 1;
      this.ticksPerSecond = 1;
      this.type = "text";
      this.output = new StringBuilder();
    }

    /**
     * Builds the view of the desired type with the given specifications.
     *
     * @return the view that will show the animation.
     * @throws IllegalArgumentException if the specified view is not a supported type
     */
    IEasyAnimatorView build() throws IllegalArgumentException {
      switch (type) {
        case ("text"):
          return new TextEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight,
              output);
        case ("visual"):
          return new VisualEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight,
              ticksPerSecond);
        case ("svg"):
          return new SvgEasyAnimatorView(canvasX, canvasY, canvasWidth, canvasHeight,
              output);
        case ("edit"):
          return new AnimationEditorView(canvasX, canvasY, canvasWidth, canvasHeight);
        case ("provider"):
          return new ViewAdapter(canvasX, canvasY, canvasWidth, canvasHeight);
        default:
          throw new IllegalArgumentException("Unsupported View, please use a supported version.");
      }
    }


    /**
     * Stores what type of view this will be. The type is processed with the view is built.
     *
     * @param type what type of view this will make.
     * @return the builder with its view type specified.
     */
    EasyAnimatorViewBuilder setViewType(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the ticks per second for this view to have.
     *
     * @param ticksPerSecond the ticks per second specifier for the view.
     * @return the builder.
     */
    EasyAnimatorViewBuilder setTicksPerSecond(int ticksPerSecond) {
      this.ticksPerSecond = ticksPerSecond;
      return this;
    }

    /**
     * Sets the canvas for the view.
     *
     * @param canvasX the amount to transform the origin in the x direction.
     * @param canvasY the amount to transform the origin in the y direction.
     * @param canvasWidth the width of the canvas.
     * @param canvasHeight the height of the canvas.
     * @return the builder.
     */
    EasyAnimatorViewBuilder setCanvas(int canvasX, int canvasY, int canvasWidth,
        int canvasHeight) {
      this.canvasX = canvasX;
      this.canvasY = canvasY;
      this.canvasWidth = canvasWidth;
      this.canvasHeight = canvasHeight;

      return this;
    }

    /**
     * Sets what the view should output to.
     *
     * @param output what the view will output to.
     * @return the builder.
     */
    EasyAnimatorViewBuilder setOutput(Appendable output) {
      this.output = output;
      return this;
    }

  }

}
