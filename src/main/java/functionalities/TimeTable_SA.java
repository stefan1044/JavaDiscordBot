package functionalities;

import database.Database;
import database.Lecture;
import database.StudentPreference;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static java.lang.Math.exp;

public class TimeTable_SA {
    public static String run() {
//        Connection con = Database.getConnection();
//        String sql = "SELECT * FROM students";
//        try {
//            Statement statement = con.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String surname = resultSet.getString("surname");
//                int year = resultSet.getInt("id_year");
//                int group = resultSet.getInt("id_group");
//                String nrMat = resultSet.getString("nr_matricol");
//                System.out.println(id + " " + name + " " + surname + " " + year  + " " + group  + " " + nrMat);
//            }
//        } catch (SQLException e) {
//            System.err.println(e);
//        }
        int nrClassroom = Database.getNrClassrooms();
        int nrTimeSlots = Database.getNrTimeslots();
        List<Lecture> lectures = Database.getLectures();
        Map<Integer, List<StudentPreference>> studentPreferences = Database.getGroupPreferences();
//        int nrClassroom = 3;
//        int nrTimeSlots = 5;
//        List<Lecture> lectures = new ArrayList<>();
//        lectures.add(new Lecture(1, 1, 1));
//        lectures.add(new Lecture(1, 1, 2));
//        lectures.add(new Lecture(1, 1, 3));
//        lectures.add(new Lecture(1, 1, 4));
//        lectures.add(new Lecture(1, 1, 5));
//
//
//        Map<Integer, List<StudentPreference>> studentPreferences = new HashMap<>();     //grupa -> preferinte
//        List<StudentPreference> list = new ArrayList<>();                 //materie -> ora
//        list.add(new StudentPreference(1, 2));
//        list.add(new StudentPreference(1, 4));
//        list.add(new StudentPreference(1, 4));
//        list.add(new StudentPreference(1, 4));
//        list.add(new StudentPreference(1, 4));
//        list.add(new StudentPreference(2, 2));
//        list.add(new StudentPreference(3, 0));
//        list.add(new StudentPreference(4, 1));
//        list.add(new StudentPreference(5, 2));
//        list.add(new StudentPreference(1, 4));
//        studentPreferences.put(1, list);
//
//
        int[][] rez = simulated_annealing(0.995, nrClassroom, nrTimeSlots, lectures, studentPreferences);
//        System.out.print("Clase:     ");
//        for (int i = 0; i < rez.length; i++) {
//            for (int j = 0; j < rez[0].length; j++)
//                System.out.print(rez[i][j] + " ");
//            System.out.println();
//            System.out.print("Intervale: ");
//        }
        return interpretResults(rez, lectures);
    }

