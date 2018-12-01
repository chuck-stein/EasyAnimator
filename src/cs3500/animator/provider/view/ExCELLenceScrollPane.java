package cs3500.animator.view;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Scroll pane that has default values to easily display shape names/keyframe times.
 */
final class ExCELLenceScrollPane extends JPanel {

  /**
   * A constructor that puts the given JList into a JScrollPane, and then sticks the name on top in
   * a JLabel.
   *
   * @param name the name that will startProgram above the element given
   * @param comp the component to be put into the {@code ExCELLenceScrollPane}
   */
  ExCELLenceScrollPane(String name, JList comp) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JLabel nameLabel = new JLabel(name);

    JScrollPane scroll = new JScrollPane(comp);

    scroll.setSize(new Dimension(100, 200));
    scroll.setWheelScrollingEnabled(true);
    this.setPreferredSize(new Dimension(100, 250));
    this.add(nameLabel);
    this.add(scroll);

  }


  //TODO : LOADING DOES NOT UPDATE MAX TIME? IS IT SET

}
