package server;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DatabaseDataSource {
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS users ( "
                    + "userId INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "username VARCHAR(255) NOT NULL UNIQUE,"
                    + "password CHAR(64),"
                    + "permissions BLOB" + ");";
    public static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS billboards ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "creator VARCHAR(255) NOT NULL,"
                    + "file BLOB" + ");";
    public static final String CREATE_TABLE3 =
            "CREATE TABLE IF NOT EXISTS scheduling ( "
                    + "name VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,"
                    + "username  VARCHAR(255),"
                    + "startTime TIME ,"
                    + "endTime  TIME ,"
                    + "dayOfWeek VARCHAR(255),"
                    + "duration INTEGER"+ ");";
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


        //Create Permissions for superUser
        User superUser = new User();
        superUser.setCreateBillboards(true);
        superUser.setEditAllBillboards(true);
        superUser.setEditUsers(true);
        superUser.setScheduleBillboards(true);

        //Create tables and user on initial startup of server
        statement.execute(CREATE_TABLE1);
        statement.execute(CREATE_TABLE2);
        statement.execute(CREATE_TABLE3);
        statement.execute(CREATE_USER);

        String insertPermissions = "UPDATE USERS SET permissions = ? WHERE username = 'superUser'";
        PreparedStatement preparedStatement = connection.prepareStatement(insertPermissions);






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


    public ArrayList<ScheduleBillboard> getSchedules() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance();
        Statement statement = connection.createStatement();

        ArrayList<String> dayList = new ArrayList<String>();
        dayList.add("Sunday");
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");

        ResultSet resultSet = statement.executeQuery(("SELECT * FROM SCHEDULING"));
        ArrayList<ScheduleBillboard> scheduleList = new ArrayList<ScheduleBillboard>();

            while (resultSet.next()) {
                String name = (resultSet.getString(1));
                String username = ((resultSet.getString(2)));
                String startTime = ((resultSet.getString(3)));
                Integer day = ((resultSet.getInt(5)));
                Integer duration = ((resultSet.getInt(6)));

                String dayString =dayList.get(day);

                ScheduleBillboard scheduleBillboard = new ScheduleBillboard();
                scheduleBillboard.setName(name);
                scheduleBillboard.setUsername(username);
                scheduleBillboard.setStartTime(startTime);
                scheduleBillboard.setDuration(duration);
                scheduleBillboard.setDayOfWeek(dayString);

                scheduleList.add(scheduleBillboard);
            }


        resultSet.close();
        statement.close();
        //connection.close();

        return scheduleList;
    }
    public void editPassword(String userName, String password) throws SQLException {
        Connection connection = DBConnection.getInstance();
        final String EDIT_PASSWORD = "UPDATE users SET password = ? WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(EDIT_PASSWORD);

        statement.setString(1,password);
        statement.setString(2, userName);
        statement.executeUpdate();
        statement.close();
    }

    public void createUser(CreateUser user) throws SQLException {
        Connection connection = DBConnection.getInstance();
        final String CREATE_USER = "INSERT INTO USERS(username,password) VALUES(?,?)";
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        PreparedStatement statement = connection.prepareStatement(CREATE_USER);


        statement.setString(1,user.getUserName());
        statement.setString(2,user.getPassword());

        statement.executeUpdate();
        statement.close();

    }

    public void deleteUser(String userName) throws SQLException {
        Connection connection = DBConnection.getInstance();
        final String DELETE_USER = "DELETE FROM users  WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(DELETE_USER);

        statement.setString(1,userName);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteBillboard(String name) throws SQLException {
        Connection connection = DBConnection.getInstance();
        final String DELETE_BILLBOARD = "DELETE FROM billboards  WHERE name = ?";

        PreparedStatement statement = connection.prepareStatement(DELETE_BILLBOARD);

        statement.setString(1,name);
        statement.executeUpdate();
        statement.close();
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
            userList.add(user);
        }
            resultSet.close();
            statement.close();
            //connection.close();
            return userList;
        }




     String [] dayOfWeek = new String[] {"Sunday", "Monday", " Tuesday", "Wednesday", "Thursday", "Friday", " Saturday"};



     public void scheduleBillboard(ScheduleBillboard scheduleBillboard) throws SQLException, ParseException {

         ArrayList<String> dayList = new ArrayList<String>();
         dayList.add("Sunday");
         dayList.add("Monday");
         dayList.add("Tuesday");
         dayList.add("Wednesday");
         dayList.add("Thursday");
         dayList.add("Friday");
         dayList.add("Saturday");

         Connection connection = DBConnection.getInstance();
         final String SCHEDULE_BILLBOARD = "REPLACE INTO scheduling( name,username,startTime,endTime,dayOfWeek, duration) VALUES(?,?,?,?,?,?)";



         DateFormat format = new SimpleDateFormat("HH:mm");
         DateFormat day = new SimpleDateFormat("EEEE");
         String dateString = "2020-05-30";
         String timeString = scheduleBillboard.getStartTime();

         int duration = scheduleBillboard.getDuration();


         Date date = format.parse(timeString);

         //Create Calendar
         Calendar calendar =  Calendar.getInstance();
         calendar.setTime(date);

         //Add Duration to start time
         calendar.add(Calendar.MINUTE,duration);


         //Get Day of the week
         int dayNum  = dayList.indexOf(scheduleBillboard.getDayOfWeek()) + 1;
         Date endDate = calendar.getTime();

         //Convert Java Date to Mysql Timestamp
         java.sql.Time startDate = new java.sql.Time(date.getTime());
         java.sql.Time endTime = new java.sql.Time(endDate.getTime());



         PreparedStatement statement = connection.prepareStatement(SCHEDULE_BILLBOARD);
         statement.setString(1,scheduleBillboard.getName());
         statement.setString(2,scheduleBillboard.getUsername());
         statement.setTime(3,startDate);
         statement.setTime(4,endTime);
         statement.setInt(5,dayNum);
         statement.setInt(6,duration);
         statement.executeUpdate();
         statement.close();

     }





    /**
     * @return
     * @throws SQLException
     */
    public Billboard viewerRequest() throws SQLException {
        Connection connection = DBConnection.getInstance();
        Billboard billboard = new Billboard();

        final String GET_BILLBOARD = " SELECT name FROM SCHEDULING WHERE (now() > startTime AND now() < endTime)";


        PreparedStatement statement1 = connection.prepareStatement(GET_BILLBOARD);

        String name=null;
        ResultSet resultSet1 = statement1.executeQuery();
        if (resultSet1.next() != false) {
             name = (resultSet1.getString(1));
        }

        final String GET_BILLBOARD2 = String.format("SELECT file FROM BILLBOARDS WHERE name = '%s'",name);
        PreparedStatement statement2 = connection.prepareStatement(GET_BILLBOARD2);
        ResultSet resultSet2 = statement2.executeQuery(GET_BILLBOARD2);
        Billboard currentBillboard = null;


            if (resultSet2.next() != false) {
                //De-serialize Billboard
                byte [] o = resultSet2.getBytes(1);
                try {
                     currentBillboard = byteToObject(o);
                     System.out.println(currentBillboard.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            statement1.close();
            statement2.close();
            resultSet1.close();
            resultSet2.close();
            //connection.close();
            return currentBillboard;
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