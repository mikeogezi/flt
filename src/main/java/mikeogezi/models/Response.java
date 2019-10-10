package mikeogezi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mikeogezi.utils.ResponseType;

@JsonIgnoreProperties(value = { "type" })
public class Response {

    private ResponseType type;

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
        this.setResponseCode(type.getResponseCode());
        this.setResponseMessage(type.toString());
    }

    private String responseCode;

    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public boolean isSuccess () {
        return "00".equals(getResponseCode());
    }

}
