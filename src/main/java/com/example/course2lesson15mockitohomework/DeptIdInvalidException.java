package com.example.course2lesson15mockitohomework;

public class DeptIdInvalidException extends RuntimeException {
    public DeptIdInvalidException() {
    }

    public DeptIdInvalidException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "DeptIdInvalidException{} " + super.toString();
    }
}
