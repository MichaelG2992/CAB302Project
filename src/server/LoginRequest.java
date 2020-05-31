package server;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//From Week 9 Assignment Q/A

public class LoginRequest extends Request {
    private String userName;
    private String password;



    public LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
        /*
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String input = "password";
        byte [] hashedPassword = messageDigest.digest(input.getBytes());

        setPassword(bytesToString(hashedPassword));
        System.out.print(String.format("Password: %s  Hashed Password:%s",input,bytesToString(hashedPassword)));
        */
    }

    public static String bytesToString(byte[] hash){
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hash){
            stringBuffer.append((String.format("%02x", b & 0xFF)));
        }
        return stringBuffer.toString();
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }



}
