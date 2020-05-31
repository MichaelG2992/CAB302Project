package controlPanel;

import com.sun.tools.javac.Main;
import org.xml.sax.SAXException;
import server.Billboard;
import server.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static controlPanel.BillboardControlPanel.sendXML;

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
    private JButton exportLocallyButton;
    private JTextField messageText;
    private JList scheduleList;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner durationSpinner;
    private JPanel createSchedulePanel;
    private JComboBox dayOfWeekCombo;
    private JLabel dayOfWeekLabel;
    private JButton createPermissionsButton;

    private JLabel billboardName;
    private JLabel billboardNameLabel;
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
    private JLabel passwordLabel;
    private JPanel mainMenuPanel;
    private JLabel welcomeLabel;
    private JLabel createBillboardLabel;
    private JLabel scheduleBillboardLabel;
    private JLabel editAllBillboardsLabel;
    private JLabel editBillboardsLabel;
    private JLabel editUsersLabel;
    private JLabel permissions;
    private JLabel createPermissionLabel;
    private JPanel permissionsEditInfo;
    private JLabel editUserLabel;
    private JButton confirmChangesToPermissionsButton;
    private JButton cancelPermissionsButton;
    private JButton cancelPermissionButton;
    private JButton deleteBillboardButton;
    private JButton logOutButton;
    private JCheckBox editUsersCheckBox;
    private JCheckBox createBillboardsCheckBox;
    private JButton editScheduleButton;
    private JButton createScheduleButton;
    private JButton sendScheduleToDatabaseButton;
    private JCheckBox scheduleBillboardsCheckBox;
    private JCheckBox editAllBillboardsCheckBox;
    private JButton createButton;
    private JButton previewBillboardButton;
    private CardLayout layout;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;
    private JFrame frame;
    private User currentUser, selectedUser;
    private DefaultListModel  userList = new DefaultListModel();
    Billboard currentBillboard;

    private String INCORRECT_PERMISSIONS_TEXT = "You do not have permission to access this.";

    public MainMenuGUI(String title, JFrame frame) throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super(title);
        this.frame = frame;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setContentPane(mainPanel);
        setSize(400,200);
        this.setLocationRelativeTo(null);

        // Set cards
        layout = (CardLayout) (cardLayout.getLayout());
        // Set up buttons
        setupButtons();

        // Set default layout
        // On start up, new session
        layout.show(cardLayout, "LoginScreen");


        //Hide Main Menu Buttons
        createNewBillboardButton.setVisible(false);
        editBillboardsButton.setVisible(false);
        scheduleBillboardsButton.setVisible(false);
        editUsersButton.setVisible(false);
        editBillboardsButton.setVisible(false);
        editButton.setVisible(false);
        createButton.setVisible(false);

        ;

        // this.setContentPane(cardLayout);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Sets current user to the given user. This should be the person that logs in.
     * @param user
     */
    public void setCurrentUser(User user){
        currentUser = user;
        // For testing. Get from database in final or create super user if database is empty
        userList.addElement(currentUser);

        // Set up Edit list as the content depends on user permissions
        setupEditList();
        setupUserList();

    }

    /**
     * Adds Action Listeners to their corresponding buttons.
     */
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
        confirmChangesToPermissionsButton.addActionListener(this::confirmPermissionsActionPerformed);
        createButton.addActionListener(this::createUserActionPerformed);
        previewBillboardButton.addActionListener(this::previewActionPerformed);

    }

    /**
     * Creates the Billboards list.
     */
    public void setupEditList(){
//         For testing
        // Will get from server in final
        Billboard billboard1 = new Billboard("Testing1", "#287f8e",
                "Testing1", "#287f8e", "Testing1", "Testing1",
                "#287f8e", "SuperUser");
        Billboard billboard2 = new Billboard("Testing2", "#287f8e",
                "Testing2", "#287f8e", "Testing2", "Testing2",
                "#287f8e", "User2");
        Billboard billboard3 = new Billboard("Testing3", "#287f8e",
                "Testing3", "#287f8e", "Testing3", "Testing3",
                "#287f8e", "User1");
        Billboard[] billboardList = {billboard1, billboard2, billboard3};
        editBillboardList = new JList(billboardList);

        editBillboardList.setSelectedIndex(0);
        //JButton editButton = new JButton("Edit");




        editBillboardList.setPreferredSize(new Dimension(200, 300));
        JScrollPane list = new JScrollPane(editBillboardList);
        listPanel.add(list);
        this.pack();

    }

    /**
     * Sets up the Users List and adds it to a scroll panel.
     */
    public void setupUserList(){
        // For testing
        // Will get from server in final
        // Delete from server in final
        if(currentUser.getEditUsers()){
            User user1 = new User("User1", true,
                    true, true, true);
            User user2 = new User("User2", true, true,
                    false, false);
            User user3 = new User("User3", false, false,
                    true, false);

            userList.addElement(user1);
            userList.addElement(user2);
            userList.addElement(user3);
        }
        else{

        }

        editUserList = new JList(userList);

        editUserList.setSelectedIndex(0);


        //editUserList.setPreferredSize(new Dimension(200, 300));
        JScrollPane userPane = new JScrollPane(editUserList);
        userListPanel.add(userPane);

    }

    /**
     * Removes all Action Listeners from the button.
     * @param button
     */
    public void removeActionListeners(JButton button){
        ActionListener[] listeners = button.getActionListeners();
        for(ActionListener i: listeners ){
            button.removeActionListener(i);
        }
    }

    /**
     * Updates the Create New page with current billboard information.
     */
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

    /**
     * Updates the User Info pages with current selected user information.
     */
    public void refreshUser(){
        nameLabel.setText(selectedUser.getUserName());
        // levelLabel.setText(selectedUser.getPermissions());
        // Get password from Server if permission allows
        if(selectedUser.getCreateBillboards()){
            createBillboardLabel.setVisible(true);
        }
        else{
            createBillboardLabel.setVisible(false);

        }
        if(selectedUser.getScheduleBillboards()){
            scheduleBillboardLabel.setVisible(true);
        }
        else{
            scheduleBillboardLabel.setVisible(false);

        }
        if(selectedUser.getEditAllBillboards()){
            editAllBillboardsLabel.setVisible(true);
        }
        else{
            editAllBillboardsLabel.setVisible(false);

        }if(selectedUser.getEditUsers()){
            editUsersLabel.setVisible(true);
        }
        else{
            editUsersLabel.setVisible(false);

        }
        passwordLabel.setText("");
    }

    /**
     * Previews the current billboard.
     * Code taken from Billboard Viewer
     */
    public void previewBillboard(){
        //Assign Billboard Values
        currentBillboard.setName(billboardNameField.getText());
        currentBillboard.setMessageText(messageText.getText());
        currentBillboard.setInfoText(infoText.getText());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        Dimension preferredSize = new Dimension(screenWidth, (screenHeight / 3 ));

        JFrame frame = new JFrame("Billboard Viewer");
        JPanel panel = new JPanel();

        //message customization
        JLabel messageLabel = null;

        if (currentBillboard.getMessageText() != null) {
            messageLabel = new JLabel(currentBillboard.getMessageText(), SwingConstants.CENTER);
            messageLabel.setMaximumSize(screenSize);
            messageLabel.setPreferredSize(preferredSize);
            if(currentBillboard.getMessageColour() != null){
                messageLabel.setForeground(Color.decode(currentBillboard.getMessageColour()));
            }
            Font messageFont = new Font("Courier", Font.PLAIN,40);
            messageLabel.setFont(messageFont);
        }

        //image customization
        JLabel imageLabel = null;
        URL url = null;
        if(currentBillboard.getPictureUrl() != null && currentBillboard.getPictureUrl() != ""){
            try {
                url = new URL(currentBillboard.getPictureUrl());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedImage image = null;
            try {
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            if(imageHeight > screenHeight){
                imageHeight = screenHeight;
            }
            if(imageWidth > screenWidth){
                imageWidth = screenWidth;
            }
            Image sizedImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
            imageLabel = new JLabel(new ImageIcon(sizedImage), SwingConstants.CENTER);
            imageLabel.setMaximumSize(screenSize);
            imageLabel.setPreferredSize(preferredSize);
        }

        //information customization
        JLabel infoLabel = null;
        if(currentBillboard.getInfoText() != null) {
            infoLabel = new JLabel(currentBillboard.getInfoText(), SwingConstants.CENTER);
            infoLabel.setMaximumSize(screenSize);
            infoLabel.setPreferredSize(preferredSize);
            if(currentBillboard.getInfoColour() != null){
                infoLabel.setForeground(Color.decode(currentBillboard.getInfoColour()));
            }
            Font infoFont = new Font("Courier", Font.PLAIN,20);
            infoLabel.setFont(infoFont);
        }

        //background customization
        if (currentBillboard.getBackgroundColour() != null) {
            panel.setBackground(Color.decode(currentBillboard.getBackgroundColour()));
        }

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(screenWidth, screenHeight);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if(messageLabel != null){
            panel.add(messageLabel);
        }
        if(imageLabel != null){
            panel.add(imageLabel);
        }
        if(infoLabel != null){
            panel.add(infoLabel);
        }
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        KeyAdapter listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        };

        frame.addKeyListener(listener);


    }

    /**
     * Set up schedule
     */
    public void setupSchedule(){

    }

    /**
     * Sets up the Colour Chooser.
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
     * Sets up the File Chooser.
     * @param frame
     */
    public void setFileChooser(JFrame frame){
        fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(frame);
        pictureButton.setText(String.valueOf(fileChooser.getSelectedFile()));
        currentBillboard.setPictureUrl(String.valueOf(fileChooser.getSelectedFile()));
        //fileChooser.showSaveDialog(frame);
    }

    /**
     * Opens a window and exports the current billboard to an XML file in the selected directory.
     * @param frame
     */
    public void setExportWindow(JFrame frame){
        fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(currentBillboard.getName()));
        fileChooser.showSaveDialog(frame);
        currentBillboard.setXmlFilePath(fileChooser.getSelectedFile().getPath());
    }

    /**
     * Sets up the check boxes with corresponding permissions.
     * @param frame
     */
    public void setupEditPermission(JFrame frame){
        layout.show(cardLayout, "EditPermissions");

        // Check for current permissions
        if(currentUser != selectedUser){
            editUsersCheckBox.setSelected(selectedUser.getEditUsers());
            editUsersCheckBox.setVisible(true);
        }
        else{
            editUsersCheckBox.setVisible(false);
        }
        editAllBillboardsCheckBox.setSelected(selectedUser.getEditAllBillboards());
        createBillboardsCheckBox.setSelected(selectedUser.getCreateBillboards());
        scheduleBillboardsCheckBox.setSelected(selectedUser.getScheduleBillboards());
        //selectedUser.setPermissions(input);
        refreshUser();

    }

    /**
     * Sets the permissions with the selected check boxes.
     */
    public void setPermissions(){
        // Set permissions to whatever check boxes are selected
        if(currentUser != selectedUser){
            selectedUser.setEditUsers(editUsersCheckBox.isSelected());
        }
        selectedUser.setEditAllBillboards(editAllBillboardsCheckBox.isSelected());
        selectedUser.setCreateBillboards(createBillboardsCheckBox.isSelected());
        selectedUser.setScheduleBillboards(scheduleBillboardsCheckBox.isSelected());
    }

    /**
     * Imports an XML file and fills the Create New Billboard page with the imported information.
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

    /**
     * Changes the selected users password.
     */
    public void changePassword(){
        String input = (String) JOptionPane.showInputDialog(null, "Enter new password",
                "");
        // Set password on server
        // Should be salted in final
        selectedUser.setPassword(input);
        refreshUser();
    }

    /**
     * Creates a new user with the input username.
     */
    public void createNewUser(){

        String input = (String) JOptionPane.showInputDialog(null, "Enter new username",
                "");
        selectedUser = new User(input);
        changePassword();
        userList.addElement(selectedUser);
        setupEditPermission(frame);

    }
