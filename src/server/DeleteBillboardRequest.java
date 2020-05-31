package server;

import java.io.Serializable;

public class DeleteBillboardRequest implements Serializable {


    private String name;


    public DeleteBillboardRequest(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
