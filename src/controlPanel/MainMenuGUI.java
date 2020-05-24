package controlPanel;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenuGUI extends JFrame implements ActionListener {
    private JButton createNewBillboardButton;
    private JPanel mainPanel;
    private JButton editBillboardsButton;
    private JButton editUsersButton;
    private JButton scheduleBillboardsButton;
    private JPanel billboardCreator;
    private JPanel editBillboardsPanel;
    private JPanel cardLayout;
    private JButton exportButton;
    private JTextField messageText;
    private JTextField infoText;
    private JButton messageColourButton;
    private JButton informationColourButton;
    private JButton pictureButton;
    private JButton backgroundColourButton;
    private JScrollBar scrollBar1;
    private JList list1;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JTextField enterBillboardNameTextField;
    private CardLayout layout;
    protected JColorChooser colorChooser;
    protected JFileChooser fileChooser;
    private JFrame frame;
    public MainMenuGUI(String title, JFrame frame) throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super(title);
        this.frame = frame;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setContentPane(mainPanel);
        setSize(400,300);
        this.setLocationRelativeTo(null);

        // Set cards
        layout = (CardLayout) (cardLayout.getLayout());

        // Set up buttons
        submitButton.addActionListener(this::submitActionPerformed);
        messageColourButton.addActionListener(this::colourActionPerformed);
        informationColourButton.addActionListener(this::colourActionPerformed);
        backgroundColourButton.addActionListener(this::colourActionPerformed);
        createNewBillboardButton.addActionListener(this::createNewActionPerformed);
        exportButton.addActionListener(this::exportActionPerformed);
        pictureButton.addActionListener(this::fileActionPerformed);
        editBillboardsButton.addActionListener(this::editActionPerformed);

        // Set default layout
        // On start up, new session
         layout.show(cardLayout, "LoginScreen");

         //Hide Main Menu Buttons
         createNewBillboardButton.setVisible(false);
         editBillboardsButton.setVisible(false);
         scheduleBillboardsButton.setVisible(false);
         editUsersButton.setVisible(false);
        ;

        // On start up, same session
        // layout.show(cardLayout, "BillboardCreator");

        // this.setContentPane(cardLayout);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }


    /**
     *
     * @param button
     */
    public void setColourChooser(JButton button){
        colorChooser = new JColorChooser();
        ActionListener okActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColour = colorChooser.getColor();
                button.setForeground(selectedColour);
                // Convert to hex
                StringBuilder hexColour = new StringBuilder("#");

                if(selectedColour.getRed() < 16) hexColour.append("0");
                hexColour.append(Integer.toHexString(selectedColour.getRed()));

                if(selectedColour.getGreen() < 16) hexColour.append("0");
                hexColour.append(Integer.toHexString(selectedColour.getGreen()));

                if(selectedColour.getBlue() < 16) hexColour.append("0");
                hexColour.append(Integer.toHexString(selectedColour.getBlue()));

                String hexCode = hexColour.toString();
                button.setText(hexCode);

                if(button == messageColourButton){
                    BillboardControlPanel.setMessageColour(hexCode);
                }
                else if(button == backgroundColourButton){
                    BillboardControlPanel.setBackgroundColour(hexCode);
                }
                else if(button == informationColourButton){
                    BillboardControlPanel.setInfoColour(hexCode);
                }
            }
        };
        ActionListener cancelActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For testing
                System.out.println("CANCELLED");
            }
        };
        final JDialog dialog = JColorChooser.createDialog(null, "Colour Chooser", true,
                colorChooser, okActionListener, cancelActionListener);
        dialog.setVisible(true);

    }

    /**
     *
     * @param frame
     */
    public void setFileChooser(JFrame frame){
        fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(frame);
        pictureButton.setText(String.valueOf(fileChooser.getSelectedFile()));
        BillboardControlPanel.setPictureUrl(String.valueOf(fileChooser.getSelectedFile()));
        //fileChooser.showSaveDialog(frame);
    }

    //Action listeners
    //Submit Button for Log In
    public void submitActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            //Get username and password inputs
             String  userName = usernameField.getText();
             String password = passwordField.getText();

            //Log in authentication
            //Check for empty text fields
            if (!userName.isEmpty() && !password.isEmpty()){
                // if successful
                if (BillboardControlPanel.logIn("superUser","password") ){
                    layout.show(cardLayout, "BillboardCreator");
                    setSize(500,250);
                    JOptionPane.showMessageDialog(this,"Logged in successfully. Welcome to Billboard Manager",
                            "Welcome",JOptionPane.PLAIN_MESSAGE);
                    //Show Main Menu Buttons
                    createNewBillboardButton.setVisible(true);
                    editBillboardsButton.setVisible(true);
                    scheduleBillboardsButton.setVisible(true);
                    editUsersButton.setVisible(true);

                }
                // if not successful
                else{
                    JOptionPane.showMessageDialog(this,"Incorrect login or password",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            // if text fields empty
            else{
                JOptionPane.showMessageDialog(this,"Please enter a valid username and password to continue",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "EditBillboardsList");

        }
    }
    public void okActionPerformed(ActionEvent actionEvent){

    }
    public void fileActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setFileChooser(frame);
        }
    }
    public void exportActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            BillboardControlPanel.setMessageText(messageText.getText());
            BillboardControlPanel.setInfoText(infoText.getText());
            BillboardControlPanel.setBillboardName(enterBillboardNameTextField.getText());
            try {
                BillboardControlPanel.exportXML();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }

        }
    }
    public void colourActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setColourChooser((JButton) source);
        }
    }
    public void createNewActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "BillboardCreator");

        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
