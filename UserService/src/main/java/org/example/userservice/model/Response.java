package org.example.userservice.model;

public class Response {
    private String status;
    private Object data;

    private Response(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public static Response success(Object data) {
        return new Response("success", data);
    }

    public static Response error(String message) {
        return new Response("error", message);
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}