package viewer;

import server.Billboard;
import server.BillboardRequest;;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Properties;
import javax.imageio.ImageIO;



public class Viewer {

    //Network components
    private static Socket socket = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static int port;
    private static String host;
    private static Billboard billboard = null;
    private static boolean gotBillboard;


    public static void createServerConnection() throws FileNotFoundException {
        Properties networkProps = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("network.props");
            networkProps.load((in));
            in.close();

            //Get data source from network properties
            int port = Integer.parseInt(networkProps.getProperty("port"));
            String host = networkProps.getProperty("host.name");

            //Set up connection to server
            Socket socket = new Socket(host, port);
            //Display Server Connection
            System.out.println(String.format("Client connected to host: %s at port: %d", host, port));

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            BillboardRequest billboardRequest = new BillboardRequest();
            objectOutputStream.writeObject(billboardRequest);


            Object o = objectInputStream.readObject();

            if (o instanceof Billboard) {
                billboard = (Billboard) o;
                System.out.println(billboard.getXmlString());
            }


            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String ConnectionFailed(){
        // message to display if the Connection fails
        return "Billboard could not be displayed or Connection to the server could not be reached";
    }

    public static String billboardFile(){
        if (gotBillboard){
            return billboard.getXmlString();
        }
        else return "";



    }

    public static void main(String args[]) {

        //connect to the server and retrieve the billboard to be displayed.
        try {
            createServerConnection();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        Dimension preferredSize = new Dimension(screenWidth, (screenHeight / 3));
        Dimension imagePlusOne = new Dimension(screenWidth, 2*(screenHeight/3));
        Dimension onlyInfo = new Dimension(3*(screenWidth/4), screenHeight/2);

        JFrame frame = new JFrame("Billboard Viewer");
        JPanel panel = new JPanel();
        JLabel tester = new JLabel();
        final int[] tested = {1};
        tester.setText("<html>testing" + tested[0] + "</html>");

        //message customization
        JLabel messageLabel = null;
        if (billboardFile().contains("<message")) {
            String[] findMessage = billboardFile().split("<message");
            findMessage[1] = findMessage[1].replaceAll("<",">");
            String[] getMessage = findMessage[1].split(">");
            System.out.println(getMessage[1]);
            messageLabel = new JLabel(getMessage[1], SwingConstants.CENTER);
            messageLabel.setText("<html><div style='text-align: center;'>" + getMessage[1] + "</div></html>");
            messageLabel.setMaximumSize(screenSize);
            messageLabel.setPreferredSize(preferredSize);
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
        BufferedImage image = null;
        if(billboardFile().contains("<picture")){
            String[] findPath;
            String[] getPath;
            String filePath;
            if(billboardFile().contains("<picture url")){
                findPath = billboardFile().split("url=\"");
                getPath = findPath[1].split("\"");
                filePath = getPath[0];
                try {
                    url = new URL(filePath);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    image = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(billboardFile().contains("<picture data")){
                findPath = billboardFile().split("data=\"");
                getPath = findPath[1].split("\"");
                filePath = getPath[0];
                byte[] decodedImage = Base64.getDecoder().decode(filePath);
                try {
                    image = ImageIO.read(new ByteArrayInputStream(decodedImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int imageWidth = screenWidth/2;
            int imageHeight = screenHeight/2;

            Image sizedImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
            imageLabel = new JLabel(new ImageIcon(sizedImage), SwingConstants.CENTER);
            imageLabel.setMaximumSize(screenSize);
            imageLabel.setPreferredSize(preferredSize);
        }

        //information customization
        JLabel infoLabel = null;
        if(billboardFile().contains("<information")) {
            String[] findInfo = billboardFile().split("<information");
            findInfo[1] = findInfo[1].replaceAll("<",">");
            String[] getInfo = findInfo[1].split(">");
            System.out.println(getInfo[1]);
            infoLabel = new JLabel(getInfo[1], SwingConstants.CENTER);
            infoLabel.setText("<html><div style='text-align: center;'>" + getInfo[1] + "</div></html>");
            infoLabel.setMaximumSize(screenSize);
            infoLabel.setPreferredSize(preferredSize);
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
        frame.setSize(screenSize);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if(messageLabel != null){
            panel.add(messageLabel);
            if(imageLabel == null && infoLabel == null){
                messageLabel.setSize(screenSize);
            }

        }
        if(imageLabel != null){
            if((messageLabel != null && infoLabel == null) ||(messageLabel == null && infoLabel != null) ){
                imageLabel.setPreferredSize(imagePlusOne);
                //imageLabel.setLocation(screenWidth/2, screenHeight/2);
            }
            if(messageLabel == null && infoLabel == null){

                imageLabel.setLocation(screenWidth/2, screenHeight/2);
            }
            panel.add(imageLabel);
        }
        if(infoLabel != null){
            if(imageLabel == null && messageLabel == null){
                infoLabel.setSize(onlyInfo);
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                infoLabel.setVerticalAlignment(SwingConstants.CENTER);
            }
            panel.add(infoLabel);
        }
        if(imageLabel == null && messageLabel == null && infoLabel == null){
            JLabel failed = new JLabel(ConnectionFailed(), SwingConstants.CENTER);
            failed.setText("<html><div style='text-align: center;'>" + ConnectionFailed() + "</div></html>");
            Font failedFont = new Font("Courier", Font.PLAIN,40);
            failed.setFont(failedFont);
            panel.add(failed);
        }
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //create a timer to attempt connection to database every 15 seconds
        ActionListener checkConnection = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    createServerConnection();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("checking connection");
                tested[0]++;
                tester.setText("<html>testing" + tested[0] + "</html>");
                panel.add(tester);
                frame.setContentPane(panel);
            }
        };

        Timer t = new Timer(2000, checkConnection);
        t.setRepeats(true);
        t.start();

        //add listeners to close Viewer on mouse press and escape press
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                t.stop();
            }
        });

        KeyAdapter listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    t.stop();
                }
            }
        };

        frame.addKeyListener(listener);

    }
}