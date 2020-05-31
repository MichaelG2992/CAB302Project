package server;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class LoginReply implements Serializable {
    private String userName;
    private boolean loginSuccessful = false;
    private String sessionToken;
    private String permissions;

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

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

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
