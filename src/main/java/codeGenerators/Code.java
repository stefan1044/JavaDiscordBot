package codeGenerators;

import java.io.Serializable;

public class Code implements Serializable {
    private final String code;
    private final String firstname;
    private final String lastname;
    private final int year;

    Code(String code, String firstname, String lastname, int year){
        this.code = code;
        this.firstname = firstname;
        this.lastname = lastname;
        this.year = year;
    }

    @Override
    public String toString(){
        return "{\n" +
                "    \"code\": \"" + this.code + "\",\n" +
                "    \"firstname\": \"" + this.firstname + "\",\n" +
                "    \"lastname\": \"" + this.lastname + "\",\n" +
                "    \"year\": " + this.year + "\n" +
                "}\n";
    }

    public String getCode() {
        return this.code;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getYear() {
        return year;
    }
}
