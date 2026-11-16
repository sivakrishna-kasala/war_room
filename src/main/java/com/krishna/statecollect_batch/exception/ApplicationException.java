package com.krishna.statecollect_batch.exception;

public class ApplicationException extends RuntimeException{

    private Integer status;
    private String message;
    private String description;


    public ApplicationException(Integer status,String message){
        super(message);
        this.status=status;
        this.message=message;
    }

    public ApplicationException(Integer status,String message,String description){
        super(message+" "+description);
        this.status=status;
        this.message=message;
        this.description=description;
    }

    public ApplicationException(String message,String description){
        super(message+"  "+description);
        this.message=message;
        this.description=description;
    }

    public ApplicationException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.description = null;
    }

    public ApplicationException(Integer status,Throwable cause){
        super(cause);
        this.status=status;
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
