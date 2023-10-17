package edu.wkd.adminappbanghangonline.model.response;

import java.util.List;

import edu.wkd.adminappbanghangonline.model.obj.User;


public class UserResponse {

    private boolean success;
    private String message;
    private List<User> result;

    public UserResponse() {
    }

    public UserResponse(boolean success, String message, List<User> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }


    @Override
    public String toString() {
        return "ProductResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
}
