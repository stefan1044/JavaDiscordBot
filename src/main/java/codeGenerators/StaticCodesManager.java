package codeGenerators;

import java.util.ArrayList;
import java.util.Objects;
import database.Database;


public class StaticCodesManager {

    private static final String pathToCodesFile = ".\\src\\main\\resources\\codes";
    private static ArrayList<Code> codes = null;


    // fa tu aici cu baza de date
    public static void writeCodesToDatabase() {
        for (Code code : codes) {
            String firstName = code.getFirstname();
            String lastName = code. getLastname();
            String nrMatricol = code.getCode();
            int idYear = code.getYear();
            char groupLetter = code.getGroup();
            int groupNumber = code.getGroupNumber();
            Database.insertStudent(firstName, lastName, idYear, groupLetter, groupNumber, nrMatricol);
        }
    }

    public static void addCodeToList(Code code) {
        if (StaticCodesManager.codes == null){
            StaticCodesManager.codes = new ArrayList<>();
        }
        StaticCodesManager.codes.add(code);
//        writeCodesToDatabase();
    }

    public static void loadCodesList() {
        Database.getAllStudents();
    }

    public static ArrayList<Code> getCodes(){
        return Objects.requireNonNullElseGet(StaticCodesManager.codes, ArrayList::new);
    }
}
