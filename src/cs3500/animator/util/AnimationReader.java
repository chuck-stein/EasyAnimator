package cs3500.animator.util;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A helper to read animation data and construct an animation from it.
 */
public class AnimationReader {

  /**
   * A factory for producing new animations, given a source of shapes and a builder for constructing
   * animations.
   *
   * <p>
   * The input file format consists of two types of lines:
   * <ul>
   * <li>Shape lines: the keyword "shape" followed by two identifiers (i.e.
   * alphabetic strings with no spaces), giving the unique name of the shape, and the type of shape
   * it is.</li>
   * <li>Motion lines: the keyword "motion" followed by an identifier giving the name
   * of the shape to move, and 16 integers giving the initial and final conditions of the motion:
   * eight numbers giving the time, the x and y coordinates, the width and height, and the red,
   * green and blue color values at the start of the motion; followed by eight numbers for the end
   * of the motion.  See {@link AnimationBuilder#addMotion}</li>
   * </ul>
   * </p>
   *
   * @param readable The source of data for the animation
   * @param builder A builder for helping to construct a new animation
   * @param <Doc> The main model interface type describing animations
   */
  public static <Doc> Doc parseFile(Readable readable, AnimationBuilder<Doc> builder) {
    Objects.requireNonNull(readable, "Must have non-null readable source");
    Objects.requireNonNull(builder, "Must provide a non-null AnimationBuilder");
    Scanner s = new Scanner(readable);
    // Split at whitespace, and ignore # comment lines
    s.useDelimiter(Pattern.compile("(\\p{Space}+|#.*)+"));
    while (s.hasNext()) {
      String word = s.next();
      switch (word) {
        case "canvas":
          readCanvas(s, builder);
          break;
        case "shape":
          readShape(s, builder);
          break;
        case "motion":
          readMotion(s, builder);
          break;
        case "rotation-motion":
          readRotationMotion(s,builder);
          break;
        case "shape-at-layer":
          readLayeredShape(s,builder);
          break;
        default:
          throw new IllegalStateException("Unexpected keyword: " + word + s.nextLine());
      }
    }
    return builder.build();
  }

  /**
   * Reads the canvas and sends it to the builder.
   *
   * @param s the scanner with the info
   * @param builder the builder being built
   * @param <Doc> the builder to be returned
   */
  private static <Doc> void readCanvas(Scanner s, AnimationBuilder<Doc> builder) {
    int[] vals = new int[4];
    String[] fieldNames = {"left", "top", "width", "height"};
    for (int i = 0; i < 4; i++) {
      vals[i] = getInt(s, "Canvas", fieldNames[i]);
    }
    builder.setBounds(vals[0], vals[1], vals[2], vals[3]);
  }

  /**
   * Reads the shape and sends it to builder.
   *
   * @param s scanner with info
   * @param builder builder to build
   * @param <Doc> builder.
   */
  private static <Doc> void readShape(Scanner s, AnimationBuilder<Doc> builder) {
    String name;
    String type;
    int layer = 0;
    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a name, but no more input available");
    }
    if (s.hasNext()) {
      type = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a type, but no more input available");
    }

    builder.declareShape(name, type);
  }

  /**
   * Reads the shape and sends it to builder.
   *
   * @param s scanner with info
   * @param builder builder to build
   * @param <Doc> builder.
   */
  private static <Doc> void readLayeredShape(Scanner s, AnimationBuilder<Doc> builder) {
    String name;
    String type;
    int layer = 0;
    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a name, but no more input available");
    }
    if (s.hasNext()) {
      type = s.next();
    } else {
      throw new IllegalStateException("Shape: Expected a type, but no more input available");
    }
    if (s.hasNextInt()) {
      layer = s.nextInt();
    }
    builder.declareShape(name, type, layer);
  }

  /**
   * Reads the motion and sends it to builder.
   *
   * @param s the scanner with info
   * @param builder the builder to build
   * @param <Doc> the builder
   */
  private static <Doc> void readMotion(Scanner s, AnimationBuilder<Doc> builder) {
    String[] fieldNames = new String[]{
        "initial time",
        "initial x-coordinate", "initial y-coordinate",
        "initial width", "initial height",
        "initial red value", "initial green value", "initial blue value",
        "final time",
        "final x-coordinate", "final y-coordinate",
        "final width", "final height",
        "final red value", "final green value", "final blue value",
    };
    int[] vals = new int[16];
    String name;
    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Motion: Expected a shape name, but no more input available");
    }
    for (int i = 0; i < 16; i++) {
      vals[i] = getInt(s, "Motion", fieldNames[i]);
    }
    builder.addMotion(name,
        vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7],
        vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14], vals[15]);
  }

  /**
   * Reads the motion and sends it to builder.
   *
   * @param s the scanner with info
   * @param builder the builder to build
   * @param <Doc> the builder
   */
  private static <Doc> void readRotationMotion(Scanner s, AnimationBuilder<Doc> builder) {
    String[] fieldNames = new String[]{
        "initial time",
        "initial x-coordinate", "initial y-coordinate",
        "initial width", "initial height",
        "initial red value", "initial green value", "initial blue value", "initial rotation value",
        "final time",
        "final x-coordinate", "final y-coordinate",
        "final width", "final height",
        "final red value", "final green value", "final blue value", "final rotation value"
    };
    int[] vals = new int[18];
    String name;
    if (s.hasNext()) {
      name = s.next();
    } else {
      throw new IllegalStateException("Motion: Expected a shape name, but no more input available");
    }
    for (int i = 0; i < 18; i++) {
      vals[i] = getInt(s, "Motion", fieldNames[i]);
    }
    builder.addRotationMotion(name,
        vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7],
        vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14], vals[15], vals[16],
        vals[17]);
  }

  /**
   * Parses an Int from the scanner.
   *
   * @param s the scanner with info
   * @param label the label of the string
   * @param fieldName the field name of the string
   * @return the int.
   */
  private static int getInt(Scanner s, String label, String fieldName) {
    if (s.hasNextInt()) {
      return s.nextInt();
    } else if (s.hasNext()) {
      throw new IllegalStateException(
          String.format("%s: expected integer for %s, got: %s", label, fieldName, s.next()));
    } else {
      throw new IllegalStateException(
          String.format("%s: expected integer for %s, but no more input available",
              label, fieldName));
    }
  }

}
