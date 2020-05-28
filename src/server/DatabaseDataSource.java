package server;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;


public class DatabaseDataSource {
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS users ( "
                    + "userId INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "username VARCHAR(255) NOT NULL UNIQUE,"
                    + "password CHAR(64),"
                    + "permissions VARCHAR(255)" + ");";
    public static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS billboards ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "creator VARCHAR(255) NOT NULL,"
                    + "file BLOB" + ");";
    public static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS scheduling ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "username  VARCHAR(255) UNIQUE,"
                    + "startTime DATETIME DEFAULT NULL,"
                    + "endTime  DATETIME DEFAULT NULL" + ");";
    public static final String CREATE_USER =
            "INSERT IGNORE INTO users "
                    + "SET userId = 1,"
                    + "username = 'superUser', "
                    + "password = 'password',"
                    + "permissions = ('Create Billboards, Edit All Users, Edit All Billboards, Schedule Billboards') " + ";";

    /**
     *
     */
    public DatabaseDataSource() {
    }

    ;

    /**
     * @throws SQLException
     */
    public void DatabaseStartUp() throws SQLException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();

        //Create tables and user on initial startup of server
        statement.execute(CREATE_TABLE1);
        statement.execute(CREATE_TABLE2);
        statement.execute(CREATE_TABLE3);
        statement.execute(CREATE_USER);

        statement.close();
        // connection.close();
    }

    /**
     * @return
     * @throws SQLException
     */
    public ArrayList<Billboard> getBillboards() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(("SELECT * FROM billboards"));
        ArrayList<Billboard> billboardList = new ArrayList<Billboard>();

        while (resultSet.next()){
            String name = (resultSet.getString(1));
            String creator = (resultSet.getString(2));

            //De-serialize Billboard
            byte [] o = resultSet.getBytes(3);
            Billboard billboard = byteToObject(o);

            //Add Billboard to list
            billboardList.add(billboard);

        }

        resultSet.close();
        statement.close();
        //connection.close();

        return billboardList;
    }

    public ArrayList<User> getUsers() throws SQLException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(("SELECT * FROM users"));
        ArrayList<User> userList = new ArrayList<User>();

        while (resultSet.next()) {
            String userName = (resultSet.getString(2));
            String permissions = ((resultSet.getString(4)));
            User user = new User();
            user.setUsername(userName);
            user.setPermissions(permissions);
            userList.add(user);
        }
            resultSet.close();
            statement.close();
            //connection.close();
            return userList;
        }


    /**
     * @return
     * @throws SQLException
     */
    public Billboard viewerRequest() throws SQLException {
        Connection connection = DBConnection.getInstance();
        Billboard billboard = new Billboard();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(("SELECT name FROM SCHEDULING WHERE now() > startTime AND now() < endTime"));

        if (resultSet.next() != false) {
            billboard.setName(resultSet.getString(1));
        } else {
            System.out.println("Billboard Viewing is currently unavailable");
        }
        resultSet.close();
        statement.close();
        //connection.close();
        return billboard;
    }


    /**
     * @param request
     * @return
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public LoginReply loginSuccessful(LoginRequest request) throws SQLException, NoSuchAlgorithmException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery((String.format("SELECT password, permissions FROM users WHERE BINARY username = '%s'",
                request.getUserName())));
        LoginReply loginReply = new LoginReply();


        //Check for database results
        if (resultSet.next() != false) {
            //Retrieve password and permissions from set
            String storedPassword = resultSet.getString(1);
            String permissions = resultSet.getString(2);

            //Cross validate request password with database password
            if (storedPassword.equals(request.getPassword())) {
                loginReply.setLoginSuccessful(true);

                //Generate Session Token
                String sessionToken = loginReply.createSessionToken();
                loginReply.setUserName(request.getUserName());
                loginReply.setSessionToken(sessionToken);
                loginReply.setPermissions(permissions);

            } // Incorrect password
            else {
                loginReply.setLoginSuccessful(false);
            }

        }
        //Empty set retrieved from database
        else{
            loginReply.setLoginSuccessful(false);
        }

        resultSet.close();
        statement.close();
        //connection.close();
        return loginReply;
    }


    /**
     * @param billboard
     * @throws SQLException
     */
    //Insert billboard into database
    public void createBillboard(Billboard billboard) throws SQLException, IOException {

        final String ADD_BILLBOARD = "REPLACE INTO billboards(name, creator, file ) VALUES (?, ? , ?)";

        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(ADD_BILLBOARD);

        statement.setString(1,billboard.getName());
        statement.setString(2, billboard.getCreator());
        //Serialize billboard object
        byte [] data = objectToByte(billboard);
        statement.setBytes(3,data);

        statement.executeUpdate();
        statement.close();
        //connection.close();
    }

    //Implementation used from http://www.easywayserver.com/java/save-serializable-object-in-java/
    private  byte[] objectToByte(Object o) throws IOException {
        //Convert Billboard to bytes
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(o);

        objectOutputStream.flush();
        objectOutputStream.close();
        byteArrayOutputStream.close();

        return byteArrayOutputStream.toByteArray();

    }

    //Implementation used from http://www.easywayserver.com/java/save-serializable-object-in-java/
    private  Billboard byteToObject(byte [] bytes ) throws IOException, ClassNotFoundException {
        //Convert bytes to Billboard object
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object o = objectInputStream.readObject();
        Billboard billboard = (Billboard) o;
        objectInputStream.close();
        byteArrayInputStream.close();

        return billboard;






    }

}