package preferences;

import codeGenerators.Code;
import database.Database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferenceManager {
    
    public static void writePreferenceToDatabase(Preference preference, Code code){
        String nrMatricol = code.getCode();
        int idStudent = Database.getIdFromNrMat(nrMatricol);
        System.out.println(nrMatricol);
        System.out.println(idStudent);
        Database.insertStudentPreference(idStudent, preference.getClassId(), preference.getTimeslotId());
    }
}
