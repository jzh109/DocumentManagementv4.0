package cs.whut.common;

import cs.whut.frame.LoginFrame;

import java.awt.*;
import java.sql.Timestamp;

/**
 * Created on 16:37 2020/9/29
 */
public class MainGUI {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try{
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"Initial system...");
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"User login.");
                LoginFrame.main(args);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
