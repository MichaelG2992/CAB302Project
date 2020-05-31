package server;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class LoginReply implements Serializable {
    private String userName;
    private boolean loginSuccessful = false;
    private String sessionToken;
    private boolean createBillboards,
            editAllBillboards,
            scheduleBillboards,
            editUsers;

    public LoginReply()  {
    }

    public boolean getLoginSuccessful(){
        return loginSuccessful;
    }

    public String getSessionToken(){
        return sessionToken;
    }

    public void setSessionToken(String sessionToken){
         this.sessionToken = sessionToken ;
    }

    public void setLoginSuccessful(boolean loginSuccessful){
        this.loginSuccessful = loginSuccessful ;
    }

    public void setUserName(String userName){this.userName=userName;}

    public String getUserName() {
        return userName;
    }


    public void setCreateBillboards(boolean value){createBillboards = value;}
    public boolean getCreateBillboards(){return createBillboards;}

    public void setEditAllBillboards(boolean value){editAllBillboards = value;}
    public boolean getEditAllBillboards(){return editAllBillboards;}

    public void setScheduleBillboards(boolean value){scheduleBillboards = value;}
    public boolean getScheduleBillboards(){return scheduleBillboards;}

    public void setEditUsers(boolean value){editUsers = value;}
    public boolean getEditUsers(){return editUsers;}




    /**
     * @return
     */
    // From Week 9 Assignment Q/A
    //Creates random session token
    public static  String createSessionToken(){
        Random random = new Random();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        String randomString = bytesToString(randomBytes);
        return randomString;

    }

    /**
     * @param hash
     * @return
     */
    // From Week 9 Assignment Q/A
    public static String bytesToString(byte[] hash){
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hash){
            stringBuffer.append((String.format("%02x", b & 0xFF)));
        }
        return stringBuffer.toString();


}}
