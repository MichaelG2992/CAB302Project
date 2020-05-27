package controlPanel;

import com.sun.tools.javac.Main;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private JList editBillboardList;
    private JList editUserList;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JTextField billboardNameField;
    private JPanel listPanel;
    private JButton editButton;
    private JButton importFileButton;
    private JPanel schedulePanel;
    private JTable table1;
    private JPanel editUsersPanel;
    private JPanel userListPanel;
    private JPanel userInfoPanel;
    private JButton deleteUserButton;
    private JButton editPermissionsButton;
    private JButton changePasswordButton;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel passwordLabel;
    private CardLayout layout;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;
    private JFrame frame;
    private User currentUser, selectedUser;
    Billboard currentBillboard;

    public MainMenuGUI(String title, JFrame frame) throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super(title);
        this.frame = frame;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setContentPane(mainPanel);


        // Set cards
        layout = (CardLayout) (cardLayout.getLayout());
        // Set up buttons
        setupButtons();

        setupEditList();
        setupUserList();
        // Set default layout
        // On start up, new session
        layout.show(cardLayout, "LoginScreen");
        // On start up, same session
        // layout.show(cardLayout, "BillboardCreator");
        // if necessary
        // otherwise keep contents of previous billboard
        // currentBillboard = new Billboard();

        // if edit
        // currentBillboard = billboardSelected;

        // this.setContentPane(cardLayout);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editButton.setVisible(false);

        this.pack();
        this.setVisible(true);
    }

    public void setupButtons(){
        submitButton.addActionListener(this::submitActionPerformed);
        messageColourButton.addActionListener(this::colourActionPerformed);
        informationColourButton.addActionListener(this::colourActionPerformed);
        backgroundColourButton.addActionListener(this::colourActionPerformed);
        createNewBillboardButton.addActionListener(this::createNewActionPerformed);
        exportButton.addActionListener(this::exportActionPerformed);
        pictureButton.addActionListener(this::fileActionPerformed);
        editBillboardsButton.addActionListener(this::editActionPerformed);
        importFileButton.addActionListener(this::importActionPerformed);
        scheduleBillboardsButton.addActionListener(this::scheduleActionPerformed);
        editUsersButton.addActionListener(this::userActionPerformed);
        editPermissionsButton.addActionListener(this::editPermissionsActionPerformed);
        deleteUserButton.addActionListener(this::deleteUserActionPerformed);
        changePasswordButton.addActionListener(this::changePasswordActionPerformed);

    }

    public void setupEditList(){
        // For testing
        // Will get from server in final
        Billboard billboard1 = new Billboard("Testing1", "#287f8e",
                "Testing1", "#287f8e", "Testing1", "Testing1",
                "#287f8e", "User3");
        Billboard billboard2 = new Billboard("Testing2", "#287f8e",
                "Testing2", "#287f8e", "Testing2", "Testing2",
                "#287f8e", "User2");
        Billboard billboard3 = new Billboard("Testing3", "#287f8e",
                "Testing3", "#287f8e", "Testing3", "Testing3",
                "#287f8e", "User1");
        Billboard[] billboardList = {billboard1, billboard2, billboard3};
        editBillboardList = new JList(billboardList);

        editBillboardList.setSelectedIndex(0);





        editBillboardList.setPreferredSize(new Dimension(200, 300));
        JScrollPane list = new JScrollPane(editBillboardList);
        listPanel.add(list);

    }


    public void setupUserList(){
        // For testing
        // Will get from server in final
        // Delete from server in final
        User user1 = new User("User1", "Edit Schedule");
        User user2 = new User("User2", "Create New");
        User user3 = new User("User3", "Approve Schedule");

        User[] userList = {user1, user2, user3};
        editUserList = new JList(userList);

        editUserList.setSelectedIndex(0);


        editUserList.setPreferredSize(new Dimension(200, 300));
        JScrollPane userPane = new JScrollPane(editUserList);
        userListPanel.add(userPane);

    }

    public void removeActionListeners(JButton button){
        ActionListener[] listeners = button.getActionListeners();
        for(ActionListener i: listeners ){
            button.removeActionListener(i);
        }
    }

    public void refreshBillboard(){

        if(currentBillboard.getInfoText() != null )
            infoText.setText(currentBillboard.getInfoText());
        else infoText.setText("");
        if(currentBillboard.getMessageText() != null)
            messageText.setText(currentBillboard.getMessageText());
        else messageText.setText("");

        if(currentBillboard.getName() != null)
            billboardNameField.setText(currentBillboard.getName());
        else billboardNameField.setText("");


        if(currentBillboard.getPictureUrl()!= null)
           pictureButton.setText(currentBillboard.getPictureUrl());
        else pictureButton.setText("Picture");


        // If null just set to default
        if(currentBillboard.getBackgroundColour() != null && currentBillboard.getBackgroundColour()!= "")
            backgroundColourButton.setForeground(Color.decode(currentBillboard.getBackgroundColour()));
        else backgroundColourButton.setForeground(Color.black);
        //backgroundColourButton.setText(currentBillboard.getBackgroundColour());

        //messageColourButton.setText(currentBillboard.getMessageColour());
        if(currentBillboard.getMessageColour() != null && currentBillboard.getMessageColour()!= "")
            messageColourButton.setForeground(Color.decode(currentBillboard.getMessageColour()));
        else messageColourButton.setForeground(Color.black);

        if(currentBillboard.getInfoColour() != null && currentBillboard.getInfoColour()!= "")
            informationColourButton.setForeground(Color.decode(currentBillboard.getInfoColour()));
        else informationColourButton.setForeground(Color.black);
        //informationColourButton.setText(currentBillboard.getInfoColour());
    }

    public void refreshUser(){
        nameLabel.setText(selectedUser.getUsername());
        levelLabel.setText(selectedUser.getPermissions());
        // Get password from Server
        passwordLabel.setText("");
    }

    public void setupSchedule(){

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
                //button.setText(hexCode);

                if(button == messageColourButton){
                    currentBillboard.setMessageColour(hexCode);
                }
                else if(button == backgroundColourButton){
                    currentBillboard.setBackgroundColour(hexCode);
                }
                else if(button == informationColourButton){
                    currentBillboard.setInfoColour(hexCode);
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
        currentBillboard.setPictureUrl(String.valueOf(fileChooser.getSelectedFile()));
        //fileChooser.showSaveDialog(frame);
    }

    public void setExportWindow(JFrame frame){
        fileChooser = new JFileChooser();


        fileChooser.setSelectedFile(new File(currentBillboard.getName()));
        fileChooser.showSaveDialog(frame);
        currentBillboard.setXmlFilePath(fileChooser.getSelectedFile().getPath());
    }


    public void setupEditPermission(JFrame frame){
        String[] permissionsList = {"Create Billboards", "Edit all Billboards", "Schedule Billboards",
        "Edit Users"};

        String input = (String) JOptionPane.showInputDialog(null, "Set Permissions",
                "Permissions", JOptionPane.QUESTION_MESSAGE, null, permissionsList,
                 permissionsList[0]);
        selectedUser.setPermissions(input);
        refreshUser();

    }

    /**
     *
     * @param frame
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void importFile(JFrame frame) throws IOException, SAXException, ParserConfigurationException {
        fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(frame);
        // if selected
        currentBillboard = Billboard.importXML(String.valueOf(fileChooser.getSelectedFile()));
        refreshBillboard();
    }


    // Action listeners.
    public void changePasswordActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String input = (String) JOptionPane.showInputDialog(null, "Change Password",
                    "");
            // Set password on server
            // user.setPassword(input);
            refreshUser();
        }
    }

    public void deleteUserActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            // Delete from server as well
            selectedUser = null;
            layout.show(cardLayout, "EditUsers");
            // Create new instance of Billboard
            // If new session ???

            removeActionListeners(editButton);

            editButton.addActionListener(this::editUserListActionPerformed);

            editButton.setVisible(true);

        }
    }

    public void editPermissionsActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setupEditPermission(frame);

        }
    }

    public void editBillboardActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "BillboardCreator");
            // Basically the same as import too so put this in a method later.
            currentBillboard = (Billboard) editBillboardList.getSelectedValue();

            refreshBillboard();
            removeActionListeners(editButton);
            editButton.setVisible(false);

        }
}

    public void importActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            try {
                importFile(frame);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void submitActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            // if successful
            // layout.show(cardLayout, "BillboardCreator");
            // if not successful
            JOptionPane.showMessageDialog(this,"Incorrect login or password",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "EditBillboardsList");

            removeActionListeners(editButton);
            editButton.addActionListener(this::editBillboardActionPerformed);
            editButton.setVisible(true);
        }
    }

    public void userActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "EditUsers");
            // Create new instance of Billboard
            // If new session ???

            removeActionListeners(editButton);

            editButton.addActionListener(this::editUserListActionPerformed);

            editButton.setVisible(true);
        }
    }

    public void scheduleActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "ScheduleEditor");
            // Create new instance of Billboard
            // If new session ???

            editButton.setVisible(false);

        }
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
            currentBillboard.setName(billboardNameField.getText());
            currentBillboard.setMessageText(messageText.getText());
            currentBillboard.setInfoText(infoText.getText());
            setExportWindow(frame);
            try {
               currentBillboard.exportXML();
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
            // Create new instance of Billboard
            // If new session ???

            // Put username in as creator
            currentBillboard = new Billboard("Username");
            refreshBillboard();
            editButton.setVisible(false);

        }
    }

    private void editUserListActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "UserInfo");
            // Basically the same as import too so put this in a method later.
//                    currentBillboard = (Billboard) editBillboardList.getSelectedValue();
//                    refreshBillboard();
            // Current user changed and fill out info
            editButton.setVisible(false);

            selectedUser = (User) editUserList.getSelectedValue();
            refreshUser();

        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }


}
