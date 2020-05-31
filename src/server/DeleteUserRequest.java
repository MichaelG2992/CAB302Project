package server;

import java.io.Serializable;

public class DeleteUserRequest implements Serializable {


    private String username;
    private String sessionToken;


    public DeleteUserRequest(String username, String sessionToken) {
        this.username = username;
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getUsername() {
        return username;
    }
}

