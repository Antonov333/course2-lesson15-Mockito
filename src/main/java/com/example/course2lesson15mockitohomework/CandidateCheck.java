package com.example.course2lesson15mockitohomework;


import org.apache.commons.lang3.StringUtils;

public class CandidateCheck {

    private final String firstName;
    private final String lastName;
    private final Integer deptId;
    private final Integer salary;
    private final boolean ok;
    public boolean isFirstNameOk;
    public boolean isLastNameOk;
    public boolean isSalaryOk;
    public boolean isDeptIdOk;
    private int code;
    private String message;

    public CandidateCheck(String firstName, String lastName, Integer deptId, Integer salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deptId = deptId;
        this.salary = salary;

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

        boolean deptIdOk = (deptId != null) & (deptId > 0);
        if (!deptIdOk) {
            code = code + 10; // 1x - deptId is invalid
            message = message.concat(" ! deptId is invalid ! ");
        }

        boolean salaryIsOk = (salary != null) & (salary > 0);
        if (!deptIdOk) {
            code = code + 100; // 1xx - salary is invalid
            message = message.concat(" ! salary is invalid ! ");
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
