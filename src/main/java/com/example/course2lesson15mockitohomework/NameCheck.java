package com.example.course2lesson15mockitohomework;


import org.apache.commons.lang3.StringUtils;

public class NameCheck {

    private final String firstName;
    private final String lastName;
    private int code;
    private String message;
    private final boolean ok;

    public NameCheck(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

            /*
        0 - OK
        1 - both null or have invalid symbols
        2 - first OK, last null or have invalid symbols
        3 - first null or have invalid symbols, last OK
         */

        boolean firstNameOK = StringUtils.isAlpha(firstName);

        boolean lastNameOK = StringUtils.isAlpha(lastName);

        if (firstNameOK & lastNameOK) {
            code = 0;
            message = "OK";
        }
        if (!firstNameOK & !lastNameOK) {
            code = 1;
            message = "first and last names are missing or containing invalid symbols";
        }
        if (firstNameOK & !lastNameOK) {
            code = 2;
            message = "last name is missing or containing invalid symbols";
        }
        if (!firstNameOK & lastNameOK) {
            code = 3;
            message = "first name is missing or containing invalid symbols";
        }
        ok = (code == 0);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return ok;
    }
}
