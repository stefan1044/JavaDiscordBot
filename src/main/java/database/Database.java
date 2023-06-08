package database;

import codeGenerators.Code;
import preferences.Preference;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "calin";
    private static final String PASSWORD = "calin";
    private static Connection connection = null;
    private Database() {}
    public static Connection getConnection() {
        if (connection == null)
            createConnection();
        return connection;
    }
    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    public static void closeConnection() {
        if (connection != null)
        {
            try{
                connection.close();
            }
            catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public static int getNrClassrooms() {
        Connection con = Database.getConnection();
        String sql = "SELECT COUNT(*) FROM classrooms";
        int count = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                count = resultSet.getInt(1);

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return count;
    }

    public static int getNrTimeslots() {
        Connection con = Database.getConnection();
        String sql = "SELECT COUNT(*) FROM timeslots";
        int count = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                count = resultSet.getInt(1);

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return count;
    }

    public static List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();
        Connection con = Database.getConnection();
        String sql = "SELECT * from lectures";

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idProf = resultSet.getInt("id_professor");
                int idGroup = resultSet.getInt("id_group");
                int idSubject = resultSet.getInt("id_subject");
                lectures.add(new Lecture(idProf, idGroup, idSubject));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return lectures;
    }

    public static List<Integer> getGroups() {
        List<Integer> groups = new ArrayList<>();
        Connection con = Database.getConnection();
        String sql = "SELECT id from groups";

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                groups.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return groups;
    }

    public static Map<Integer, List<StudentPreference>> getGroupPreferences() {
        Map<Integer, List<StudentPreference>> groupPref = new HashMap<>();
        Connection con = Database.getConnection();
        String sql = "SELECT * from student_preferences";

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idSubject = resultSet.getInt("id_subject");
                int idTimeslot = resultSet.getInt("id_timeslot");
                int idStudent = resultSet.getInt("id_student");
                List<StudentPreference> studentsPref = new ArrayList<>();

                //get student group from student id
                String sql2 = "SELECT id_group FROM students WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql2);
                preparedStatement.setInt(1, idStudent);
                ResultSet studentGroup = preparedStatement.executeQuery();
                if (studentGroup.next()) {
                    int idGroup = studentGroup.getInt("id_group");
                    if (groupPref.containsKey(idGroup)) {
                        studentsPref = groupPref.get(idGroup);
                        studentsPref.add(new StudentPreference(idSubject, idTimeslot));
                        groupPref.put(idGroup, studentsPref);
                    }
                    else {
                        studentsPref.add(new StudentPreference(idSubject, idTimeslot));
                        groupPref.put(idGroup, studentsPref);
                    }
                }

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return groupPref;
    }

    public static void insertStudentPreference(int idStudent, int idSubject, int idTimeslot) {
        try {
            Connection con = Database.getConnection();
            String sql = "INSERT INTO student_preferences (id_subject, id_timeslot, id_student) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, idSubject);
            preparedStatement.setInt(2, idTimeslot);
            preparedStatement.setInt(3, idStudent);

            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void insertStudent(String name, String surname, int idYear, char groupLetter, int groupNumber, String nrMatricol) {
        try {
            Connection con = Database.getConnection();
            String sql = "INSERT INTO students (name, surname, id_year, id_group, nr_matricol)" +
                    "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3,idYear);

            String groupName = String.valueOf(groupLetter);
            groupName += groupNumber;

            String findGroupId = "SELECT id FROM groups WHERE name = ?";
            PreparedStatement findId = con.prepareStatement(findGroupId);
            findId.setString(1, groupName);
            ResultSet groupId = findId.executeQuery();
            int idGroup = 0;
            if (groupId.next()) {
                idGroup = groupId.getInt("id");
            }

            preparedStatement.setInt(4, idGroup);
            preparedStatement.setString(5, nrMatricol);

            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static List<Code> getAllStudents() {
        List<Code> students = new ArrayList<>();
        Connection con = Database.getConnection();
        String sql = "SELECT * from students";

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idStudent = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int idYear = resultSet.getInt("id_year");
                int idGroup = resultSet.getInt("id_group");
                String nrMatricol = resultSet.getString("nr_matricol");

                String getGroupName = "SELECT name FROM groups WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(getGroupName);
                preparedStatement.setInt(1, idGroup);
                ResultSet groupName = preparedStatement.executeQuery();
                char groupLetter = 'a';
                int groupNumber = 0;
                if (groupName.next()) {
                    String group = groupName.getString("name");
                    groupLetter = group.charAt(0);
                    groupNumber = group.charAt(1);
                }

                students.add(new Code(nrMatricol, surname, name, idYear, groupLetter, groupNumber));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return students;
    }

    public static int getIdFromNrMat(String nrMatricol) {
        Connection con = Database.getConnection();
        String sql = "SELECT id FROM students WHERE nr_matricol = ?";
        int id = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, nrMatricol);
            ResultSet studentId = preparedStatement.executeQuery();
            if (studentId.next())
                id = studentId.getInt("nr_matricol");

        } catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }

//    public static List<Preference> getStudentPreferences() {
//        List<Preference> listPreference = new ArrayList<>();
//    }
    public static String getGroupNameFromId(int id) {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM groups WHERE id = ?";
        String name = "";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet groupId = preparedStatement.executeQuery();
            if (groupId.next())
                name = groupId.getString("name");

        } catch (SQLException e) {
            System.err.println(e);
        }
        return name;
    }

    public static String getSubjectNameFromId(int id) {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM subjects WHERE id = ?";
        String name = "";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet subjectId = preparedStatement.executeQuery();
            if (subjectId.next())
                name = subjectId.getString("name");

        } catch (SQLException e) {
            System.err.println(e);
        }
        return name;
    }

    public static String getClassNameFromId(int id) {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM classrooms WHERE id = ?";
        String name = "";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet classId = preparedStatement.executeQuery();
            if (classId.next())
                name = classId.getString("name");

        } catch (SQLException e) {
            System.err.println(e);
        }
        return name;
    }
}

