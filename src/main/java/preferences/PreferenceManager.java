package preferences;

import codeGenerators.Code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferenceManager {
    
    public static void writePreferenceToDatabase(Preference preference, Code code){
        // student code e nr matricol
        String studentCode = code.getCode();
        
        
        //fa aici querryu sa bage un preference in db, ai nr marticol il bagi dupa ala
    }
    
    public static Map<Code, List<Preference>> getPreferences(){
        //faci un query sa sa ia toate preferenceurile, le initializezi ca un obiect de tip Preference si le pui in mapu asta
        // vezi ca pt fiecare student tre sa initializezi si lista
        Map<Code, List<Preference>> preferenceList = new HashMap<>(0);
        // aici scrii codu
        
        
        return preferenceList;
    }
}
