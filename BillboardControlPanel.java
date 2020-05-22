import com.sun.source.tree.AnnotatedTypeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;

public class BillboardControlPanel extends JFrame{

    // Should be editable in GUI
        public static final String xmlFilePath = "C:\\Users\\kathr\\Documents\\testBillboard.xml";

        // Default values for testing
        private static String backgroundColour = "#0000FF";
        private static String messageColour = "##FFFF0";
        private static String messageText = "Welcome to the ____ Corporation's Annual Fundraiser!";
        private static String pictureUrl = "https://example.com/" +
                "fundraiser_image.jpg";
        private static String pictureData= "iVBORw0KGgoAAA" +
                "ANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcm" +
                "VhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0N" +
                "yArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQIAAAACXBIWX" +
                "MAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp" +
                "1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0D" +
                "Y+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQmCC";
        private static String infoColour = "#00FFFF";
        private static String infoText = "Be sure to check out https://example.com/ for more information.";

    public static void setInfoText(String text){
        infoText = text;
    }
    public static void setMessageText(String text){
        messageText = text;
    }
    public static void setPictureUrl(String url){
        pictureUrl = url;
    }
    public static void setMessageColour(String color){
            messageColour = color;
    }
    public static void setInfoColour(String color){
        infoColour = color;
    }
    public static void setBackgroundColour(String color){
        backgroundColour = color;
    }

    public static void exportXML() throws ParserConfigurationException, TransformerException{

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
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));


            // For Debugging
            // StreamResult streamResult = new StreamResult(System.out);


            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");
        }

        public static void main(String argv[])  throws ClassNotFoundException,
           UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            //LayoutCards.createAndShowGUI();
            JFrame frame = new JFrame();

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            MainMenuGUI mainMenu  = new MainMenuGUI("Main Menu", frame);

            //frame.setContentPane(mainMenu);
            //frame.setVisible(true);
        }
}
