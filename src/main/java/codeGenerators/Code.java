package codeGenerators;

import java.io.Serializable;

public class Code implements Serializable {
    private final String code;
    private final String firstname;
    private final String lastname;
    private final int year;
    private final char group;
    private final int groupNumber;

    public Code(String code, String firstname, String lastname, int year, char group, int groupNumber){
        this.code = code;
        this.firstname = firstname;
        this.lastname = lastname;
        this.year = year;
        this.group = group;
        this.groupNumber = groupNumber;
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
    
    public char getGroup() {
        return group;
    }
    
    public int getGroupNumber() {
        return groupNumber;
    }
    
    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", year=" + year +
                ", group='" + group + '\'' +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
