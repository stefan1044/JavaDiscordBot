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
        Database.insertStudentPreference(idStudent, preference.getClassId(), preference.getTimeslotId());
    }
    
//    public static Map<Integer, List<Preference>> getPreferences(){
//        //faci un query sa sa ia toate preferenceurile, le initializezi ca un obiect de tip Preference si le pui in mapu asta
//        // vezi ca pt fiecare student tre sa initializezi si lista
//        Map<Integer, List<Preference>> preferenceList = new HashMap<>(0);
//
//        // aici scrii codu
//
//
//        return preferenceList;
//    }
}
