package com.example.course2lesson15mockitohomework;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongNameFormatException extends RuntimeException {

    public WrongNameFormatException() {
        super("WrongNameFormatException");
    }

    public WrongNameFormatException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }

}
