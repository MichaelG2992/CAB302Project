package server;

import java.io.Serializable;

public class DeleteUserRequest implements Serializable {


    private String username;


    public DeleteUserRequest(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }
}
