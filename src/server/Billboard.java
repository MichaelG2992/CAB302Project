package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Billboard implements Serializable {

    private String name;
    private String xmlFile;
    private String creator;
    private String sessionToken;

    /**
     *
     */
    public Billboard(){
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    public void setFile(String file){this.xmlFile = file;}

    public String getFile(){return xmlFile;}

    public void setCreator(String creator){this.creator = creator;}

    public String getCreator(){return creator;}

    public void setSessionToken(String sessionToken){this.sessionToken = sessionToken;}

    public String getSessionToken(){return sessionToken;}


}
