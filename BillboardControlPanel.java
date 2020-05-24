package controlPanel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
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


        public static void main(String argv[])  throws ClassNotFoundException,
           UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            JFrame frame = new JFrame();

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            MainMenuGUI mainMenu  = new MainMenuGUI("Main Menu", frame);

        }
}
