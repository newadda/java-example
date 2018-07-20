package org.onecellboy.web.api.response.error;

import java.util.LinkedList;
import java.util.List;

public class ApiError {

    private int status;

    private String message;

    private List<SubError> errors;

    public ApiError() {
        this.errors = new LinkedList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubError> getErrors() {
        return errors;
    }

    public void setErrors(List<SubError> errors) {
        this.errors = errors;
    }
}
