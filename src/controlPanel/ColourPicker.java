package controlPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Should open new window to choose a colour for the text/background
 */
public class ColourPicker extends JPanel implements ChangeListener, ActionListener{
    private final JFrame frame;

    protected JColorChooser colorChooser;
    public ColourPicker(JFrame frame){
            this.frame = frame;
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);
        JButton selectColour = new JButton("Select");
        selectColour.addActionListener(this::actionRevert);
        add(selectColour);
        add(colorChooser);
    }

// open up separate window for picking a colour
    private void showColourPicker(){
        // Setup
        JPanel colourPane = new JPanel();
        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ColourPicker colourPick = new ColourPicker(frame);

       // frame.getContentPane().removeAll();
//        frame.setContentPane(colourPick);
//        frame.revalidate();
//        frame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source instanceof JButton){

            JButton button = (JButton) source;
            button.setText("CLICKED");
            showColourPicker();
            frame.pack();
            frame.setVisible(true);
        }
        }

    public void actionRevert(ActionEvent actionEvent){
        Color selectedColour = colorChooser.getColor();
        BillboardControlPanel.setMessageColour(String.valueOf(selectedColour.hashCode()));

        // frame.revalidate();
       // frame.setVisible(true);

    }

}
