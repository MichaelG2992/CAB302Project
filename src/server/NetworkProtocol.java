package server;

import java.sql.SQLException;

/**
 *
 */
public class NetworkProtocol  {


    private final int CURRENT_BILLBOARD_REQUEST = 0;
    private final int LOGIN_REQUEST = 1;


    /**
     * @param protocol
     * @return
     * @throws SQLException
     */
    public int Request(Object protocol) throws SQLException {


        if (protocol instanceof LoginRequest){
            return LOGIN_REQUEST;

        }
        else if (protocol instanceof BillboardRequest) {
            return CURRENT_BILLBOARD_REQUEST;

        }
        else{
            return -1;
        }



    }



}
