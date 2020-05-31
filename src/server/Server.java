package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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

    //User and SessionTokens
    private static HashMap<String, String> users = new HashMap<String, String>();
    public ObjectInputStream objectInputStream = null;


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        Properties networkProps = new Properties();
        FileInputStream in = null;
        ServerSocket serverSocket = null;
        try {
            in = new FileInputStream("./network.props");
            networkProps.load((in));
            in.close();

            //Get data source from network properties
            int port = Integer.parseInt(networkProps.getProperty("port"));

            //Set up connection
            serverSocket = new ServerSocket(port);

            //Display Server Connection
            System.out.println(String.format("Server set up at port: %d", port));

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        //Create tables if not exist
        DatabaseDataSource dataSource = new DatabaseDataSource();
        dataSource.DatabaseStartUp();

        for (; ; ) {

            Socket socket = serverSocket.accept();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((socket.getOutputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            try {
                Object o = objectInputStream.readObject();
                System.out.println("\n Received Request: " + o.getClass());

                //Check request
                NetworkProtocol networkProtocol = new NetworkProtocol();
                int state = networkProtocol.Request(o);


                //Requests
                switch (state) {
                    //Billboard Viewer Request
                    case 0:
                        //Retrieve billboard data from database
                        DatabaseDataSource billboardData = new DatabaseDataSource();
                        Billboard b = billboardData.viewerRequest();
                        objectOutputStream.writeObject(b);
                        break;

                    //Login Request
                    case 1:
                        LoginRequest loginRequest = (LoginRequest) o;

                        //Instantiate Database object
                        DatabaseDataSource loginData = new DatabaseDataSource();

                        //Check request logindata to database logindata
                        LoginReply l = loginData.loginSuccessful(loginRequest);

                        //If LoginSuccessful set user and sessiontoken within server
                        if (l.getLoginSuccessful()) {
                            users.put(l.getUserName(), l.getSessionToken());
                        }
                        objectOutputStream.writeObject(l);
                        break;


                    //Create Billboard Request
                    case 2:
                        Billboard billboard = (Billboard) o;

                        //Check for valid sessiontoken
                        if (users.containsValue(billboard.getSessionToken())) {
                            //Create billboard and add to database
                            DatabaseDataSource createData = new DatabaseDataSource();
                            createData.createBillboard(billboard);
                            System.out.println(String.format("Added Name: %s to the database", billboard.getName()));
                        } else {
                            GeneralMessageResponse generalMessageResponse = new GeneralMessageResponse("Not a vaild Session Token. Please Log In to continue.");
                            objectOutputStream.writeObject(generalMessageResponse);

                        }
                        break;


                    //List Billboards Request
                    case 3:
                        ListBillboardsRequest listBillboardsRequest = (ListBillboardsRequest) o;
                        if (users.containsValue(listBillboardsRequest.getSessionToken())){
                            DatabaseDataSource listData = new DatabaseDataSource();
                            ArrayList<Billboard> listBillboards = listData.getBillboards();
                            objectOutputStream.writeObject(listBillboards);

                        }else {
                            GeneralMessageResponse generalMessageResponse = new GeneralMessageResponse("Not a vaild Session Token. Please Log In to continue.");
                            objectOutputStream.writeObject(generalMessageResponse);

                        }
                        break;

                        //List Users Request
                    case 4:
                        UserRequest userRequest = (UserRequest) o;
                        if (users.containsValue(userRequest.getSessionToken())){
                            DatabaseDataSource userData = new DatabaseDataSource();
                            ArrayList<User> listUsers = userData.getUsers();
                            objectOutputStream.writeObject(listUsers);
                        }
                        else {
                            GeneralMessageResponse generalMessageResponse = new GeneralMessageResponse("Not a vaild Session Token. Please Log In to continue.");
                            objectOutputStream.writeObject(generalMessageResponse);
                        }
                        break;

                        //Edit Password Request
                    case 5:
                        EditPasswordRequest editPasswordRequest = (EditPasswordRequest) o;
                        DatabaseDataSource passwordData = new DatabaseDataSource();
                        passwordData.editPassword(editPasswordRequest.getUsername(),editPasswordRequest.getPassword());
                        System.out.println("CHANGED PASSWORD to" + editPasswordRequest.getPassword());
                        break;

                        //Delete User Request
                    case 6:
                        DeleteUserRequest deleteUserRequest = (DeleteUserRequest) o;
                        DatabaseDataSource deleteUserData = new DatabaseDataSource();
                        deleteUserData.deleteUser(deleteUserRequest.getUsername());
                        System.out.println("DELETED USER" + deleteUserRequest.getUsername());
                        break;


                        //Delete Billboard Request
                    case 7:
                        DeleteBillboardRequest deleteBillboardRequest = (DeleteBillboardRequest) o;
                        DatabaseDataSource deleteBillboardData = new DatabaseDataSource();
                        deleteBillboardData.deleteBillboard(deleteBillboardRequest.getName());
                        System.out.println("DELETED BILLBOARD" + deleteBillboardRequest.getName());
                        break;

                        //Create Billboard Schedule Request
                    case 8:
                        ScheduleBillboard scheduleBillboard = (ScheduleBillboard) o;
                        DatabaseDataSource scheduleBillboardData = new DatabaseDataSource();
                        scheduleBillboardData.scheduleBillboard(scheduleBillboard);
                        break;

                        //List Schedules Request
                    case 9 :
                        DatabaseDataSource listScheduleData = new DatabaseDataSource();
                        ArrayList<ScheduleBillboard> scheduleList = listScheduleData.getSchedules();
                        objectOutputStream.writeObject(scheduleList);
                        break;

                        //Create User Request
                    case 10:
                        DatabaseDataSource createUserData = new DatabaseDataSource();
                        CreateUser createUser = (CreateUser) o;
                        createUserData.createUser(createUser);
                        break;

                    case 11:
                        DatabaseDataSource changePermissionsData = new DatabaseDataSource();
                        ChangePermissions changePermissions = (ChangePermissions) o;
                        changePermissionsData.changePermissions(changePermissions);
                        break;



                    default:
                        objectOutputStream.writeObject(null);

                }
            }// If no connection to server
            catch (IOException e) {
                continue;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            objectInputStream.close();
                objectOutputStream.flush();
                objectOutputStream.close();
                socket.close();


            }



        }
}

