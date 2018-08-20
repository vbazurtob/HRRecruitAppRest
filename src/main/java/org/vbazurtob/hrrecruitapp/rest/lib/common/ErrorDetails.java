package org.vbazurtob.hrrecruitapp.rest.lib.common;

import java.util.Objects;

public class ErrorDetails {
    private String message;
    private String details;

    public ErrorDetails(){}

    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setdetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getdetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetails that = (ErrorDetails) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, details);
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
