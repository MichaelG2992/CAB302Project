package server;

public class UserRequest extends Request {

    private String sessionToken;

    public UserRequest(String sessionToken){
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
