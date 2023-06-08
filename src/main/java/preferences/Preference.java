package preferences;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Preference implements Serializable {
    
    static public final String IS_VALID = "Preference updated!";
    static private final String WRONG_CLASS = "You have provided an invalid class!";
    static private final String WRONG_DAY = "You have provided an invalid day. Please select one of the given choices";
    static private final String WRONG_START_HOUR = "You have provided an invalid start hour. Please select one of the " +
            "given choices";
    static private final List<String> DAY_NAMES = List.of(new String[]{"monday", "tuesday", "wednesday", "thursday",
            "friday"});
    static private final List<Integer> START_HOURS = List.of(new Integer[]{1, 2, 3, 4, 5, 6});
    static private final Map<String, Integer> FIRST_YEAR_CLASS_NAMES = new HashMap<>() {{
        put("Introduction to programming", 1);
        put("Data structures and algorithms", 2);
        put("Logic", 3);
        put("Mathematics", 4);
        put("Computer architecture and operating systems", 5);
        put("English 1", 6);
    }};

    static private final Map<String, Integer> SECOND_YEAR_CLASS_NAMES = new HashMap<>() {{
        put("Computer networks", 7);
        put("Formal languages, automata and compilers", 8);
        put("Databases", 9);
        put("Graph algorithms", 10);
        put("Genetic algorithms", 11);
        put("English 3", 12);
    }};

    static private final Map<String, Integer> THIRD_YEAR_CLASS_NAMES = new HashMap<>() {{
        put("Python programming", 13);
        put("Machine learning", 14);
        put("Artificial intelligence", 15);
        put("Information security", 16);
        put("Neural networks", 17);
        put("Introduction to mixed reality", 18);
    }};
    
    private final String day;
    private final int startHour;
    private final int year;
    private final String className;
    
    public Preference(String className, String day, int startHour) {
        if (Preference.FIRST_YEAR_CLASS_NAMES.containsKey(className)) {
            this.year = 1;
        } else if (Preference.SECOND_YEAR_CLASS_NAMES.containsKey(className)) {
            this.year = 2;
        } else if (Preference.THIRD_YEAR_CLASS_NAMES.containsKey(className)) {
            this.year = 3;
        } else {
            this.year = -1;
        }
        this.className = className;
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

    public int getClassId() {
        if (year == 1)
            return FIRST_YEAR_CLASS_NAMES.get(className);
        else if (year == 2)
            return SECOND_YEAR_CLASS_NAMES.get(className);
        return SECOND_YEAR_CLASS_NAMES.get(className);
    }

    public int getTimeslotId() {
        return 6 * (Preference.DAY_NAMES.indexOf(this.day) - 1) + this.startHour;
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
