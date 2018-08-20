package org.vbazurtob.hrrecruitapp.rest.model.response;

import java.util.Objects;

public class RestServiceStatus {

    private String status;
    private String message;

    public RestServiceStatus(){}

    public RestServiceStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestServiceStatus that = (RestServiceStatus) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }

    @Override
    public String toString() {
        return "RestServiceStatus{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
