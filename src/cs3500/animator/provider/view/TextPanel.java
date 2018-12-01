package cs3500.animator.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents a TextField and the name/label tht it's given.
 */
final class TextPanel extends JPanel {

  /**
   * A constructor that creates a TextPanel with the given {@code JTextField} and sticks the given
   * name on top as a {@code JLabel}. The given button is placed below the text field.
   *
   * @param name the name to be stuck on top
   * @param text the text field
   * @param button the button to be added
   */
  TextPanel(String name, JTextField text, JButton button) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JLabel nameLabel = new JLabel(name);

    this.add(nameLabel);
    this.add(text);
    this.add(button);

  }

  /**
   * A constructor that creates a TextPanel with the given {@code JTextField} and sticks the given
   * name on top as a {@code JLabel}. The given 2 buttons are placed below the text field.
   *
   * @param name the name to be stuck on top
   * @param text the text field
   * @param buttonOne the first button to be added
   * @param buttonTwo the second button to be added
   */
  TextPanel(String name, JTextField text, JButton buttonOne, JButton buttonTwo) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JLabel nameLabel = new JLabel(name);

    this.add(nameLabel);
    this.add(text);
    this.add(buttonOne);
    this.add(buttonTwo);

  }
}
