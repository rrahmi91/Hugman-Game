import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
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
            case 0 -> System.out.println(readFile(".\\Files\\hugmanStartPatern.txt"));
            case 1 -> System.out.println(readFile(".\\Files\\hugmanFirstErrorPatern.txt"));
            case 2 -> System.out.println(readFile(".\\Files\\hugmanSecondErrorPatern.txt"));
            case 3 -> System.out.println(readFile(".\\Files\\hugmanThirdErrorPatern.txt"));
            case 4 -> System.out.println(readFile(".\\Files\\hugmanFoutrthErrorPatern.txt"));
            case 5 -> System.out.println(readFile(".\\Files\\hugmanFifthErrorPatern.txt"));
            case 6 -> System.out.println(readFile(".\\Files\\hugmanSixthErrorPatern.txt"));
            case 7 -> System.out.println(readFile(".\\Files\\hugmanSeventhErrorPatern.txt"));
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    public static String creatingHiddenTextWithTheLengthOfTheText(String text) {
        String hidenText = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                hidenText = hidenText + " ";
            } else {
                hidenText = hidenText + "_";
            }
        }
        return hidenText;
    }

    public static String searchSymbolInText(String cityName, String hidenCityName, char charFromUser) {
        String text = cityName.toLowerCase();
        char lowerCaseConvertedChar = Character.toLowerCase(charFromUser);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == lowerCaseConvertedChar) {
                hidenCityName = hidenCityName.substring(0, i) + cityName.charAt(i) + hidenCityName.substring(i + 1);
            }
        }
        return hidenCityName;
    }

    public static boolean characterMatchingCheck(String cityName, char charFromUser) {
        String text = cityName.toLowerCase();
        char lowerCaseConvertedChar = Character.toLowerCase(charFromUser);
        boolean match = false;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == lowerCaseConvertedChar) {
                match = true;
            }
        }
        return match;
    }

    public static char readFormUser(Scanner scanner, String userName) {
        System.out.println("\n" + userName + " въведете символ: ");
        String readSymbol = scanner.next();
        return readSymbol.charAt(0);
    }

    //------------------------------------------Start----------------------------
    public static String[] readPlayersNames(Scanner scanner) {
        String[] playerNamesArray = new String[2];
        for (int i = 1; i <= playerNamesArray.length; i++) {
            System.out.println("\nМоля въведете име на ирач " + i);
            playerNamesArray[i - 1] = scanner.nextLine();
        }
        return playerNamesArray;
    }

    public static void gameLoop(Scanner scanner, String cityName) {

        String[] Pleyers = readPlayersNames(scanner);
        String hidenText = creatingHiddenTextWithTheLengthOfTheText(cityName);
        System.out.println(cityName);
        int ErrorCount = 0;

        while (!hidenText.equals(cityName)) {
            int i = 0;
            while (i < Pleyers.length) {
                printPaternGame(ErrorCount);
                System.out.println(hidenText);
                char ch = readFormUser(scanner, Pleyers[i]);
                hidenText = searchSymbolInText(cityName, hidenText, ch);

                if (!characterMatchingCheck(cityName, ch)) {
                    i=(i == 1) ? 0 : 1;
                    ErrorCount++;/////Провери защо еррор следващия цикъл броиии
                }

                if (hidenText.equals(cityName)) {
                    System.out.println("\n" + Pleyers[i] + " ти печелиш честито");
                    break;
                } else if (ErrorCount>=8) {
                    System.out.println("\n" + Pleyers[i] + " ти губиш");
                    break;
                }
            }
        }

    }


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String cityName=extractOnlyCityNameFromDataArray();
        gameLoop(scanner,cityName);
    }
}
