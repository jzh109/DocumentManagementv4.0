package cs.whut.frame;

import cs.whut.common.DataProcessing;
import cs.whut.common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created on 19:18 2020/9/28
 */
public class LoginFrame extends JFrame {
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JTextField nameTextField;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    static JFrame frame;

    public LoginFrame() {
        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginMethod();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        confirmButton.addActionListener(e -> loginMethod());
        cancelButton.addActionListener(e -> {
            frame.dispose();
            System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t\t" + "System exit.");
            System.exit(0);
        });
    }

    private void loginMethod() {
        String name = nameTextField.getText().trim();
        String password = new String(passwordField.getPassword());
        try {
            if (name.trim().equals("")) {
                frame.dispose();
                System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t\t" +
                        "User name is empty.");
                JOptionPane.showMessageDialog(null, "User name cannot be empty.");
                System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t\t" +
                        "Restart login.");
                LoginFrame.main(null);
            } else if (DataProcessing.searchUser(name) == null) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "User doesn't exist!");
                LoginFrame.main(null);
            } else {
                User user = DataProcessing.searchUser(name, password);
                if (user != null) {
                    MainFrame mainFrame = new MainFrame(user);
                    mainFrame.setVisible(true);
                    System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t\t" + user.getRole() + " user [" + user.getName() + "] login successfully.");
                } else {
                    frame.dispose();
                    JOptionPane.showMessageDialog(null, "Username or password is incorrect.");
                    LoginFrame.main(null);
                }
            }
        } catch (SQLException ex) {
            frame.dispose();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
            LoginFrame.main(null);
            ex.printStackTrace();
        }
        frame.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frame = new JFrame("System Login");
                frame.setContentPane(new LoginFrame().loginPanel);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setSize(380, 200);
                UserFrame.setToCenter(frame);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
                //frame.pack();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
