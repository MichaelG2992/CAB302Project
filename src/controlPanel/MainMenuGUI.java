package controlPanel;

import com.sun.tools.javac.Main;
import org.xml.sax.SAXException;
import server.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    private JButton getExportLocallyButton;
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
    private JButton deleteBillboardButton;
    private JPanel schedulePanel;
    private JPanel editUsersPanel;
    private JPanel userListPanel;
    private JButton deleteUserButton;
    private JButton editPermissionsButton;
    private JButton changePasswordButton;
    private JButton createScheduleButton;
    private JButton getLogOutButton;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JButton logOutButton;
    private JButton exportLocallyButton;
    private JPanel mainMenuPanel;
    private JLabel welcomeLabel;
    private JLabel createBillboardLabel;
    private JLabel scheduleBillboardsLabel;
    private JLabel editAllBillboardsLabel;
    private JLabel editUsersLabel;
    private JPanel permissionsEditInfo;
    private JButton confirmChangesToPermissionsButton;
    private JButton cancelPermissionsButton;
    private JCheckBox editUsersCheckBox;
    private JCheckBox createBillboardsCheckBox;
    private JCheckBox scheduleBillboardsCheckBox;
    private JCheckBox editAllBillboardsCheckBox;
    private JButton createButton;
    private JButton previewBillboardButton;
    private JPanel createSchedulePanel;
    private JLabel billboardName;
    private JLabel scheduleName;
    private JPanel userInfoPanel;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner durationSpinner;
    private JLabel dayOfWeekLabel;
    private JComboBox dayOfWeekCombo;
    private JLabel billboardNameLabel;
    private JButton sendScheduleToDatabaseButton;
    private JPanel scheduleListPanel;
    private JList scheduleList;
    private JButton editScheduleButton;
    private JButton createPermissionsButton;
    private JButton cancelPermissionButton;
    private JLabel permissions;
    private JLabel createPermissionLabel;
    private JLabel editBillboardsLabel;
    private JLabel scheduleBillboardLabel;
    private JLabel editUserLabel;
    private JTable scheduleTable;
    private JFormattedTextField setStartTime;
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
         logOutButton.setVisible(false);
         deleteBillboardButton.setVisible(false);
         createScheduleButton.setVisible(false);
         sendScheduleToDatabaseButton.setVisible(false);
         editScheduleButton.setVisible(false);
        createButton.setVisible(false);

        ;

        // On start up, same session
        // layout.show(cardLayout, "BillboardCreator");

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
        //setupEditList();
        //setupUserList();

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
        logOutButton.addActionListener(this::logOutPerformed);
        deleteBillboardButton.addActionListener(this::deleteBillboardPerformed);
        exportLocallyButton.addActionListener(this::exportXMLLocallyPerformed);
        createScheduleButton.addActionListener(this::createSchedulePerformed);
        sendScheduleToDatabaseButton.addActionListener(this::sendScheduleToDatabaseButtonPerformed);
        editScheduleButton.addActionListener(this::editSchedulePerformed);
        createButton.addActionListener(this::createUserActionPerformed);
        previewBillboardButton.addActionListener(this::previewActionPerformed);
        createPermissionsButton.addActionListener(this::confirmPermissionsActionPerformed);
        cancelPermissionButton.addActionListener(this::cancelPermissionsActionPerformed);

    }

    public void setupEditList(ArrayList<Billboard> list){
        listPanel.removeAll();

        editBillboardList = new JList(list.toArray());

        editBillboardList.setSelectedIndex(0);
        //JButton editButton = new JButton("Edit");


        editBillboardList.setPreferredSize(new Dimension(200, 300));
        JScrollPane scrollPane = new JScrollPane(editBillboardList);
        listPanel.add(scrollPane);
        this.pack();

    }


    public void setupUserList(ArrayList<User> list){
        //Clear panel of values
        userListPanel.removeAll();

        editUserList = new JList(list.toArray());

        editUserList.setSelectedIndex(0);


        editUserList.setPreferredSize(new Dimension(200, 300));
        JScrollPane userPane = new JScrollPane(editUserList);
        userListPanel.add(userPane);

        this.pack();

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

    public void setupSchedule(ArrayList<ScheduleBillboard> list){

        schedulePanel.removeAll();

        scheduleList = new JList(list.toArray());


        scheduleList.setSelectedIndex(0);


        scheduleList.setPreferredSize(new Dimension(700, 200));
        JScrollPane scrollPane = new JScrollPane(scheduleList);
        schedulePanel.add(scrollPane);
        editScheduleButton.setVisible(true);
        createButton.setVisible(false);
        this.pack();



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
            createPermissionLabel.setVisible(true);
        }
        else{
            createPermissionLabel.setVisible(false);

        }
        if(selectedUser.getScheduleBillboards()){
            scheduleBillboardLabel.setVisible(true);
        }
        else{
            scheduleBillboardLabel.setVisible(false);

        }
        if(selectedUser.getEditAllBillboards()){
            editBillboardsLabel.setVisible(true);
        }
        else{
            editBillboardsLabel.setVisible(false);

        }if(selectedUser.getEditUsers()){
            editUserLabel.setVisible(true);
        }
        else{
            editUserLabel.setVisible(false);

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

        ChangePermissions changePermissions = new ChangePermissions(selectedUser.getUserName(),selectedUser.getCreateBillboards(),
                selectedUser.getEditAllBillboards(),selectedUser.getScheduleBillboards(),selectedUser.getEditUsers());
        try {
            BillboardControlPanel.setPermissions(changePermissions);
        } catch (IOException e) {
            e.printStackTrace();
        }


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

        CreateUser createUser = new CreateUser();
        createUser.setUsername(selectedUser.getUserName());
        createUser.setPassword(selectedUser.getPassword());

        try {
            BillboardControlPanel.createUser(createUser);
        } catch (IOException e) {
            e.printStackTrace();
        }



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
            JPasswordField jPasswordField = new JPasswordField();


            int dialogChoice = JOptionPane.showConfirmDialog(null,jPasswordField,"Change Password",JOptionPane.OK_CANCEL_OPTION);

            if (dialogChoice == JOptionPane.OK_OPTION){
                try {
                    BillboardControlPanel.editPassword(selectedUser.getUserName(),jPasswordField.getText());
                    //Send User Confirmation
                    JOptionPane.showMessageDialog(this,"Changed Password Successfully",
                            "Changed Password",JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            refreshUser();
        }
    }

    public void createSchedulePerformed(ActionEvent actionEvent){

        //Get Selected Billboard Name
        currentBillboard = (Billboard) editBillboardList.getSelectedValue();
        billboardNameLabel.setText(currentBillboard.getName());

        //Set Up Spinner for time input
        SpinnerModel hourModel = new SpinnerNumberModel(00,00,23,1); {
        }
        hourSpinner.setModel(hourModel);

        SpinnerModel minuteModel = new SpinnerNumberModel(00,00,59,1); {
        }
        minuteSpinner.setModel(minuteModel);

        layout.show(cardLayout, "Create Schedule");
        createScheduleButton.setVisible(false);
        sendScheduleToDatabaseButton.setVisible(true);
        createButton.setVisible(false);



    }

    public void sendScheduleToDatabaseButtonPerformed(ActionEvent actionEvent){

        String time = hourSpinner.getValue()+":"+minuteSpinner.getValue();
        ScheduleBillboard scheduleBillboard = new ScheduleBillboard(currentBillboard.getName(),time, (int)durationSpinner.getValue(),(String)dayOfWeekCombo.getSelectedItem(),"MICHAEL");
        try {
            BillboardControlPanel.createSchedule(scheduleBillboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this,"Sent Schedule to Database",
                "Success",JOptionPane.PLAIN_MESSAGE);

    }



    public void deleteUserActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            try {
                BillboardControlPanel.deleteUser(selectedUser.getUserName());
                JOptionPane.showMessageDialog(null,String.format("Deleted User: %s from Database",selectedUser.getUserName()));
                editUsersButton.doClick();
            } catch (IOException e) {
                e.printStackTrace();
            }


            layout.show(cardLayout, "EditUsers");
            removeActionListeners(editButton);
            editButton.addActionListener(this::editUserListActionPerformed);
            editButton.setVisible(true);
            this.pack();

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
            setupEditPermission(frame);

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
            layout.show(cardLayout, "BillboardCreator");
            // Basically the same as import too so put this in a method later.
            currentBillboard = (Billboard) editBillboardList.getSelectedValue();

            refreshBillboard();
            removeActionListeners(editButton);
            editButton.setVisible(false);
            deleteBillboardButton.setVisible(false);
            sendScheduleToDatabaseButton.setVisible(false);
            editScheduleButton.setVisible(false);
            sendScheduleToDatabaseButton.setVisible(false);
            createButton.setVisible(false);
            this.pack();

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
                    layout.show(cardLayout, "MainMenu");

                    setSize(600,300);
                    JOptionPane.showMessageDialog(this,"Logged in successfully. Welcome to Billboard Manager",
                            "Welcome",JOptionPane.PLAIN_MESSAGE);
                    //Show Main Menu Buttons
                    createNewBillboardButton.setVisible(true);
                    editBillboardsButton.setVisible(true);
                    scheduleBillboardsButton.setVisible(true);
                    editUsersButton.setVisible(true);
                    logOutButton.setVisible(true);
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

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Get Billboards from database
                        ArrayList<Billboard> list = BillboardControlPanel.listBillboards();
                        //Setup Billboard JList with current values
                        setupEditList(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();

            //Display Edit Billboard UI
            layout.show(cardLayout, "EditBillboardsList");
            this.setTitle("Edit Billboard");
            removeActionListeners(editButton);
            editButton.addActionListener(this::editBillboardActionPerformed);
            editButton.setVisible(true);
            deleteBillboardButton.setVisible(true);
            editButton.setText("Edit Billboard");
            createScheduleButton.setVisible(true);
            sendScheduleToDatabaseButton.setVisible(false);
            editScheduleButton.setVisible(false);
            createButton.setVisible(false);
            this.pack();
        }
    }

    public void editSchedulePerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

          
            //Get Selected Billboard Name
            ScheduleBillboard currentSchedule = (ScheduleBillboard) scheduleList.getSelectedValue();


            billboardNameLabel.setText(currentSchedule.getName());

            //Set Up Spinner for time input
            SpinnerModel hourModel = new SpinnerNumberModel(00,00,23,1); {
            }
            hourSpinner.setModel(hourModel);


            SpinnerModel minuteModel = new SpinnerNumberModel(00,00,59,1); {
            }
            minuteSpinner.setModel(minuteModel);

            dayOfWeekCombo.setSelectedItem(currentSchedule.getDayOfWeek());


            durationSpinner.setValue(currentSchedule.getDuration());

            layout.show(cardLayout, "Create Schedule");
            createScheduleButton.setVisible(false);
            sendScheduleToDatabaseButton.setVisible(true);
            editScheduleButton.setVisible(false);
            createButton.setVisible(false);

            this.pack();
        }
    }


    public void deleteBillboardPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            System.out.println("DELETE");

            try {
                currentBillboard = (Billboard) editBillboardList.getSelectedValue();
                BillboardControlPanel.deleteBillboard(currentBillboard.getName());
                JOptionPane.showMessageDialog(null,String.format("Deleted Billboard: %s from Database",currentBillboard.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            editBillboardsButton.doClick();
            layout.show(cardLayout, "EditBillboardsList");
            removeActionListeners(deleteBillboardButton);
            deleteBillboardButton.addActionListener(this::deleteBillboardPerformed);
            this.pack();

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

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Get Users from database
                        ArrayList<User> list = BillboardControlPanel.listUser();
                        //Setup User JList with current values
                        setupUserList(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();

            layout.show(cardLayout, "EditUsers");
            this.setTitle("Edit Users");
            removeActionListeners(editButton);
            editButton.addActionListener(this::editUserListActionPerformed);
            editButton.setVisible(true);
            editButton.setText("Edit/Delete User");
            deleteBillboardButton.setVisible(false);
            createScheduleButton.setVisible(false);
            sendScheduleToDatabaseButton.setVisible(false);
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
            layout.show(cardLayout, "ScheduleEditor");

            editButton.setVisible(false);
            editButton.setText("Edit Schedule");
            deleteBillboardButton.setVisible(false);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Get Users from database
                        ArrayList<ScheduleBillboard> list = BillboardControlPanel.listSchedules();
                        setupSchedule(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
            this.pack();
        }
        createButton.setVisible(false);
    }



    public void fileActionPerformed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            setFileChooser(frame);
        }
    }

    public void logOutPerformed(ActionEvent actionEvent)  {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            try {
                restartControlPanel(actionEvent);
                JOptionPane.showMessageDialog(null, "You have logged out successfully",
                        "Logged Out", JOptionPane.INFORMATION_MESSAGE);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
    }


    //Export Billboard to Database
    public void exportActionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            //Assign Billboard Values
            currentBillboard = new Billboard();
            currentBillboard.setName(billboardNameField.getText());
            System.out.println(currentBillboard.getName()+currentBillboard.getName());
            currentBillboard.setMessageText(messageText.getText());
            currentBillboard.setInfoText(infoText.getText());
            try {
                //If billboard not sent to database
                if (!BillboardControlPanel.exportXML(currentBillboard)){
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

    //Export Billboard Locally
    public void exportXMLLocallyPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;

            //Assign Billboard Values
            currentBillboard = new Billboard();
            currentBillboard.setName(billboardNameField.getText());
            System.out.println(currentBillboard.getName()+currentBillboard.getName());
            currentBillboard.setMessageText(messageText.getText());
            currentBillboard.setInfoText(infoText.getText());

            //SetUp File Directory to export locally
            //From https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
            JFrame jFrame = new JFrame();
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.setDialogTitle("Select Folder To Export XML");
            jFileChooser.setFileSelectionMode((JFileChooser.DIRECTORIES_ONLY));
            jFileChooser.setAcceptAllFileFilterUsed(false);


            int option = jFileChooser.showSaveDialog(jFrame);
            if (option == JFileChooser.APPROVE_OPTION){
                File file = jFileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                filePath.replaceAll("\\\\","\\\\\\\\");

                //Set filePath to export locally
                currentBillboard.setXmlFilePath(filePath);

                try {
                    //Export Billboard Locally
                    currentBillboard.exportXML();
                    JOptionPane.showMessageDialog(null,String.format("Exported Billboard: %s locally.",currentBillboard.getName()));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }


            infoText.setText("");
            billboardNameField.setText("");
            messageText.setText("");
            pictureButton.setText("Click to select Picture");
        }
    }

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
            JButton button = (JButton) source;
            layout.show(cardLayout, "BillboardCreator");
            this.setTitle("Create Billboard");


            // Put username in as creator
            currentBillboard = new Billboard("Username");
            refreshBillboard();
            editButton.setVisible(false);
            deleteBillboardButton.setVisible(false);
            sendScheduleToDatabaseButton.setVisible(false);
            createButton.setVisible(false);

        }
    }

    //Edit Button Click on Users Panel
    private void editUserListActionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            layout.show(cardLayout, "UserInfo");

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
        try {
            BillboardControlPanel.serverConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenuGUI mainMenu = new MainMenuGUI("Main Menu", frame);
        setCurrentUser(currentUser);

    }
}
