package server;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDataSource {
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS users ( "
                    + "userId INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "username VARCHAR(255) NOT NULL UNIQUE,"
                    + "password CHAR(64),"
                    + "permissions ENUM('Create Billboards')" +");";
     public static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS billboards ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "file MEDIUMTEXT" + ");" ;
    public static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS scheduling ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "username  VARCHAR(255) UNIQUE,"
                    + "startTime DATETIME DEFAULT NULL,"
                    + "endTime  DATETIME DEFAULT NULL"+ ");" ;
    public static final String CREATE_USER =
            "INSERT IGNORE INTO users "
                    + "SET userId = 1,"
                    + "username = 'superUser', "
                    + "password = 'password',"
                    + "permissions = 'Create Billboards'" + ";" ;


    /**
     *
     */
    public DatabaseDataSource(){};

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
       // System.out.println(connection.isClosed());
    }

    /**
     * @return
     * @throws SQLException
     */
    public Billboard getBillboard() throws SQLException {
        Connection connection = DBConnection.getInstance();
        //System.out.println(connection.isClosed());
        Billboard billboard = new Billboard();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(("SELECT * FROM BILLBOARDS"));
        resultSet.next();
        billboard.setName(resultSet.getString(1));


        resultSet.close();
        statement.close();
        //connection.close();
        return billboard;
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

        if (resultSet.next() != false){
            billboard.setName(resultSet.getString(1));
        }
        else{
            System.out.println("Billboard Viewing is currently unavailable");
        }
        resultSet.close();
        statement.close();
        //connection.close();
        return billboard;
    }

    public LoginReply loginSuccessful(LoginRequest request) throws SQLException, NoSuchAlgorithmException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery((String.format("SELECT password FROM users WHERE username = '%s'",
                request.getUserName())));

        if (resultSet.next() != false){
            //Retrieve password from set
            String storedPassword = resultSet.getString(1);
            LoginReply loginReply = new LoginReply();

            if (storedPassword.equals(request.getPassword())){
                loginReply.loginSuccessful(true);
                //Generate Session Token
                String sessionToken = loginReply.createSessionToken();
                loginReply.setSessionToken(sessionToken);
            }
            else{
                loginReply.loginSuccessful(false);
            }
            return loginReply;
        }

        resultSet.close();
        statement.close();
        //connection.close();
        return null;

    }



}