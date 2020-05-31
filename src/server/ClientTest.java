package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class ClientTest {
    public static void main(String[] args)  {

        Properties networkProps = new Properties();
        FileInputStream in = null;

        try{
            in = new FileInputStream("./network.props");
            networkProps.load((in));
            in.close();

            //Get data source from network properties
            int port = Integer.parseInt(networkProps.getProperty("port"));
            String host = networkProps.getProperty("host.name");

            //Set up connection to server
            Socket socket = new Socket(host,port);
            //Display Server Connection
            System.out.println(String.format("Client connected to host: %s at port: %d" ,host, port));

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            //TESTING REQUESTS
            ListBillboardsRequest billboardRequest = new ListBillboardsRequest();
            objectOutputStream.writeObject(billboardRequest);
            //LoginRequest loginRequest = new LoginRequest("superUser","password");
            //ListBillboardsRequest listBillboardsRequest = new ListBillboardsRequest();
            //erRequest userRequest = new UserRequest();
           //ObjectOutputStream.writeObject(userRequest);
            objectOutputStream.flush();



            Object o = objectInputStream.readObject();


            if (o instanceof ListBillboardsRequest){
                Billboard billboard = (Billboard) o;
                System.out.println(" Billboard Name is : " + billboard.getName());
            }
            else if ( o instanceof LoginReply){
                LoginReply loginReply = (LoginReply) o;
                if (loginReply.getLoginSuccessful() == true){
                    System.out.println("Log in successful with session token:" + ((LoginReply) o).getSessionToken());
                }
                else{
                    System.out.println("Log in not successful");
                }
            }

            else if (o.toString().contains("Billboard")){
                ArrayList<Billboard> list = (ArrayList<Billboard>) o;
                int count = 0;
                for (Billboard billboard : list) {
                    System.out.println("Billboard Name:" + billboard.getName());
                    System.out.println("Creator:" + billboard.getCreator());
                    count ++;


                }
                System.out.println(count);
            }

            else if (o.toString().contains("User")){
                ArrayList<User> list = (ArrayList<User>) o;
                   for (User user : list){
                        System.out.println("User Name:" + user.getUserName());
                        //System.out.println("User Permissions:" + user.getPermissions());

                    }

                }

            else{
                System.out.println("\nUnknown Request");
            }


            objectInputStream.close();
            objectOutputStream.close();
            socket.close();

        } catch (IOException | NumberFormatException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}