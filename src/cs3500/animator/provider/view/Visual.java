import Listener.IActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Will create the visual view for an animation.
 */
public class Visual extends JFrame implements IView {

  protected JLabel display;
  protected JScrollPane scrollPane;
  protected JButton commandButton;
  protected JButton exitButton;
  protected JTextField input;

  Consumer<String> commandCallback;
  String inputString = "";


  /**
   * The visual View for an animation.
   */

  public Visual() {

    super();


  }

  /**
   * Collects user input.
   *
   * @return String with user input.
   */
  public String getInputString() {
    return inputString;
  }

  /**
   * Clears the user input collection.
   */
  public void clearInputString() {

    inputString = "";
  }

  /**
   * sets up action listener.
   *
   * @param listener is a ActionListener.
   */
  public void setListener(IActionListener listener) {
    listener.actionPerformed();
  }

  /**
   * displays Visual view.
   */
  public void display() {

    this.setTitle("Motion");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //display
    this.setLayout(new FlowLayout());
    display = new JLabel("Animations Display");
    this.add(display);

    // scrollplane
    scrollPane = new JScrollPane(display);
    this.add(scrollPane, BorderLayout.CENTER);

    // textfield
    input = new JTextField(10);
    this.add(input);

    //Exit Button
    // should stop animation
    exitButton = new JButton("Exit");
    exitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);

    });
    this.add(exitButton);

    // Command Button
    commandButton = new JButton("Submit");
    commandButton.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept(input.getText());
        input.setText("");
      }
    });

    commandCallback = null;
    this.pack();


  }
}
