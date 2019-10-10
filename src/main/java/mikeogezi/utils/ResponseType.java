package mikeogezi.utils;

import mikeogezi.models.Response;

public enum ResponseType {
    SUCCESS,
    FAILURE,
    ERROR,
    VALIDATION_ERROR;

    public boolean isSuccess (Response response) {
        return SUCCESS.equals(response.getType());
    }

    public boolean isError (Response response) {
        return ERROR.equals(response.getType());
    }

    public boolean isFailure (Response response) {
        return FAILURE.equals(response.getType());
    }

    public boolean isValidationError (Response response) {
        return VALIDATION_ERROR.equals(response.getType());
    }

    public String getResponseCode () {
        switch (this) {
            case SUCCESS:
                return "00";
            case FAILURE:
                return "01";
            case ERROR:
                return "02";
            case VALIDATION_ERROR:
                return "03";
        }

        return null;
    }

}
