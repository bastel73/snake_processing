package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the server login to initialize a game session.
 */
public class Login {
    class ButtonController implements ActionListener {
        private boolean status;

        public ButtonController(boolean status) {
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            this.status = true;
            System.out.println(status);
            updateLoginObserver();
            frame.setVisible(false);
        }

    }

    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    boolean status;
    private NetworkClient networkClient;
    ButtonController controller;


    public Login() {
        this.status = false;
        this.frame = new JFrame("Login");
        this.label = new JLabel("Name: ");
        this.textField = new JTextField(10);
        this.button = new JButton("Start");
        controller = new ButtonController(status);
        button.addActionListener(controller);

        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.add(textField);
        frame.add(button);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Informs the network client about successful login
     */
    private void updateLoginObserver(){
        this.networkClient.hasLoggedIn(textField.getText());
    }

    /**
     * Adds a client that depends on the successful login
     *
     * @param client network client to be informed
     */
    public void addLoginObserver(NetworkClient client){
        this.networkClient = client;
    }

}
