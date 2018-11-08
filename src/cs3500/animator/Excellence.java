package cs3500.animator;

import cs3500.animator.view.EasyAnimatorViewBuilder;
import cs3500.animator.view.SwingBasedEasyAnimatorView;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.*;

import cs3500.animator.controller.EasyAnimatorSimpleController;
import cs3500.animator.controller.IEasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModelBuilder;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SimpleTextBasedEasyAnimatorView;

import static cs3500.animator.util.AnimationReader.parseFile;


public final class Excellence {

  public static void main(String[] args) {

    EasyAnimatorViewBuilder viewBuilder = new EasyAnimatorViewBuilder();
    EasyAnimatorModelBuilder modelBuilder = new EasyAnimatorModelBuilder();
    IEasyAnimatorModel m;
    IEasyAnimatorView v;
    Appendable output = System.out;
    Readable input = new StringReader("Dummy");
    int tickPerSecond = 50;

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
            errorPopup("-view Must be followed by a view type.");
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
          tickPerSecond = Integer.parseInt(args[i + 1]);


          i++;
          break;
        default:
          errorPopup("Invalid command specification");

      }
    }
    if (!(hasInFile && hasView)) {
      errorPopup("You need to specify an In file and a View.");
    }

    viewBuilder.setOutput(output);
    viewBuilder.setTicksPerSecond(tickPerSecond);
    m = parseFile(input, modelBuilder);
   viewBuilder.setCanvas(m.getCanvasX(),m.getCanvasY(),m.getCanvasWidth(),m.getCanvasHeight());
  v = viewBuilder.build();
    IEasyAnimatorController c = new EasyAnimatorSimpleController(v, m);
    c.go();
    finishFile(output);
  }

  private static EasyAnimatorViewBuilder decideView(EasyAnimatorViewBuilder viewBuilder, String s) {
    try {
      return viewBuilder.setViewType(s);
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
      return null;
    }
  }

  private static FileWriter createWriteTo(String msg) {
    try {
      return new FileWriter(msg);
    } catch (IOException e) {
      errorPopup("Could not write to or create file with this name.");
      return null;
    }
  }

  private static FileReader createReadFrom(String msg) {
    try {
      return new FileReader(msg);
    } catch (IOException e) {
      errorPopup("Could not read from file with this name.");
      return null;
    }
  }

  private static void finishFile(Appendable output) {
    try {
      ((Flushable) output).flush();
    } catch (IOException e) {
      errorPopup("File cannot be closed, File not properly created.");
    }
  }

  private static void errorPopup(String msg) {
    JOptionPane.showMessageDialog(new JPanel(), msg, "WHOOPSY", JOptionPane.ERROR_MESSAGE);
    System.exit(-1);
  }


}
