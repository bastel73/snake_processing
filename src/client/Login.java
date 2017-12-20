package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
Hier wird das Login geregelt. Das ist bisher schmutzig programmiert. Lasst Euch was einfallen, wie das besser geht.
 */
public class Login {
    class ButtonController implements ActionListener {
        private boolean status;

        public ButtonController(boolean status) {
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            this.status=true;
            System.out.println(status);
        }

        public boolean returnSendStatus() {
            return status;
        }
    }

    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    boolean status;
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

    public String getLoginID() {

        //lasst euch was einfallen, wie man das verbessern kann
        while(!controller.returnSendStatus()) {
            System.out.println(controller.returnSendStatus());
        }
        return textField.getText();
    }
}
