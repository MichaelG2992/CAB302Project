import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillboardControlPanelUI extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton exportButton;
    private JTextField messageTextIN;
    private JTextField infoTextIN;
    private JLabel messageLabel;
    private JLabel infoLabel;
    private JButton messageColourButton;
    private JButton infoColourButton;

//    // Set up UI
//    public BillboardControlPanelUI() throws ClassNotFoundException,
//            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
//        //super(title);
//
//        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////      this.setContentPane(mainPanel);
////
////       //exportButton.addActionListener(this::actionPerformed);
////        ColourPicker messageCC = new ColourPicker(this);
////        messageColourButton.addActionListener(messageCC);
////        infoColourButton.addActionListener(messageCC);
//
//        //this.pack();
//        //this.setVisible(true);
//    }

    public static void billboardControlPanelUISwitch() throws
            ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame = new BillboardControlPanelUI("Billboard Control Panel");

        //exportButton.addActionListener(this::actionPerformed);
        //ColourPicker messageCC = new ColourPicker(this);
        //messageColourButton.addActionListener(messageCC);
//        //infoColourButton.addActionListener(messageCC);
//        frame.pack();
//        frame.setVisible(true);
    }

    // Export when clicked
    @Override
    public void actionPerformed(ActionEvent export) {
        // for testing. remove later
        System.out.println(export.getActionCommand());
        System.out.println(export.getSource());
        System.out.println(export.getWhen());
        Object source = export.getSource();
        if (source instanceof JButton) {
            //BillboardControlPanel.updateValuesXML(infoTextIN.getText(), messageTextIN.getText());
            JButton button = (JButton) source;
            try {
                BillboardControlPanel.exportXML();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            //button.setText("CLICKED");
        }
    }

    public void createUIComponents() throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // TODO: place custom component creation code here

    }

    public JPanel getPanel() throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        //new BillboardControlPanelUI();
        //createUIComponents();
        return mainPanel;
    }
}
