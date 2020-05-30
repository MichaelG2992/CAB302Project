package server;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class BillboardTest implements Serializable{

    // Default values for testing
    private static String name = "Test Billboard";
    private static String backgroundColour = "#0000FF";
    private static String messageColour = "##FFFF0";
    private static String messageText = "Welcome to the ____ Corporation's Annual Fundraiser!";
    private static String pictureUrl = "https://example.com/" +
            "fundraiser_image.jpg";
    private static String infoColour = "#00FFFF";
    private static String infoText = "Be sure to check out https://example.com/ for more information.";


    // Should be editable in GUI
    public String xmlFilePath;
    private String creator;
    private String sessionToken;


    // Default empty constructor
    public BillboardTest(){

    }

    public BillboardTest(String username){
        creator = username;
    }
    // Constructor with all values
    public BillboardTest(String nameText, String backgroundColourText, String message, String messageColourText,
                         String pictureUrlText, String info, String infoColourText, String username){
        name = nameText;
        backgroundColour = backgroundColourText;
        messageText = message;
        messageColour = messageColourText;
        pictureUrl = pictureUrlText;
        infoColour = infoColourText;
        infoText = info;
        creator = username;
    }

    //Setters
    public void setName(String text) {
        name = text;
    }


    public void setInfoText(String text) {
        infoText = text;
    }

    public void setMessageText(String text) {
        messageText = text;
    }

    public void setPictureUrl(String url) {
        pictureUrl = url;
    }

    public void setMessageColour(String colour) {
        messageColour = colour;
    }

    public void setInfoColour(String colour) {
        infoColour = colour;
    }

    public void setBackgroundColour(String colour) {
        backgroundColour = colour;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }


    //Getters
    public String getName() {
        return name;
    }

    public String getInfoText() {
        return infoText;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getMessageColour() {
        return messageColour;
    }

    public String getInfoColour() {
        return infoColour;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public String getCreator() {
        return creator;
    }

    public String getSessionToken() {
        return sessionToken;
    }



    public static BillboardTest importXML(String importFile) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(importFile);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(importFile);

        BillboardTest importedBillboard = new BillboardTest();

        // Get values from XML
        String name = importFile.substring(importFile.lastIndexOf('\\') + 1);
        name = name.substring(0, name.length() - 4);
        importedBillboard.setName(name);
        // Get background colour
        NodeList billboardNodes = document.getElementsByTagName("billboard");
        Node billboard = billboardNodes.item(0);
        NamedNodeMap billboardBackground = billboard.getAttributes();
        if(billboardBackground != null){
            importedBillboard.setBackgroundColour(billboardBackground.getNamedItem("background").getTextContent());
        }

        // Get message text and colour
        NodeList nodelistMessage = document.getElementsByTagName("message");
        Node message = nodelistMessage.item(0);
        NamedNodeMap messageColour= message.getAttributes();
        if(message != null){
            importedBillboard.setMessageText(document.getElementsByTagName("message").item(0).getTextContent());
            if(messageColour != null ){
                importedBillboard.setMessageColour(messageColour.getNamedItem("colour").getTextContent());
            }
        }

        // Get picture URL
        NodeList nodelistPicture = document.getElementsByTagName("picture");
        Node picture = nodelistPicture.item(0);
        //importedBillboard.setPictureUrl(document.getElementsByTagName("picture").item(0).getTextContent());
        NamedNodeMap pictureURL = picture.getAttributes();
        if(picture != null){
            if(pictureURL != null ){
                importedBillboard.setPictureUrl(pictureURL.getNamedItem("url").getTextContent());
            }
        }

        // Get info text and colour
        NodeList nodelistInfo = document.getElementsByTagName("information");
        Node information = nodelistInfo.item(0);
        NamedNodeMap infoColour = information.getAttributes();
        if(information != null){
            importedBillboard.setInfoText(document.getElementsByTagName("information").item(0).getTextContent());
            if(infoColour != null){
                importedBillboard.setInfoColour(infoColour.getNamedItem("colour").getTextContent());
            }
        }

        return importedBillboard;
    }

    public void exportXML()
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("billboard");
        document.appendChild(root);
        Attr backgroundColourAttr = document.createAttribute("background");
        root.setAttributeNode(backgroundColourAttr);
        backgroundColourAttr.setValue(backgroundColour);
        // Message element
        Element message= document.createElement("message");

        root.appendChild(message);

        // set an attribute to message element
        Attr messageColourAttr = document.createAttribute("colour");
        messageColourAttr.setValue(messageColour);
        message.setAttributeNode(messageColourAttr);
        message.appendChild(document.createTextNode(messageText));

        // Picture element and attribute
        Element picture = document.createElement("picture");
        Attr pictureInfo = document.createAttribute("url");
        pictureInfo.setValue(pictureUrl);
        picture.setAttributeNode(pictureInfo);
        root.appendChild(picture);

        // Information element and attribute
        Element information = document.createElement("information");
        Attr infoColourAttr = document.createAttribute("colour");
        infoColourAttr.setValue(infoColour);
        information.setAttributeNode(infoColourAttr);
        information.appendChild(document.createTextNode(infoText));
        root.appendChild(information);

        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath + "\\\\"+ this.name + ".xml"));

        // For Debugging
        // StreamResult streamResult = new StreamResult(System.out);
        transformer.transform(domSource, streamResult);


        System.out.println("Done creating XML File");
    }

    @Override
    public String toString() {
        return "NAME:  " + this.name +    "                          CREATOR: " + this.creator;
    }
}
