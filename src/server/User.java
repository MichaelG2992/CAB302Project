package server;

import java.io.Serializable;

public class User  implements Serializable {

    private String username;
    private String password;
    private boolean createBillboards,
                    editAllBillboards,
                    scheduleBillboards,
                    editUsers;
    public User(){
    }

    public User(String username){
        this.username = username;
    }

    public User(String name, boolean createBillboards, boolean editAllBillboards, boolean scheduleBillboards,
                boolean editUsers){
        username = name;
        this.createBillboards = createBillboards;
        this.editAllBillboards = editAllBillboards;
        this.scheduleBillboards = scheduleBillboards;
        this.editUsers = editUsers;
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

    public void setCreateBillboards(boolean value){createBillboards = value;}
    public boolean getCreateBillboards(){return createBillboards;}

    public void setEditAllBillboards(boolean value){editAllBillboards = value;}
    public boolean getEditAllBillboards(){return editAllBillboards;}

    public void setScheduleBillboards(boolean value){scheduleBillboards = value;}
    public boolean getScheduleBillboards(){return scheduleBillboards;}

    public void setEditUsers(boolean value){editUsers = value;}
    public boolean getEditUsers(){return editUsers;}

    public String toString(){return username;}

}

