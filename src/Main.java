import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String[][] readFromExel() {
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
        int lastRow = sheet != null ? sheet.getLastRowNum() : 0;
        String[][] arrayOut = new String[lastRow + 1][cols];

        for (int i = 0; i <= lastRow; i++) {
            for (int j = 0; j < cols; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                arrayOut[i][j] = cell.toString();
            }
        }

        return arrayOut;
    }

    public static int randomGenerator(int max, int min) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static String[] randomCitySelection(String[][] citiesData) {
        int cityNumber = randomGenerator(257, 1);

        String[] cityData = new String[citiesData[0].length];
        System.arraycopy(citiesData[cityNumber], 0, cityData, 0, citiesData[0].length);
        return cityData;
    }

    public static String extractOnlyCityNameFromDataArray(String[][] citiesData) {
        return randomCitySelection(citiesData)[0];
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

    public static String[][] readPlayersNames(Scanner scanner) {
        String[][] playerDataMatrix = new String[2][2];
        String initialScore = "0";
        for (int i = 1; i <= playerDataMatrix.length; i++) {
            System.out.println("\nМоля въведете име на ирач " + i);
            playerDataMatrix[i - 1][0] = scanner.nextLine();
            playerDataMatrix[i - 1][1] = initialScore;
        }

        return playerDataMatrix;
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

    public static String gameLoop(String[][] players, Scanner scanner, String cityName) {
        int errorCount = 0;
        int pleyerSelector = 0;
        System.out.println(cityName);
        String hidenText = creatingHiddenTextWithTheLengthOfTheText(cityName);

        while (!hidenText.equals(cityName)) {
            printPaternGame(errorCount);
            System.out.println(hidenText);
            char ch = readFormUser(scanner, players[pleyerSelector][0]);

            boolean symbolFoundBefore = checkForTypedCharacterFoundBefore(hidenText, ch);
            if (symbolFoundBefore) {
                System.out.println("Въведохте същевтвуващ съмвол");
            }

            hidenText = searchSymbolInText(cityName, hidenText, ch);

            String messageEndGame = " е търсения град.!";
            if (hidenText.equals(cityName)) {
                System.out.println(cityName + messageEndGame);
                System.out.println("\n" + players[pleyerSelector][0] + "\u001B[32m ти печелиш честито!!!\u001B[0m\n");
                break;
            } else if (errorCount >= 6) {
                printPaternGame(errorCount + 1);
                System.out.println(cityName + messageEndGame);
                System.out.println("\n" + players[pleyerSelector][0] + "\u001B[31m ти губиш \u001B[0m\n");
                break;
            }

            boolean characterMatching = characterMatchingCheck(cityName, ch);
            if (!characterMatching && !symbolFoundBefore) {
                pleyerSelector = (pleyerSelector == 1) ? 0 : 1;
                errorCount++;
            }
        }

        return players[pleyerSelector][0];
    }

    public static boolean promptForGameContinuation(Scanner scanner) {
        scanner.nextLine();
        String greenColor = "\u001B[32m";
        String redColor = "\u001B[31m";
        String resetColor = "\u001B[0m";
        String infoText = greenColor + "\"Да\"" + resetColor + " за продължение " + redColor + "\"Не\"" + resetColor + " за изход.";

        System.out.println("Въведете вашия избор:");
        System.out.println(infoText);

        String readFromUser;
        boolean gameContinues;
        while (true) {
            readFromUser = scanner.nextLine();
            readFromUser = readFromUser.toLowerCase();
            if (readFromUser.equals("да")) {
                gameContinues = true;
                break;
            } else if (readFromUser.equals("не")) {
                gameContinues = false;
                break;
            } else {
                System.out.println(redColor + "Въведохте, не подходяща команда." + resetColor + " Моля въведете " + infoText);
            }
        }

        return gameContinues;
    }

    public static void runHangmanGame(Scanner scanner, String[][] cityData) {
        String[][] players = readPlayersNames(scanner);
        String[][] score;
        while (true) {
            String cityName = extractOnlyCityNameFromDataArray(cityData);
            String roundWinner = gameLoop(players, scanner, cityName);
            score = saveScore(roundWinner, players);
            boolean gameContinuation = promptForGameContinuation(scanner);
            printMatrix(score);
            if (!gameContinuation) {
                break;
            }
        }
        System.out.println(printWinner(score));
    }

    public static String[][] saveScore(String winnerName, String[][] playersName) {

        if (winnerName.equals(playersName[0][0])) {
            playersName[0][1] = String.valueOf(Integer.parseInt(playersName[0][1]) + 1);
        } else if (winnerName.equals(playersName[1][0])) {
            playersName[1][1] = String.valueOf(Integer.parseInt(playersName[1][1]) + 1);
        }

        return playersName;
    }

    public static String printWinner(String[][] playerData) {
        int scorePlayer1 = Integer.parseInt(playerData[0][1]);
        int scorePlayer2 = Integer.parseInt(playerData[1][1]);
        if (scorePlayer1 > scorePlayer2) {
            return "Поздравления " + playerData[0][0] + " ти печелиш";
        } else if (scorePlayer1 < scorePlayer2) {
            return "Поздравления " + playerData[1][0] + " ти печелиш";
        } else {
            return "Поздравления и на двамата ви " + playerData[0][0] + " и " + playerData[1][0] + ". " + "Вашия резултат е равен";
        }
    }

    public static void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "      ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[][] cityData = readFromExel();
        runHangmanGame(scanner, cityData);


    }
}
