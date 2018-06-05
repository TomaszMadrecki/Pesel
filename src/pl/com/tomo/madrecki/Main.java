package pl.com.tomo.madrecki;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        showInfoAboutPesel();
    }

    private static int[] intPeselArray(String pesel) {
        int[] peselArray = new int[pesel.length()];
        for (int i = 0; i < peselArray.length; i++) {
            peselArray[i] = Integer.parseInt(pesel.substring(i, i + 1));
        }
        return peselArray;
    }

    private static boolean checkPesel(String pesel) {
        int[] vagNum = new int[10];
        vagNum[0] = 1;
        vagNum[1] = 3;
        vagNum[2] = 7;
        vagNum[3] = 9;
        vagNum[4] = 1;
        vagNum[5] = 3;
        vagNum[6] = 7;
        vagNum[7] = 9;
        vagNum[8] = 1;
        vagNum[9] = 3;

        if (pesel.matches("[0-9]{11}")) {
            int controlSum = 0;
            int temp;
            for (int i = 0; i < vagNum.length; i++) {
                temp = vagNum[i] * intPeselArray(pesel)[i];
                controlSum = controlSum + temp;
            }
            controlSum %= 10;
            controlSum = 10 - controlSum;
            controlSum %= 10;
            return controlSum == intPeselArray(pesel)[10];
        } else {
            return false;
        }
    }

    private static String dateOfBirth(String pesel) {
        final String OLD_FORMAT = "yymmdd";
        final String NEW_FORMAT = "dd-mm-yyyy";
        String oldDateString = pesel.substring(0, 6);
        String newDateString;
        SimpleDateFormat dateFormat = new SimpleDateFormat(OLD_FORMAT);
        Date date = null;
        try {
            date = dateFormat.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat.applyPattern(NEW_FORMAT);
        newDateString = dateFormat.format(date);

        return newDateString;
    }

    private static String sex(String pesel) {
        if (intPeselArray(pesel)[9] % 2 == 0) {
            return "You're a woman";
        } else {
            return "You're a man";
        }
    }

    private static void showInfoAboutPesel() {
        Scanner scanner = new Scanner(System.in);
        String pesel = "";
        System.out.println("Insert your PESEL");
        while (!checkPesel(pesel)) {
            pesel = scanner.nextLine();
            if (checkPesel(pesel)) {
                System.out.println("Pesel is valid");
                System.out.println("You're born at: " + dateOfBirth(pesel));
                System.out.println(sex(pesel));
            } else {
                System.err.println("Pesel is not valid! Try again!");
            }
        }
    }
}
