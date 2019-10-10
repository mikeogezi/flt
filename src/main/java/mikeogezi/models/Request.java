package mikeogezi.models;

import javax.validation.constraints.NotNull;

public class Request {

    @NotNull(message = "token cannot be null or empty")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
