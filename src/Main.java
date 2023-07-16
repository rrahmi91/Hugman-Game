import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static XSSFSheet readSheetFromExcel() {
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
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sheet;
    }

    public static String[][] createArrayFromSheet(XSSFSheet sheet) {
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

    public static String[] getCityData(String[][] citiesData) {
        return randomCitySelection(citiesData);
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

    public static void printMenu() {
        String blueColor = "\u001B[34m";
        String yellowColor = "\u001B[33m";
        String resetColor = "\u001B[0m";
        System.out.println(blueColor + "*******************************************************************" + resetColor);
        System.out.println(blueColor + "*" + resetColor + yellowColor + " Моля, изберете режим на игра като въведете 1 или 2 в конзолата. " + blueColor + "*");
        System.out.println(blueColor + "*-----------------------------------------------------------------*");
        System.out.println(blueColor + "*" + resetColor + "\t\t\t\t    1.За самостоятелна игра.\t\t\t\t\t  " + blueColor + "*");
        System.out.println(blueColor + "*" + resetColor + "\t\t\t\t    2.Игра за двама. \t\t\t\t\t\t\t  " + blueColor + "*");
        System.out.println(blueColor + "*******************************************************************" + resetColor);
    }

    public static byte askGameMod(Scanner scanner) {
        byte numberOfPlayers;
        while (true) {
            printMenu();
            try {
                numberOfPlayers = scanner.nextByte();
                if (numberOfPlayers < 3 && numberOfPlayers > 0) {
                    break;
                } else {
                    System.out.println("\u001B[31mНе валиден избор.\u001B[0m");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\u001B[31mГрешка: Не валиден вход. !!!!\u001B[0m");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return numberOfPlayers;
    }

    public static String[][] readPlayersNames(Scanner scanner, byte numberOfPlayers) {
        String[][] playerDataMatrix = {{"Player1", "0"},
                {"Player2", "0"}};
        for (int i = 1; i <= numberOfPlayers; i++) {
            playerDataMatrix[i - 1][0] = usernameValidation(i, scanner, playerDataMatrix);
        }

        return playerDataMatrix;
    }

    public static String usernameValidation(int gamerNumber, Scanner scanner, String[][] userData) {
        while (true) {
            System.out.println("\nМоля въведете име на ирач " + gamerNumber);
            userData[gamerNumber - 1][0] = scanner.nextLine().trim();

            if (userData[gamerNumber - 1][0].trim().isEmpty()) {
                System.out.println("\u001B[31mГрешка: Невалидно име на играч " + gamerNumber + " \u001B[0m");
            } else if (userData[0][0].equals(userData[1][0].trim())) {
                System.out.println("\u001B[31mГрешка: Това име вече съществува.!\u001B[0m");
            } else {
                break;
            }
        }

        return userData[gamerNumber - 1][0];
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
            case 0 ->
                    System.out.println(readFile(".\\Files\\hangmanStartPatern.txt"));
            case 1 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanFirstErrorPatern.txt")) + "\u001B[0m");
            case 2 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanSecondErrorPatern.txt")) + "\u001B[0m");
            case 3 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanThirdErrorPatern.txt")) + "\u001B[0m");
            case 4 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanFoutrthErrorPatern.txt")) + "\u001B[0m");
            case 5 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanFifthErrorPatern.txt")) + "\u001B[0m");
            case 6 ->
                    System.out.println("\u001B[32m" + (readFile(".\\Files\\hangmanSixthErrorPatern.txt")) + "\u001B[0m");
            case 7 ->
                    System.out.println("\u001B[31m" + (readFile(".\\Files\\hangmanSeventhErrorPatern.txt")) + "\u001B[0m");
        }
    }

    public static boolean validateInputFromUser(String inputFromUser) {
        boolean validInput = false;
        if (inputFromUser.length() < 2) {
            int asciValue = inputFromUser.charAt(0);
            if (asciValue > 1039 && asciValue < 1104) {
                validInput = true;
            }
        }

        return validInput;
    }

    public static char readFormUser(Scanner scanner, String username) {
        String readSymbol = null;
        for (int i = 3; i >= 1; i--) {
            System.out.println("\n" + username + " въведете символ: ");
            readSymbol = scanner.next();
            if (validateInputFromUser(readSymbol)) {
                break;
            } else {
                System.out.println("\n" + username + " въведохте невалиден вход оставащти опити " + (i - 1));
                System.out.println("\u001B[42m\033[3mМоля използвайте само:\033[0m\u001B[0m А а Б б В в Г г Д д Е е Ж ж З з И и Й й К к Л л М м Н н О о П пР р С с Т т У у Ф ф Х х Ц ц Ч ч Ш ш Щ щ Ъ ъ Ь ь Ю ю Я я\n");
                System.out.println("\u001B[41m\033[3mНе валидни входове:\033[0m\u001B[0m 0 1 2 3 4 5 6 7 8 9  ! \" № % & ' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _ { | } ~");
            }
        }

        return readSymbol.charAt(0);
    }

    public static String saveCharactersEntered(String charEnteredBefore, char userInput) {
        charEnteredBefore = charEnteredBefore + userInput;
        return charEnteredBefore;
    }


    public static String searchSymbolInWord(String cityName, String hidenCityName, char charFromUser, boolean foundBefore) {
        String cityNameLowerCase = cityName.toLowerCase();
        char charFromUserLowerCase = Character.toLowerCase(charFromUser);
        if (!foundBefore) {
            for (int i = 0; i < cityNameLowerCase.length(); i++) {
                if (cityNameLowerCase.charAt(i) == charFromUserLowerCase) {
                    hidenCityName = hidenCityName.substring(0, i) + cityName.charAt(i) + hidenCityName.substring(i + 1);
                }
            }
        }

        return hidenCityName;
    }

    public static boolean checkForTypedCharacterFoundBefore(String word, char charFromUser) {
        char charFromUserLowerCase = Character.toLowerCase(charFromUser);
        String wordLowerCase = word.toLowerCase();

        boolean foundBefore = false;
        for (int i = 0; i < word.length(); i++) {
            if (wordLowerCase.charAt(i) == charFromUserLowerCase) {
                foundBefore = true;
                System.out.println("\u001B[33mВъведохте същевтвуващ символ или е въведен от преди.\u001B[0m");
                System.out.println("\u001B[35mВъведени символи:\u001B[0m " + word);
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

    public static int selectRandomPlayer(byte numberOfPlayers) {
        int nextPlayer;
        if (numberOfPlayers == 1) {
            nextPlayer = 0;
        } else {
            nextPlayer = randomGenerator(1, 0);
        }
        return nextPlayer;
    }

    public static void printInfoData(String[] cityData) {
        String cityName = cityData[0];
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println(cityName + " е търсения град.!");
        System.out.println("\u001B[1m\u001B[47m\u001B[30mВ град " + cityName + " живеят общо " + cityData[1] + " души, от които са " + cityData[2] + " мъже и " + cityData[3] + " жени.\033[0m\u001B[0m");
        System.out.println("-------------------------------------------------------------------------------------------------------");
    }

    public static String gameLoop(String[][] players, Scanner scanner, String[] cityData, byte numberOfPlayers) {
        int errorCount = 0;
        int playerSelector = selectRandomPlayer(numberOfPlayers);
        String cityName = cityData[0];
        String hidenWord = creatingHiddenTextWithTheLengthOfTheText(cityName);
        String winnerName = null;
        String characterEnteredBefore = "";
        while (!hidenWord.equals(cityName)) {
            printPaternGame(errorCount);
            System.out.println(hidenWord);
            char inputFromUser = readFormUser(scanner, players[playerSelector][0]);
            boolean foundBefore = checkForTypedCharacterFoundBefore(characterEnteredBefore, inputFromUser);
            hidenWord = searchSymbolInWord(cityName, hidenWord, inputFromUser, foundBefore);
            if (!characterMatchingCheck(cityName, inputFromUser) && !foundBefore) {
                playerSelector = playerChange(numberOfPlayers, playerSelector);
                errorCount++;
            }
            characterEnteredBefore = saveCharactersEntered(characterEnteredBefore, inputFromUser);

            if (hidenWord.equals(cityName)) {
                printInfoData(cityData);
                winnerName = players[playerSelector][0];
                System.out.println("\n" + winnerName + "\u001B[32m ти печелиш този рунд честито!!!\u001B[0m\n");
                break;
            } else if (errorCount > 6) {
                printPaternGame(errorCount);
                printInfoData(cityData);
                System.out.println("\n" + players[playerSelector][0] + "\u001B[31m ти губиш този рунд.!!!\u001B[0m\n");
                break;
            }
        }

        return winnerName;
    }

    public static int playerChange(byte numberOfPlayers, int playerSelector) {
        if (numberOfPlayers != 1) {
            playerSelector = (playerSelector == 1) ? 0 : 1;
        }

        return playerSelector;
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

    public static void runHangmanGame(Scanner scanner, String[][] citiesData, byte numberOfPlayers) {
        String[][] players = readPlayersNames(scanner, numberOfPlayers);
        String[][] score;
        while (true) {
            String[] cityData = getCityData(citiesData);
            //1System.out.println(cityData[0]);
            String roundWinner = gameLoop(players, scanner, cityData, numberOfPlayers);
            score = saveScore(roundWinner, players);

            boolean gameContinuation = promptForGameContinuation(scanner);
            if (!gameContinuation) {
                break;
            }
        }
        System.out.println("--------------------------Резултат от играта-----------------------------");
        printMatrix(score, numberOfPlayers);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(printWinner(score, numberOfPlayers));
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(readFile(".\\Files\\endLogo.txt"));
    }

    public static String[][] saveScore(String winnerName, String[][] playersName) {
        try {
            if (winnerName.equals(playersName[0][0])) {
                playersName[0][1] = String.valueOf(Integer.parseInt(playersName[0][1]) + 1);
            } else if (winnerName.equals(playersName[1][0])) {
                playersName[1][1] = String.valueOf(Integer.parseInt(playersName[1][1]) + 1);
            }
        } catch (NullPointerException e) {
            System.out.println("--------------------------");
            System.out.println("Няма победител в този рунд");
            System.out.println("--------------------------");
        }

        return playersName;
    }

    public static String printWinner(String[][] playerData, byte numberOfPlayer) {
        int scorePlayer1 = Integer.parseInt(playerData[0][1]);
        int scorePlayer2 = Integer.parseInt(playerData[1][1]);
        if (scorePlayer1 > scorePlayer2) {
            return "Поздравления " + playerData[0][0] + " ти печелиш";
        } else if (scorePlayer1 < scorePlayer2 && numberOfPlayer == 2) {
            return "Поздравления " + playerData[1][0] + " ти печелиш";
        } else if (scorePlayer1 == scorePlayer2 && numberOfPlayer == 2) {
            return "Поздравления и на двамата ви " + playerData[0][0] + " и " + playerData[1][0] + ". " + "Вашия резултат е равен.!!";
        } else if (scorePlayer1 == scorePlayer2 && numberOfPlayer == 1) {
            return "\u001B[33mЗа съжаление " + playerData[0][0] + " ти нямаш игра печелена.\u001B[0m";
        } else {
            return "Грешка: Програмиста е пропуснал ситуация";
        }
    }

    public static void printMatrix(String[][] matrix, byte numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " \" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        XSSFSheet sheet = readSheetFromExcel();

        System.out.println(readFile(".\\Files\\startLogo.txt"));

        byte numberOfPlayers = askGameMod(scanner);
        String[][] cityData = createArrayFromSheet(sheet);
        runHangmanGame(scanner, cityData, numberOfPlayers);
    }
}
