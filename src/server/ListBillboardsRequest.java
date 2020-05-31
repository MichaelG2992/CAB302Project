package server;

import java.io.Serializable;

public class ListBillboardsRequest implements Serializable {

    private String sessionToken;

    public ListBillboardsRequest(String sessionToken){
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
