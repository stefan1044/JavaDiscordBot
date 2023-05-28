package codeGenerators;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class StaticCodesManager {

    private static final String pathToCodesFile = ".\\src\\main\\resources\\codes";
    private static ArrayList<Code> codes = null;


    private static void writeCodesToFile() {
        try (
                FileOutputStream fout = new FileOutputStream(StaticCodesManager.pathToCodesFile, false);
                ObjectOutputStream oos = new ObjectOutputStream(fout)
        ) {
            oos.writeObject(StaticCodesManager.codes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void addCodeToList(Code code) {
        if (StaticCodesManager.codes == null){
            StaticCodesManager.codes = new ArrayList<>();
        }
        StaticCodesManager.codes.add(code);
        writeCodesToFile();
    }

    public static void loadCodesList() {
        ArrayList<Code> codesList;

        try (
                FileInputStream fin = new FileInputStream(StaticCodesManager.pathToCodesFile);
                ObjectInputStream ois = new ObjectInputStream(fin)
        ) {
            codesList = (ArrayList<Code>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        StaticCodesManager.codes = codesList;
    }

    public static ArrayList<Code> getCodes(){
        return Objects.requireNonNullElseGet(StaticCodesManager.codes, ArrayList::new);
    }
}
