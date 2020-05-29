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

public class Viewer {
    public static boolean counted(long timeStarted){
        long nowMillis = System.currentTimeMillis();
        int timePassed = (int)((nowMillis - timeStarted) / 1000);

        if(timePassed >= 5){
            return true;
        }
        else{
            return false;
        }
    }

    public static String viewChanger(long time, int number){
        if(counted(time) == true){
            if(number >= 16){
                number = number + 1;
            }
            else {
                number = 1;
            }
        }
        return "src/billboards/" + number + ".png";
    }

    public static String billboardFile(){
        //paste file contents to test.
        return "<billboard>\n" +
                "    <information>Billboard with an information tag and nothing else. Note that the text is word-wrapped. The quick brown fox jumped over the lazy dogs.</information>\n" +
                "</billboard>";
    }

    public static void main(String args[]) {

        //connect to the server and retrieve the billboard to be displayed.

        //if no billboard could be displayed show an error message

        /* the code for displaying url
        * String path = "http://chart.finance.yahoo.com/z?s=GOOG&t=6m&q=l";
                    System.out.println("Get Image from " + path);
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    JLabel label = new JLabel(new ImageIcon(image));
                    JFrame f = new JFrame();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.getContentPane().add(label);
                    f.pack();
                    f.setLocation(200, 200);
                    f.setVisible(true);
        * */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //long timeStarted = System.currentTimeMillis();
        String filePath = "https://cloudstor.aarnet.edu.au/plus/s/EvYVdlUNx72ioaI/download";
        URL url = null;
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
        JFrame frame = new JFrame("Billboard Viewer");
        JPanel panel = new JPanel();
        //JLabel label = new JLabel(new ImageIcon(image));

        //message customization
        JLabel messageLabel = null;
        if (billboardFile().contains("<message")) {
            //String helper = billboardFile().replace(">", "<");
            String[] findMessage = billboardFile().split(">");
            findMessage[2] = findMessage[2].replaceAll("<.+","");
            System.out.println(findMessage[2]);
            messageLabel = new JLabel(findMessage[2]);
            //Font messageFont = new Font("Courier", Font.BOLD,75);
        }

        //image customization
        //JLabel imageLabel = new JLabel("test text"/*new ImageIcon(image)*/);

        //information customization
        JLabel infoLabel = null;
        if(billboardFile().contains("<information")) {
            infoLabel = new JLabel("test text"/*new ImageIcon(image)*/);
            //Font infoFont = new Font("Courier", Font.PLAIN,50);
        }

        //background customization
        if (billboardFile().contains("<billboard background=")) {
            String[] findBackground = billboardFile().split("\"");
            frame.setBackground(Color.decode(findBackground[1]));
        }

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(screenWidth, screenHeight);
        //messageLabel.setFont(messageFont);
        //messageLabel.setHorizontalTextPosition(JLabel.CENTER);
        //messageLabel.setVerticalTextPosition(JLabel.CENTER);
        //panel.add(messageLabel);
        frame.setContentPane(messageLabel);
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
