import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static String[] readPlayersNames(Scanner scanner) {
        String[] playerNamesArray = new String[2];
        for (int i = 1; i <= playerNamesArray.length; i++) {
            System.out.println("\nМоля въведете име на ирач " + i);
            playerNamesArray[i - 1] = scanner.nextLine();
        }
        return playerNamesArray;
    }

    public static String[] citySelection(int rowNumber) {
        String excelFilePatch = ".\\Files\\CityInBulgaria.xlsx";

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(excelFilePatch);
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        XSSFWorkbook workbook = null;
        try {
            if (inputStream != null) {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        XSSFSheet sheet = null;
        if (workbook != null) {
            sheet = workbook.getSheetAt(0);
        }

        int cols = sheet != null ? sheet.getRow(1).getLastCellNum() : 0;
        String[] arrayOut = new String[cols];

        for (int i = 0; i < cols; i++) {
            XSSFRow row = sheet.getRow(rowNumber);
            XSSFCell cell = row.getCell(i);
            arrayOut[i] = cell.toString();
        }

        return arrayOut;
    }

    public static int randomGenerator(int max, int min) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static String[] randomCitySelection() {
        int cityNumber = randomGenerator(257, 1);
        return citySelection(cityNumber);
    }

    public static String extractOnlyCityNameFromDataArray() {
        return randomCitySelection()[0];
    }

    public static String readFile(String filePatch) {
        String line;
        StringBuilder resulText = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePatch));
            while ((line = reader.readLine()) != null) {
                resulText.append(line).append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resulText.toString();
    }

    public static void printPaternGame(int error) {
        switch (error) {

            case 0 -> System.out.println(readFile(".\\Files\\hangmanStartPatern.txt"));
            case 1 -> System.out.println(readFile(".\\Files\\hangmanFirstErrorPatern.txt"));
            case 2 -> System.out.println(readFile(".\\Files\\hangmanSecondErrorPatern.txt"));
            case 3 -> System.out.println(readFile(".\\Files\\hangmanThirdErrorPatern.txt"));
            case 4 -> System.out.println(readFile(".\\Files\\hangmanFoutrthErrorPatern.txt"));
            case 5 -> System.out.println(readFile(".\\Files\\hangmanFifthErrorPatern.txt"));
            case 6 -> System.out.println(readFile(".\\Files\\hangmanSixthErrorPatern.txt"));
            case 7 -> System.out.println(readFile(".\\Files\\hangmanSeventhErrorPatern.txt"));
        }
    }

    public static String creatingHiddenTextWithTheLengthOfTheText(String text) {
        StringBuilder hidenText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                hidenText.append(" ");
            } else {
                hidenText.append("_");
            }
        }
        return hidenText.toString();
    }

    public static String searchSymbolInText(String cityName, String hidenCityName, char charFromUser) {
        String cityNameLowerCase = cityName.toLowerCase();
        char charFromUserLowerCase = Character.toLowerCase(charFromUser);
        boolean foundBefore = checkForTypedCharacterFoundBefore(hidenCityName, charFromUser);

        for (int i = 0; i < cityNameLowerCase.length(); i++) {
            if (cityNameLowerCase.charAt(i) == charFromUserLowerCase && !foundBefore) {
                hidenCityName = hidenCityName.substring(0, i) + cityName.charAt(i) + hidenCityName.substring(i + 1);
            }
        }
        return hidenCityName;
    }

    public static boolean checkForTypedCharacterFoundBefore(String hidenCityName, char charFromUser) {
        char charFromUserLowerCase = Character.toLowerCase(charFromUser);
        String hidenCityNameLowerCase = hidenCityName.toLowerCase();

        boolean foundBefore = false;
        for (int i = 0; i < hidenCityName.length(); i++) {
            if (hidenCityNameLowerCase.charAt(i) == charFromUserLowerCase) {
                foundBefore = true;
                break;
            }
        }
        return foundBefore;
    }

    public static boolean characterMatchingCheck(String cityName, char charFromUser) {
        String cityNameLowerCase = cityName.toLowerCase();
        char charFromUserLowerCase = Character.toLowerCase(charFromUser);
        boolean match = false;

        for (int i = 0; i < cityNameLowerCase.length(); i++) {
            if (cityNameLowerCase.charAt(i) == charFromUserLowerCase) {
                match = true;
                break;
            }
        }
        return match;
    }

    public static boolean validateInputFromUser(String inputFromUser) {
        boolean validInput = false;
        if (inputFromUser.length() < 2) {
            int asciValue = inputFromUser.charAt(0);
            if (asciValue > 1039 && asciValue < 1104 && inputFromUser.charAt(0) != '_') {
                validInput = true;
            }
        }
        return validInput;
    }

    public static char readFormUser(Scanner scanner, String userName) {
        String readSymbol = null;
        for (int i = 3; i >= 1; i--) {
            System.out.println("\n" + userName + " въведете символ: ");
            readSymbol = scanner.next();
            if (validateInputFromUser(readSymbol)) {
                break;
            } else {
                System.out.println("\n" + userName + " въведохте невалиден вход оставащти опити " + (i - 1));
            }
        }
        return readSymbol.charAt(0);
    }

    public static void gameLoop(Scanner scanner, String cityName) {

        String[] players = readPlayersNames(scanner);
        String hidenText = creatingHiddenTextWithTheLengthOfTheText(cityName);
        System.out.println(cityName);
        int errorCount = 0;
        int pleyerSelector = 0;
        while (!hidenText.equals(cityName)) {
            printPaternGame(errorCount);
            System.out.println(hidenText);
            char ch = readFormUser(scanner, players[pleyerSelector]);

            boolean symbolFoundBefore = checkForTypedCharacterFoundBefore(hidenText, ch);
            if (symbolFoundBefore) {
                System.out.println("Въведохте същевтвуващ съмвол");
            }

            hidenText = searchSymbolInText(cityName, hidenText, ch);

            String messageEndGame = " е търсения град.!";
            if (hidenText.equals(cityName)) {
                System.out.println(cityName + messageEndGame);
                System.out.println("\n" + players[pleyerSelector] + "\u001B[32m ти печелиш честито\u001B[0m");
                break;
            } else if (errorCount >= 6) {
                printPaternGame(errorCount+1);
                System.out.println(cityName + messageEndGame);
                System.out.println("\n" + players[pleyerSelector] + "\u001B[31m ти губиш \u001B[0m");
                break;
            }

            boolean characterMatching = characterMatchingCheck(cityName, ch);
            if (!characterMatching && !symbolFoundBefore) {
                pleyerSelector = (pleyerSelector == 1) ? 0 : 1;
                errorCount++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cityName = extractOnlyCityNameFromDataArray();
        gameLoop(scanner, cityName);

    }
}
