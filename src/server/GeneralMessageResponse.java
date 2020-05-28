package server;

import java.io.Serializable;

public class GeneralMessageResponse implements Serializable {
    private String message;



    public GeneralMessageResponse(String message){
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
