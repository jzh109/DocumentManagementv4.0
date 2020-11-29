package cs.whut.frame;

import cs.whut.common.Administrator;
import cs.whut.common.User;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.*;
import javax.swing.border.*;

public class MainFrame extends JFrame {
    private JMenuItem addUserMenuItem;
    private JMenuItem deleteUserMenuItem;
    private JMenuItem updateUserMenuItem;
    private JMenuItem uploadFileMenuItem;
    private JMenuItem downloadFileMenuItem;
    private JMenuItem changeSelfInfoMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem logoffMenuItem;
    private User user;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame(new Administrator("kate","123","administrator"));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame(User user) {
        if(user==null){
            assert false;
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"Cannot find user "+user.getName());
            JOptionPane.showMessageDialog(null, "User is null!");
            System.exit(0);
        }
        this.user = user;
        
        setResizable(false);
        this.SetTitle(user.getRole());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 300, 700, 400);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1165, 33);
        contentPane.add(menuBar);

        JMenu userManagerMenu = new JMenu("User Manage");
        userManagerMenu.setFont(new Font("Consolas", Font.PLAIN, 18));
        menuBar.add(userManagerMenu);

        addUserMenuItem = new JMenuItem("Add");
        addUserMenuItem.addActionListener(e -> {
            try {
                AddUserActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        addUserMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        userManagerMenu.add(addUserMenuItem);

        updateUserMenuItem = new JMenuItem("Update");
        updateUserMenuItem.addActionListener(e -> {
            try {
                UpdateUserActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        updateUserMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        userManagerMenu.add(updateUserMenuItem);

        deleteUserMenuItem = new JMenuItem("Delete");
        deleteUserMenuItem.addActionListener(e -> {
            try {
                DelUserActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        deleteUserMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        userManagerMenu.add(deleteUserMenuItem);

        JMenu fileManageMenu = new JMenu("File Manager");
        fileManageMenu.setFont(new Font("Consolas", Font.PLAIN, 18));
        menuBar.add(fileManageMenu);

        downloadFileMenuItem = new JMenuItem("Download");
        downloadFileMenuItem.addActionListener(e -> {
            try {
                DownloadFileActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        downloadFileMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        fileManageMenu.add(downloadFileMenuItem);

        uploadFileMenuItem = new JMenuItem("Upload");
        uploadFileMenuItem.addActionListener(e -> {
            try {
                UploadFileActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        uploadFileMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        fileManageMenu.add(uploadFileMenuItem);

        JMenu selfInfoMenu = new JMenu("Self information");
        selfInfoMenu.setFont(new Font("Consolas", Font.PLAIN, 18));
        menuBar.add(selfInfoMenu);

        changeSelfInfoMenuItem = new JMenuItem("Change Password");
        changeSelfInfoMenuItem.addActionListener(e -> {
            try {
                ChangeSelfActionPerformed(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        changeSelfInfoMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        selfInfoMenu.add(changeSelfInfoMenuItem);

        JMenu otherMenu = new JMenu("Other");
        otherMenu.setFont(new Font("Consolas", Font.PLAIN, 18));
        menuBar.add(otherMenu);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this::ExitActionPerformed);
        exitMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        otherMenu.add(exitMenuItem);

        logoffMenuItem = new JMenuItem("Log out");
        logoffMenuItem.addActionListener(this::logoutActionPerformed);
        logoffMenuItem.setFont(new Font("Consolas", Font.PLAIN, 16));
        otherMenu.add(logoffMenuItem);

        SetRights(user.getRole());

    }

    private void logoutActionPerformed(ActionEvent e){
        this.dispose();
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+ " log out.");
        LoginFrame.main(null);
    }

    private void AddUserActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+ " adds user.");
        String[] name = new String[3];
        name[1] = user.getName();
        name[2] = "0";
        UserFrame.main(name);
    }

    private void DelUserActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+" deletes user.");
        String[] name = new String[3];
        name[1] = user.getName();
        name[2] = "2";
        UserFrame.main(name);
    }

    private void UpdateUserActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+" update user information.");
        String[] name = new String[3];
        name[1] = user.getName();
        name[2] = "1";
        UserFrame.main(name);
    }

    private void UploadFileActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+ "upload file.");
        String[] name =  new String[3];
        name[1]= user.getName();
        name[2] = "1";
        FileFrame.main(name);
    }

    private void DownloadFileActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+" download file.");
        String[] name = new String[3];
        name[1] = user.getName();
        name[2] = "0";
        FileFrame.main(name);
    }

    private void ChangeSelfActionPerformed(User user) throws SQLException {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+user.getName()+" change self password.");
        String[] name = new String[3];
        name[1] = user.getName();
        SelfFrame.main(name);
    }

    private void SetTitle(String role) {
        if (role.equalsIgnoreCase("administrator")) {
            setTitle("Administrator Pane");
        } else if (role.equalsIgnoreCase("browser")) {
            setTitle("Browser Pane");
        } else if (role.equalsIgnoreCase("operator")) {
            setTitle("Operator Pane");
        }
    }

    private void SetRights(String role) {
        if (role.equalsIgnoreCase("administrator")) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"User is administrator.");
            addUserMenuItem.setEnabled(true);
            deleteUserMenuItem.setEnabled(true);
            updateUserMenuItem.setEnabled(true);
            downloadFileMenuItem.setEnabled(true);
            uploadFileMenuItem.setEnabled(false);
            changeSelfInfoMenuItem.setEnabled(true);
            exitMenuItem.setEnabled(true);
        } else if (role.equalsIgnoreCase("browser")) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"User is browser.");
            addUserMenuItem.setEnabled(false);
            deleteUserMenuItem.setEnabled(false);
            updateUserMenuItem.setEnabled(false);
            downloadFileMenuItem.setEnabled(true);
            uploadFileMenuItem.setEnabled(false);
            changeSelfInfoMenuItem.setEnabled(true);
            exitMenuItem.setEnabled(true);
        } else if (role.equalsIgnoreCase("operator")) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"User is operator.");
            addUserMenuItem.setEnabled(false);
            deleteUserMenuItem.setEnabled(false);
            updateUserMenuItem.setEnabled(false);
            downloadFileMenuItem.setEnabled(true);
            uploadFileMenuItem.setEnabled(true);
            changeSelfInfoMenuItem.setEnabled(true);
            exitMenuItem.setEnabled(true);
        }
    }

    private void ExitActionPerformed(ActionEvent evt) {
        this.dispose();
        user.exitSystem();
        JOptionPane.showMessageDialog(null,"System exit. Thanks for utilizing.");
        LoginFrame.main(null);
    }
}