//    -------------------------------------------------------------------------------------------------------------------
    // Action listeners.

    /**
     * Creates a new user if the current user has the Edit Users Permission.
     * @param actionEvent
     */
    public void createUserActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            createNewUser();
            refreshUser();
            // setupUserList();
            frame.revalidate();
            //layout.show(cardLayout, "UserInfo");
            createButton.setVisible(false);
            editButton.setVisible(false);
        }
    }

    /**
     * Cancels setting permissions and brings the user back to the User Info page.
     * @param actionEvent
     */
    public void cancelPermissionsActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            refreshUser();
            layout.show(cardLayout, "UserInfo");
        }
    }

    /**
     * Sets the selected users permissions to the corresponding check boxes.
     * @param actionEvent
     */
    public void confirmPermissionsActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setPermissions();
            refreshUser();
            layout.show(cardLayout, "UserInfo");
        }
    }

    /**
     * Changes the selected users password if the user is the same as the one selected OR if they have the
     * Edit Users Permission.
     * @param actionEvent
     */
    public void changePasswordActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if(selectedUser == currentUser || currentUser.getEditUsers()){

                changePassword();
                // For testing. Should not be visible in final
                passwordLabel.setText(selectedUser.getPassword());
                passwordLabel.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes a user if the user has the Edit Users Permission. Users cannot delete their own account.
     * @param actionEvent
     */
    public void deleteUserActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if(selectedUser != currentUser && currentUser.getEditUsers()){
                // Delete from server as well
                userList.remove(userList.indexOf(selectedUser));
                selectedUser = null;
                layout.show(cardLayout, "EditUsers");
                removeActionListeners(editButton);

                editButton.addActionListener(this::editUserListActionPerformed);

                editButton.setVisible(true);
                createButton.setVisible(true);
                this.pack();
            }
            else{
                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Shows the Edit Permissions page if the user has the Edit Users Permission.
     * @param actionEvent
     */
    public void editPermissionsActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if(currentUser.getEditUsers()){
                setupEditPermission(frame);
            }
            else{
                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Edit selected billboard if the user has the Edit All Permission or they are the creator of the billboard
     * and it is not currently scheduled.
     * @param actionEvent
     */
    public void editBillboardActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            currentBillboard = (Billboard) editBillboardList.getSelectedValue();

            if(currentUser.getCreateBillboards() && currentUser.getUserName() == currentBillboard.getCreator()){
                /// AND if billboard is not scheduled yet
                layout.show(cardLayout, "BillboardCreator");

                refreshBillboard();
                removeActionListeners(editButton);
                editButton.setVisible(false);
                createButton.setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }


        }

    }

    /**
     * Imports a correctly formatted XML file and goes to Create New with all
     * values filled with the contents of the file.
     * @param actionEvent
     */
    public void importActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            try {
                importFile(frame);
                setSize(800,400);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Submits the username and password.
     * @param actionEvent
     */
    public void submitActionPerformed(ActionEvent actionEvent){
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
                if (BillboardControlPanel.logIn(userName,password) ){
                    // Should not be able to access without Create Billboards permission
                    welcomeLabel.setText("Welcome " + userName + "!");
                    layout.show(cardLayout, "MainMenu");

                    setSize(600,300);
                    JOptionPane.showMessageDialog(this,"Logged in successfully. Welcome to Billboard Manager",
                            "Welcome",JOptionPane.PLAIN_MESSAGE);
                    //Show Main Menu Buttons
                    createNewBillboardButton.setVisible(true);
                    editBillboardsButton.setVisible(true);
                    scheduleBillboardsButton.setVisible(true);
                    editUsersButton.setVisible(true);
                    this.setTitle("Billboard Control Panel");
                    this.pack();

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

    /**
     * Shows the Edit Billboard List
     * @param actionEvent
     */
    public void editActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if(currentUser.getCreateBillboards() || currentUser.getEditAllBillboards()){
                layout.show(cardLayout, "EditBillboardsList");
                this.setTitle("Edit Billboard");
                removeActionListeners(editButton);
                editButton.addActionListener(this::editBillboardActionPerformed);
                editButton.setVisible(true);
                createButton.setVisible(false);
                this.pack();

            }
            else{

                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Edit User Button Clicked on Main Menu.
     * @param actionEvent
     */
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
            createButton.setVisible(true);

            this.pack();
        }
    }

    /**
     * Takes the user to the Schedule Editor.
     * @param actionEvent
     */
    public void scheduleActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if(currentUser.getScheduleBillboards()){
                layout.show(cardLayout, "ScheduleEditor");
                editButton.setVisible(true);
                createButton.setVisible(false);
                this.pack();
            }
            else{

                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Opens up a file chooser.
     * @param actionEvent
     */
    public void fileActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setFileChooser(frame);
        }
    }

    /**
     * Export Billboard to Database and to selected directory
     * @param actionEvent
     */
    public void exportActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            //Assign Billboard Values
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
            try {
                //If billboard not sent to database
                if (sendXML(currentBillboard)){
                    //Restart Control Panel to Login Screen
                    restartControlPanel(actionEvent);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Sent Billboard To Database",
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (InstantiationException | ParserConfigurationException | IllegalAccessException |
                    UnsupportedLookAndFeelException | TransformerException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            infoText.setText("");
            billboardNameField.setText("");
            messageText.setText("");
            pictureButton.setText("Click to select Picture");
        }
    }

    /**
     * Opens the Colour Chooser.
     * @param actionEvent
     */
    public void colourActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setColourChooser((JButton) source);
        }
    }

    /**
     * Shows the Create New Billboard page and creates a new billboard to edit.
     * @param actionEvent
     */
    public void createNewActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            if(currentUser.getCreateBillboards()){
                JButton button = (JButton) source;
                layout.show(cardLayout, "BillboardCreator");
                currentBillboard = new Billboard(currentUser.getUserName());
                refreshBillboard();
                editButton.setVisible(false);
                createButton.setVisible(false);
            }
            else{

                JOptionPane.showMessageDialog(this, INCORRECT_PERMISSIONS_TEXT,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Showa edit users list.
     * @param actionEvent
     */
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
            createButton.setVisible(false);

            selectedUser = (User) editUserList.getSelectedValue();
            refreshUser();
            this.pack();

        }

    }
    private void previewActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            previewBillboard();


        }

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }


    //From https://stackoverflow.com/questions/26762324/swing-how-to-close-jpanel-programmatically/34934921
    //Restarts Control Panel and resets to log in
    public void restartControlPanel(ActionEvent actionEvent) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JComponent comp = (JComponent) actionEvent.getSource() ;
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainMenuGUI mainMenu = new MainMenuGUI("Main Menu", frame);

    }
}