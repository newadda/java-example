package org.onecellboy.web.api.response.error;

import java.util.LinkedList;
import java.util.List;

public class ApiErrorDto {
    private int status; //http status code
    private String reason;  // 에러 발생 이유 ex. InvalidParameter
    //private String reason_sub; // 자세한 이유 ex. IDNotFound
    private String user_message; // 유저에게 보여지는 메세지

    private List<Object> errors = new LinkedList<>();


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
/*
    public String getReason_sub() {
        return reason_sub;
    }

    public void setReason_sub(String reason_sub) {
        this.reason_sub = reason_sub;
    }
    */

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public List<Object> getErrors() {
        return errors;
    }
}
