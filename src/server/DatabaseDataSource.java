package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDataSource {
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS users ( "
                    + "userId INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "username VARCHAR(255),"
                    + "password CHAR(64),"
                    + "permissions ENUM('Create Billboards')" +");";
     public static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS billboards ( "
                    + "billboardNo INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "file MEDIUMTEXT" + ");" ;
    public static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS scheduling ( "
                    + "billboardNo INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "DateTime DATETIME DEFAULT NULL" + ");" ;

    private final Connection connection;


    public DatabaseDataSource() throws SQLException {
        connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE1);
        statement.execute(CREATE_TABLE2);
        statement.execute(CREATE_TABLE3);
        connection.close();
    }


}