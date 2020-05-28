package server;

import java.io.Serializable;

public class User  implements Serializable {

    private String username;
    private String password;
    private String permissions;

    public User(){
    }

    public User(String name, String level){
        username = name;
        permissions = level;
    }



    public String getUserName(){return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}

