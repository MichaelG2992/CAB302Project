package server;

import java.io.Serializable;

public class EditPasswordRequest implements Serializable {

    private String username;
    private String password;
    private String sessionToken;

    public EditPasswordRequest(String username, String password,String sessionToken) {
        this.username = username;
        this.password = password;
        this.sessionToken = sessionToken;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}







