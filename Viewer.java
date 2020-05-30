import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import server.BillboardTest.*;

public class Viewer {

    public static String billboardFile(){
        //paste file contents to test.
        return "<billboard>\n" +
                "    <message>Default-coloured message</message>\n" +
                "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" />\n" +
                "    <information colour=\"#60B9FF\">Custom-coloured information text</information>\n" +
                "</billboard>";
    }

    public static void main(String args[]) {

        //connect to the server and retrieve the billboard to be displayed.

        //if no billboard could be displayed show an error message

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        Dimension perferredSize = new Dimension(screenWidth, (screenHeight / 3));

        JFrame frame = new JFrame("Billboard Viewer");
        JPanel panel = new JPanel();

        //message customization
        JLabel messageLabel = null;
        /*if(getInfoTest() != null){

        }*/
        if (billboardFile().contains("<message")) {
            String[] findMessage = billboardFile().split("<message");
            findMessage[1] = findMessage[1].replaceAll("<",">");
            String[] getMessage = findMessage[1].split(">");
            System.out.println(getMessage[1]);
            messageLabel = new JLabel(getMessage[1], SwingConstants.CENTER);
            messageLabel.setMaximumSize(screenSize);
            messageLabel.setPreferredSize(perferredSize);
            if(billboardFile().contains("<message colour=")){
                String[] findColor = billboardFile().split("<message colour=\"");
                String[] getColor = findColor[1].split("\"");
                System.out.println(getColor[0]);
                messageLabel.setForeground(Color.decode(getColor[0]));
            }
            Font messageFont = new Font("Courier", Font.PLAIN,40);
            messageLabel.setFont(messageFont);
        }

        //image customization
        JLabel imageLabel = null;
        URL url = null;
        if(billboardFile().contains("<picture")){
            String[] findPath = billboardFile().split("url=\"");
            String[] getpath = findPath[1].split("\"");
            String filePath = getpath[0];
            try {
                url = new URL(filePath);

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
            imageLabel.setPreferredSize(perferredSize);
        }

        //information customization
        JLabel infoLabel = null;
        if(billboardFile().contains("<information")) {
            String[] findInfo = billboardFile().split("<information");
            findInfo[1] = findInfo[1].replaceAll("<",">");
            String[] getInfo = findInfo[1].split(">");
            System.out.println(getInfo[1]);
            infoLabel = new JLabel(getInfo[1], SwingConstants.CENTER);
            infoLabel.setMaximumSize(screenSize);
            infoLabel.setPreferredSize(perferredSize);
            if(billboardFile().contains("<information colour=")){
                String[] findColor = billboardFile().split("<information colour=");
                String[] getColor = findColor[1].split("\"");
                System.out.println(getColor[1]);
                infoLabel.setForeground(Color.decode(getColor[1]));
            }
            Font infoFont = new Font("Courier", Font.PLAIN,20);
            infoLabel.setFont(infoFont);
        }

        //background customization
        if (billboardFile().contains("<billboard background=")) {
            String[] findBackground = billboardFile().split("<billboard background=");
            String[] getBackground = findBackground[1].split("\"");
            System.out.println(getBackground[1]);
            panel.setBackground(Color.decode(getBackground[1]));
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
    }
