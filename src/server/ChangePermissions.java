package server;

import java.io.Serializable;

public class ChangePermissions  implements Serializable {

    private String username;
    private boolean createBillboards,
            editAllBillboards,
            scheduleBillboards,
            editUsers;


    public ChangePermissions(String name, boolean createBillboards, boolean editAllBillboards, boolean scheduleBillboards,
                boolean editUsers){
        username = name;
        this.createBillboards = createBillboards;
        this.editAllBillboards = editAllBillboards;
        this.scheduleBillboards = scheduleBillboards;
        this.editUsers = editUsers;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return this.username;
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


