package org.onecellboy.web.api.response.error;

public class ApiInvalidParameterErrorDetailDto {
    private String reason;
    private String message;

    /*InvalidParameter*/
  //  private String invalid_location_type;  // parameter, header 등
    private String invalid_location; // parameter 이름
   // private String constraint_type; // Notnull , Email등 검증 실패 원인

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
/*
    public String getInvalid_location_type() {
        return invalid_location_type;
    }

    public void setInvalid_location_type(String invalid_location_type) {
        this.invalid_location_type = invalid_location_type;
    }*/

    public String getInvalid_location() {
        return invalid_location;
    }

    public void setInvalid_location(String invalid_location) {
        this.invalid_location = invalid_location;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
