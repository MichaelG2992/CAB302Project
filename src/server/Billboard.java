package server;

import java.io.Serializable;

public class Billboard implements Serializable {

    private String name;

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
}
