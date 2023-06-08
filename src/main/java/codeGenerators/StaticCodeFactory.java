package codeGenerators;

import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class StaticCodeFactory {

    private static final Faker faker = new Faker();
    @NotNull
    public static Code getRandomCode(){

        ArrayList<Code> codes = StaticCodesManager.getCodes();
        String newCode = "RSL" + StaticCodeFactory.faker.code().ean8();
        String finalNewCode = newCode;
        boolean codeIsAlreadyUsed = codes.stream().anyMatch(code -> code.getCode().equals(finalNewCode));
        while(codeIsAlreadyUsed){
            newCode = "RSL" + StaticCodeFactory.faker.code().ean8();
            String finalNewCode1 = newCode;
            codeIsAlreadyUsed = codes.stream().anyMatch(code -> code.getCode().equals(finalNewCode1));
        }

        String firstname = StaticCodeFactory.faker.name().firstName();
        String lastname = StaticCodeFactory.faker.name().lastName();
        int year = StaticCodeFactory.faker.number().numberBetween(1, 4);
        char group = StaticCodeFactory.faker.number().numberBetween(1, 3) == 1 ? 'A' : 'B';
        int groupNumber = StaticCodeFactory.faker.number().numberBetween(1,7);

        return new Code(newCode, firstname, lastname, year, group, groupNumber);
    }

}
