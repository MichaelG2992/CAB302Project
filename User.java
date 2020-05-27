package controlPanel;

public class User {
    private String username;
    private String permissions;

    // Default empty constructor
    public User(){}

    public User(String name, String level){
        username = name;
        permissions = level;
    }

    // Set
    public void setUsername(String name){username = name;}
    public void setPermissions(String level){permissions = level;}

    // Get
    public String getUsername(){return username;}
    public String getPermissions(){return permissions;}


    public String toString(){
        return username;
    }

}
