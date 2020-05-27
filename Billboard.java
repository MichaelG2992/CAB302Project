package controlPanel;

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

public class Billboard {

    // Default values for testing
    private String name;
    private String backgroundColour;
    private String messageColour;
    private String messageText;
    private String pictureUrl;
    private String pictureData;
    private String infoColour;
    private String infoText;
    private String creator;

    // Should be editable in GUI
    public String xmlFilePath;

    // Default empty constructor
    public Billboard(){

    }

    public Billboard(String username){
        creator = username;
    }
    // Constructor with all values
    public Billboard(String nameText, String backgroundColourText, String message, String messageColourText,
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
    public void setCreator(String text){creator = text;}
    public void setName(String text){name = text;}
    public void setInfoText(String text){infoText = text;}
    public void setMessageText(String text){messageText = text;}
    public void setPictureUrl(String url){pictureUrl = url;}
    public void setMessageColour(String colour){messageColour = colour;}
    public void setInfoColour(String colour){infoColour = colour;}
    public void setBackgroundColour(String colour){backgroundColour = colour;}
    public void setXmlFilePath(String path){xmlFilePath = path;}

    //Getters
    public String getCreator(){return creator;}
    public String getName(){return name;}
    public String getInfoText(){return infoText;}
    public String getMessageText(){return messageText;}
    public String getPictureUrl(){return pictureUrl;}
    public String getMessageColour(){return messageColour;}
    public String getInfoColour(){return infoColour;}
    public String getBackgroundColour(){return backgroundColour;}
    public String getXmlFilePath(){return xmlFilePath;}

    public static Billboard importXML(String importFile) throws ParserConfigurationException,
            IOException, SAXException {
        File file = new File(importFile);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(importFile);

        Billboard importedBillboard = new Billboard();

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
        StreamResult streamResult = new StreamResult(new File(xmlFilePath + ".xml"));

        // For Debugging
        // StreamResult streamResult = new StreamResult(System.out);
        transformer.transform(domSource, streamResult);

        System.out.println("Done creating XML File");
    }

    @Override
    public String toString() {
        return this.name + "                    Creator: " + this.creator;
    }
}
