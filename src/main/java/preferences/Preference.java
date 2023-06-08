package preferences;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class Preference implements Serializable {
    
    static public final String IS_VALID = "Preference updated!";
    static private final String WRONG_CLASS = "You have provided an invalid class!";
    static private final String WRONG_DAY = "You have provided an invalid day. Please select one of the given choices";
    static private final String WRONG_START_HOUR = "You have provided an invalid start hour. Please select one of the " +
            "given choices";
    static private final List<String> DAY_NAMES = List.of(new String[]{"monday", "tuesday", "wednesday", "thursday",
            "friday"});
    static private final List<Integer> START_HOURS = List.of(new Integer[]{8, 10, 12, 14, 16, 18});
    static private final List<String> FIRST_YEAR_CLASS_NAMES = List.of(new String[]{
            "Introduction to programming",
            "Data structures and algorithms",
            "Logic",
            "Mathematics",
            "Computer architecture and operating systems",
            "English 1"
    });
    static private final List<String> SECOND_YEAR_CLASS_NAMES = List.of(new String[]{
            "Computer networks",
            "Formal languages, automata and compilers",
            "Databases",
            "Graph algorithms",
            "Genetic algorithms",
            "English 3"
    });
    static private final List<String> THIRD_YEAR_CLASS_NAMES = List.of(new String[]{
            "Python programming",
            "Machine learning",
            "Artificial intelligence",
            "Information security",
            "Neural networks",
            "Introduction to mixed reality"
    });
    
    private final String day;
    private final int startHour;
    
    private final int year;
    
    public Preference(String className, String day, int startHour) {
        if (Preference.FIRST_YEAR_CLASS_NAMES.contains(className)) {
            this.year = 1;
        } else if (Preference.SECOND_YEAR_CLASS_NAMES.contains(className)) {
            this.year = 2;
        } else if (Preference.THIRD_YEAR_CLASS_NAMES.contains(className)) {
            this.year = 3;
        } else {
            this.year = -1;
        }
        this.day = day;
        this.startHour = startHour;
    }
    
    public static String checkValidity(@NotNull Preference preference) {
        if(preference.year == -1){
            return Preference.WRONG_CLASS;
        }
        if (!Preference.DAY_NAMES.contains(preference.day)) {
            return Preference.WRONG_DAY;
        }
        if (!Preference.START_HOURS.contains(preference.startHour)) {
            return Preference.WRONG_START_HOUR;
        }
        
        return Preference.IS_VALID;
    }
    
    @Override
    public String toString() {
        return "Preference{" +
                "day='" + day + '\'' +
                ", startHour=" + startHour +
                ", year=" + year +
                '}';
    }
}
