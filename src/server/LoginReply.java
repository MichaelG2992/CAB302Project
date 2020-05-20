package server;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class LoginReply implements Serializable {
    private String userName;
    private boolean loginSuccessful;
    private String sessionToken;

    public LoginReply() throws NoSuchAlgorithmException {
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

    public void loginSuccessful(boolean loginSuccessful){
        this.loginSuccessful = loginSuccessful ;
    }


    /**
     * @return
     */
    // From Week 9 Assignment Q/A
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
