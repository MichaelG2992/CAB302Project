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
    private final int EDIT_PASSWORD = 5;
    private final int DELETE_USERS = 6;
    private final int DELETE_BILLBOARD = 7;
    private final int CREATE_SCHEDULE = 8;
    private final int LIST_SCHEDULES = 9;
    private final int CREATE_USER = 10;
    private final int CHANGE_PERMISSIONS = 11;
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
        else if (protocol instanceof EditPasswordRequest){
            return EDIT_PASSWORD;
        }
        else if (protocol instanceof DeleteUserRequest){
            return DELETE_USERS;
        }
        else if ( protocol instanceof DeleteBillboardRequest){
            return DELETE_BILLBOARD;
        }
        else if ( protocol instanceof ScheduleBillboard){
            return CREATE_SCHEDULE;
        }
        else if ( protocol instanceof  ListScheduleRequest){
            return LIST_SCHEDULES;
        }
        else if (protocol instanceof CreateUser){
            return CREATE_USER;
        }
        else if (protocol instanceof ChangePermissions){
            return  CHANGE_PERMISSIONS;
        }
        else{
            return -1;
        }



    }



}
