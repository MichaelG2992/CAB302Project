package viewer;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Viewer {
    public static boolean counted(long timeStarted){
        long nowMillis = System.currentTimeMillis();
        int timePassed = (int)((nowMillis - timeStarted) / 1000);

        if(timePassed >= 5){
            return true;
        }
        else{
            return false;
        }
    }

    public static String viewChanger(long time, int number){
        if(counted(time) == true){
            if(number >= 16){
                number = number + 1;
            }
            else {
                number = 1;
            }
        }
        return "src/billboards/" + number + ".png";
    }

    public static void main(String args[]){
        long timeStarted = System.currentTimeMillis();
        String filePath = "src/viewer/billboards/1.png";
        int picNum = 1;
        JFrame frame = new JFrame("Billboard Viewer");
        ImageIcon icon = new ImageIcon(filePath);
        JLabel label = new JLabel(icon);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        frame.add(label);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        KeyAdapter listener = new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        };

        frame.addKeyListener(listener);

        /*while(true){
            if(!filePath.equals(viewChanger(timeStarted, picNum))){
                filePath = viewChanger(timeStarted, picNum);
                label.setIcon(new ImageIcon(filePath));
                frame.add(label);
                frame.pack();
                frame.setVisible(true);

            }*/
    }
}
