package cs3500.animator.provider.view;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A JPanel that has all of the functionality for editing keyframes, since it's much cleaner to have
 * everything together for that.
 */
class TableScrollPane extends JPanel {

  /**
   * A constructor that creates a panel with all of the elements given, in a vertical box layout.
   * @param table the {@code JTable} to be used
   * @param button1 the top button
   * @param button2 the bottom button
   */
  TableScrollPane(JTable table, JButton button1, JButton button2) {
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    JScrollPane tablePane = new JScrollPane(table);
    tablePane.setPreferredSize(new Dimension(500, 40));
    this.add(tablePane);
    this.add(button1);
    this.add(button2);
  }

}
