package controlPanel;

import com.sun.tools.javac.Main;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class BillboardControlPanel extends JFrame {

    //Network components
    private static Socket socket = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static int port;
    private static String host;

    //Current User Properties
    private static String creator;
    private static String sessionToken;
    private static String permissions;



    public static boolean sendXML(Billboard billboard) throws ParserConfigurationException, TransformerException {

        boolean exportSuccess = false;

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("billboard");
            document.appendChild(root);
            Attr backgroundColourAttr = document.createAttribute("background");
            root.setAttributeNode(backgroundColourAttr);
            backgroundColourAttr.setValue(billboard.getBackgroundColour());
            // Message element
            Element message = document.createElement("message");

            root.appendChild(message);

            // set an attribute to message element
            Attr messageColourAttr = document.createAttribute("colour");
            messageColourAttr.setValue(billboard.getMessageColour());
            message.setAttributeNode(messageColourAttr);
            message.appendChild(document.createTextNode(billboard.getMessageText()));


            // Picture element and attribute
            Element picture = document.createElement("picture");
            Attr pictureInfo = document.createAttribute("url");
            pictureInfo.setValue(billboard.getPictureUrl());
            picture.setAttributeNode(pictureInfo);
            root.appendChild(picture);

            // Information element and attribute
            Element information = document.createElement("information");
            Attr infoColourAttr = document.createAttribute("colour");
            infoColourAttr.setValue(billboard.getInfoColour());
            information.setAttributeNode(infoColourAttr);
            information.appendChild(document.createTextNode(billboard.getInfoText()));
            root.appendChild(information);

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            //StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            // StreamResult streamResult = new StreamResult(new StringWriter());
            StringWriter stringWriter = new StringWriter();


            // For Debugging
            //streamResult = new StreamResult(System.out);
            transformer.transform(domSource, new StreamResult(stringWriter));

            //Convert dom source to XML string
            String file = stringWriter.getBuffer().toString();


            //Send Billboard to server
            try {
                //Set session token to request
                billboard.setSessionToken(sessionToken);
                //Set Creator to billboard
                billboard.setCreator(creator);
                //Establish  Connection
                clientConnection();
                objectOutputStream.writeObject(billboard);
                objectOutputStream.flush();

                //Read Response Message
                Object o = objectInputStream.readObject();

                //If Unsuccessful Message received from server due to invalid session
                if (o instanceof GeneralMessageResponse) {
                    GeneralMessageResponse generalMessageResponse = (GeneralMessageResponse) o;
                    String responseMessage = generalMessageResponse.getMessage();
                    JOptionPane.showMessageDialog(null, responseMessage,
                            "Error", JOptionPane.OK_OPTION);
                    exportSuccess = false;

                } else {
                    exportSuccess = true;
                }

            //No unsuccessful message received from server
            } catch (IOException e) {
                exportSuccess = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return exportSuccess;
    }

    /**
     *
     */
        //Check for connection to server
        public static boolean serverConnection() throws IOException {
            boolean connection = false;
            Properties networkProps = new Properties();
            FileInputStream in = null;

            try {
                in = new FileInputStream("./network.props");
                networkProps.load((in));
                in.close();

                //Get data source from network properties
                port = Integer.parseInt(networkProps.getProperty("port"));
                host = networkProps.getProperty("host.name");

                //Set up connection to server
                socket = new Socket(host, port);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());


                //Display Server Connection
                System.out.println(String.format("Client connected to host: %s at port: %d", host, port));
                connection = true;

            }// If no connection to server
            catch (IOException | NumberFormatException e) {
                return connection;
            }
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();

            return connection;

        }


        //Log in authentication
        public static boolean logIn(String usernameText, String passwordText) {
            boolean loggedIn = false;

            if ( socket.isClosed()){
                try {
                    clientConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //Send Login request to server
                LoginRequest loginRequest = new LoginRequest("superUser","password");
                objectOutputStream.writeObject(loginRequest);
                objectOutputStream.flush();

                Object o = objectInputStream.readObject();

                if (o instanceof LoginReply) {
                    LoginReply loginReply = (LoginReply) o;
                    //If log in successful
                    //Set current session token and permissions
                    if (loginReply.getLoginSuccessful()) {
                            System.out.println(String.format("Logged in as: %s with Session token %s and permissions: %s", loginReply.getUserName(),
                                    loginReply.getSessionToken(), loginReply.getPermissions()));
                            sessionToken = loginReply.getSessionToken();

                            //permissions = loginReply.getPermissions();
                            creator = loginReply.getUserName();
                            loggedIn = true;
                    } else {
                        loggedIn = false;
                    }
                    objectOutputStream.close();
                    objectInputStream.close();
                    socket.close();
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("CLOSED SOCKET LOG IN");
            }
            return loggedIn;
        }

        //Open new connection to server from client
        public static void clientConnection() throws IOException {
                socket = new Socket(host,port);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());

        }



        public static void main(String argv[]) throws ClassNotFoundException,
                UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
            //LayoutCards.createAndShowGUI();

            //Set up connection to server
            if (serverConnection()) {
                    JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    MainMenuGUI mainMenu = new MainMenuGUI("Main Menu", frame);

                    // For testing or if database is empty
                    User superUser = new User("SuperUser", true,
                            true, true, true);
                    mainMenu.setCurrentUser(superUser);
                }
            //Display Error message
            else{
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                ServerDownMessage serverError  = new ServerDownMessage("Error", frame);
            }
            //frame.setContentPane(mainMenu);
            //frame.setVisible(true);
        }
}
