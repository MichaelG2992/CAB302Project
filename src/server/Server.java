package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {

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

        //Connect to database
        Connection instance = DBConnection.getInstance();

        //Create tables if not exist
        DatabaseDataSource dataSource = new DatabaseDataSource();

        for (;;) {
            assert serverSocket != null;
            Socket socket = serverSocket.accept();
            socket.close();
        }
    }
}