    private static String interpretResults(int[][] rez, List<Lecture> lectures) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < rez[0].length; i++) {
            int idGrupa = i / lectures.size() + 1;
            int idMaterie = i + 1;
            int idTimeSlot = rez[1][i];
            int idClassroom = rez[0][i];

            StringBuilder timeslot = new StringBuilder();
            if ((idTimeSlot - 1) / 6 == 0)
                timeslot.append("Monday");
            else if ((idTimeSlot - 1) / 6 == 1)
                timeslot.append("Tuesday");
            else if ((idTimeSlot - 1) / 6 == 2)
                timeslot.append("Wednesday");
            else if ((idTimeSlot - 1) / 6 == 3)
                timeslot.append("Thursday");
            else if ((idTimeSlot - 1) / 6 == 4)
                timeslot.append("Firday");
            if (idTimeSlot != 6) {
//                timeslot.append(idTimeSlot % 6);
                int startHour = 8 + (((idTimeSlot - 1) % 6)) * 2;
                int endHour = 8 + ((idTimeSlot - 1) % 6 + 1) * 2;
                timeslot.append(" " + startHour + " - " + endHour);
            }
            else {
                timeslot.append(" 18 - 20 ");
            }
//                timeslot.append("6");

            String grupa = Database.getGroupNameFromId(idGrupa);
            String materie = Database.getSubjectNameFromId(idMaterie);
            String classroom = Database.getClassNameFromId(idClassroom);
            message.append("Grupa: ").append(grupa).append(" Materia: ").append(materie).append(" Clasa: ").append(classroom).append(" Ora: " + timeslot).append("\n");
        }
        return message.toString();
    }

    static int[][] generate(int nrLecture, int nrClassroom, int nrTimeSlots) {
        Random rand = new Random();
        int[] classrooms = new int[nrLecture];
        int[] timeSlots = new int[nrLecture];
        int[][] assignment = new int[2][nrLecture];
//        System.out.println("nr lectures " + nrLecture);
        for (int i = 0; i < nrLecture; i++) {
//            System.out.println("classroom este: " + nrClassroom + " timeslots este " + nrTimeSlots);
            classrooms[i] = rand.nextInt(nrClassroom);
            timeSlots[i] = rand.nextInt(nrTimeSlots);
//            System.out.println(classrooms[i] + " " + timeSlots[i]);
        }
        assignment[0] = Arrays.copyOf(classrooms, classrooms.length);
        assignment[1] = Arrays.copyOf(timeSlots, timeSlots.length);

//        assignment[0] = classrooms;
//        assignment[1] = timeSlots;
//        System.out.println("HERE");
//        for(var a: assignment){
//            for(var b:a){
//                System.out.println(b);
//            }
//        }
        return assignment;
    }

    static double fitness(int[][] assignment, List<Lecture> lectures, Map<Integer, List<StudentPreference>> studentPreference) {
        int fit = 0;
        for (int i = 0; i < lectures.size(); i++) {
            if (studentPreference == null || studentPreference.isEmpty())
                break;
            for (StudentPreference pref : studentPreference.getOrDefault(lectures.get(i).idGroup, Collections.emptyList())) {
                if (pref.idSubject != lectures.get(i).idSubject)
                    continue;
                if (pref.idTimeSlot != assignment[1][i])
                    fit++;
            }
        }
        int invalidPenalty = 1;
        for (var l : studentPreference.values())
            invalidPenalty += l.size();
        for (int i = 0; i < assignment[0].length - 1; i++) {
            for (int j = i + 1; j < assignment[0].length; j++) {
                if (assignment[0][i] == assignment[0][j] && assignment[1][i] == assignment[1][j]                    //aceeasi clasa ocupata la aceeasi ora
                        || lectures.get(i).idProfessor == lectures.get(j).idProfessor && assignment[1][i] == assignment[1][j]  //profesor la 2 ore diferite deodata
                        || lectures.get(i).idGroup == lectures.get(j).idGroup && assignment[1][i] == assignment[1][j])      //grupa la 2 ore diferite deodata
                    fit += invalidPenalty;
            }
        }
        return fit;
    }

    static int[][] neighbour(int[][] assignment, int nrClassroom, int nrTimeSlots) {
//        for(var mem: assignment){
//            for (var mem2: mem){
////                System.out.println(mem2);
//            }
//        }
        int[][] localAssignment = new int[assignment.length][assignment[0].length];
        for (int i = 0; i < assignment.length; i++)
            localAssignment[i] = Arrays.copyOf(assignment[i], assignment[0].length);

        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
//                System.out.println(localAssignment[0].length);
            int randomLecture = rand.nextInt(localAssignment[0].length);
            if (Math.random() < 0.5) {          //schimb o sala
                int newClassroom = rand.nextInt(nrClassroom);
                while (newClassroom == localAssignment[0][randomLecture])
                    newClassroom = rand.nextInt(nrClassroom);
                localAssignment[0][randomLecture] = newClassroom;
            } else {                              //schimb un interval orar
                int newTimeSlot = rand.nextInt(nrTimeSlots);
                while (newTimeSlot == localAssignment[1][randomLecture])
                    newTimeSlot = rand.nextInt(nrTimeSlots);
                localAssignment[1][randomLecture] = newTimeSlot;
            }
        }
        return localAssignment;
    }

    public static int[][] simulated_annealing(double cooldown, int nrClassroom, int nrTimeSlots, List<Lecture> lectures, Map<Integer, List<StudentPreference>> studentPreference) {
        Instant start = Instant.now();
        double temp = 100;

        int[][] assignment = generate(lectures.size(), nrClassroom, nrTimeSlots);
        double current = fitness(assignment, lectures, studentPreference);
        double minim = 1e-9;


        while (temp > minim) {
            int t = 0;
            while (t < 10000) {
                int[][] neighbour = neighbour(assignment, nrClassroom, nrTimeSlots);
                double aux = fitness(neighbour, lectures, studentPreference);
                if (aux < current) {
                    assignment = neighbour;
                    current = aux;
                }
                else if (Math.random() < exp(-Math.abs(aux - current) / temp)) {
                    assignment = neighbour;
                    current = aux;
                }
                t++;
            }
            temp *= cooldown;
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long durationMillis = duration.toMillis();

        String path = "Orar UAIC";
        try (PrintWriter printWriter = new PrintWriter(path)) {
            for (int i = 0; i < assignment.length; i++) {
                for (int j = 0; j < assignment[0].length; j++)
                    printWriter.print(assignment[i][j] + " ");
                printWriter.println();
            }
            printWriter.println("Duration: " + durationMillis + " millis.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return assignment;
    }
}