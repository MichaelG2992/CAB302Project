package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 */
public class Server {
    /**
     * @param args
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        Properties networkProps = new Properties();
        FileInputStream in = null;
        ServerSocket serverSocket = null;
        try{
            in = new FileInputStream("./network.props");
            networkProps.load((in));
            in.close();

            //Get data source from network properties
            int port = Integer.parseInt(networkProps.getProperty("port"));

            //Set up connection
            serverSocket = new ServerSocket(port);

            //Display Server Connection
            System.out.println(String.format("Server set up at port: %d",port));

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        //Create tables if not exist
        DatabaseDataSource dataSource = new DatabaseDataSource();
        dataSource.DatabaseStartUp();
        while(true) {

            Socket socket = serverSocket.accept();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((socket.getOutputStream()));
            Object o = objectInputStream.readObject();
            System.out.println("\n Received Request: " +  o.getClass());

            //Check request
            NetworkProtocol networkProtocol = new NetworkProtocol();
            int state = networkProtocol.Request(o);

            //Requests
            switch(state)
            {
                //Billboard Viewer Request
                case 0:
                    //Retrieve  billboard data from database
                    DatabaseDataSource billboardData = new DatabaseDataSource();
                    Billboard b = billboardData.viewerRequest();
                    objectOutputStream.writeObject(b);
                    break;

                //Login Request
                case 1:
                    LoginRequest loginRequest = (LoginRequest) o;
                    //Retrieve and Compare user data from database
                    DatabaseDataSource loginData = new DatabaseDataSource();

                    //Check request data to stored data
                    //Convert to Serializable class
                    LoginReply l = loginData.loginSuccessful(loginRequest);
                    objectOutputStream.writeObject(l);
                    break;

                default:
                    System.out.println("Invalid response");
                    objectOutputStream.writeObject(null);
            }
            objectOutputStream.flush();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }
}

