import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;

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

    public static String readFile(String filePatch){
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

    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
          printPaternGame(i);
        }
       //for (String i : randomCitySelection()) System.out.println(i);
    }
}
