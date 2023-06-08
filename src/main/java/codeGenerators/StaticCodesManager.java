package codeGenerators;

import java.util.ArrayList;
import java.util.Objects;

public class StaticCodesManager {

    private static final String pathToCodesFile = ".\\src\\main\\resources\\codes";
    private static ArrayList<Code> codes = null;


    // fa tu aici cu baza de date
    private static void writeCodesToFile() {
//        try (
//                FileOutputStream fileOutputStream = new FileOutputStream(StaticCodesManager.pathToCodesFile, false);
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
//        ) {
//            objectOutputStream.writeObject(StaticCodesManager.codes);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

    public static void addCodeToList(Code code) {
        if (StaticCodesManager.codes == null){
            StaticCodesManager.codes = new ArrayList<>();
        }
        StaticCodesManager.codes.add(code);
        writeCodesToFile();
    }

    public static void loadCodesList() {
//        ArrayList<Code> codesList;
//
//        try (
//                FileInputStream fileInputStream = new FileInputStream(StaticCodesManager.pathToCodesFile);
//                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
//        ) {
//            codesList = (ArrayList<Code>) objectInputStream.readObject();
//        } catch (IOException | ClassNotFoundException | ClassCastException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//        StaticCodesManager.codes = codesList;
    }

    public static ArrayList<Code> getCodes(){
        return Objects.requireNonNullElseGet(StaticCodesManager.codes, ArrayList::new);
    }
}
