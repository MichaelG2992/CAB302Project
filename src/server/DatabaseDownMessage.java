package server;

import javax.swing.*;
import java.awt.*;

public class DatabaseDownMessage extends JFrame {

    private final JFrame frame;
    private JPanel mainPanel;

    //Display warning message when server is unavailable.
    public DatabaseDownMessage(String title, JFrame frame) throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super(title);
        this.frame = frame;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setContentPane(mainPanel);
        UIManager.put("OptionPane.minimumSize",new Dimension(250,100));

        JOptionPane.showMessageDialog(this,"Connection to Database failed. Database down or incorrect network properties used." +
                        " Please exit and try again.",
                "Error",JOptionPane.ERROR_MESSAGE);
        System.exit(1);

    }
}


