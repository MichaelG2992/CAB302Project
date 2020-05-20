package controlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LayoutCards implements ItemListener {
    JPanel cards;

    public LayoutCards(){}

    public void addComponentToPane(Container contentPane) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JPanel billboardCreator = new BillboardControlPanelUI().getPanel();


    }

    public static void createAndShowGUI() throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame("Billboard Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.add(new JPanel());
        LayoutCards layout = new LayoutCards();
        layout.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
    }
}
