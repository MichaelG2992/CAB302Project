package server;

import java.sql.SQLException;

/**
 *
 */
public class NetworkProtocol  {


    private final int CURRENT_BILLBOARD_REQUEST = 0;
    private final int LOGIN_REQUEST = 1;
    private final int CREATE_BILLBOARD = 2;
    private final int LIST_BILLBOARDS = 3;
    private final int LIST_USERS = 4;


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
        else if (protocol instanceof ListBillboardsRequest) {
            return LIST_BILLBOARDS;
        }

        else if (protocol instanceof Billboard) {
            return CREATE_BILLBOARD;
        }

        else if (protocol instanceof UserRequest){
                return LIST_USERS;

        }
        else{
            return -1;
        }



    }



}
