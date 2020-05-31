package viewer;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;


public class Viewer {


    //Network components
    private static Socket socket = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static int port;
    private static String host;


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


    public static void createServerConnection() throws FileNotFoundException {
        Properties networkProps = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("./network.props");
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
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void main(String args[]){
            try {
                createServerConnection();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            long timeStarted = System.currentTimeMillis();
        String filePath = "src/viewer/billboards/1.png";
        int picNum = 1;
        JFrame frame = new JFrame("Billboard Viewer");
        ImageIcon icon = new ImageIcon(filePath);
        JLabel label = new JLabel(icon);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        frame.add(label);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        KeyAdapter listener = new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        };

        frame.addKeyListener(listener);

        /*while(true){
            if(!filePath.equals(viewChanger(timeStarted, picNum))){
                filePath = viewChanger(timeStarted, picNum);
                label.setIcon(new ImageIcon(filePath));
                frame.add(label);
                frame.pack();
                frame.setVisible(true);

            }*/
    }
}
