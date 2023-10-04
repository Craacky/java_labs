import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * The class is used to complete first and second tasks.
 *
 * @author Yakutin Nicolas
 * @version 0.1
 */
public class App {
    /**
     * Main function is used to call the punctuation counting function.
     * Create variables for the current date and time.
     * Output resutls of two functions.
     * Call the counting function for the number of zeros in a binary number.
     */
    public static void main(String[] args) {

        int count = getCount();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        System.out.println("-------------------------------------------------------");
        System.out.println("Punctuation marks in the text = " + count);
        System.out.println("-------------------------------------------------------");
        System.out.println("Developed by Yakutin | " + currentDate + " | " + currentTime);
        System.out.println("-------------------------------------------------------");
        int binaryCounter = getBinaryCount();
        System.out.println("Count of zeros of your binary number = " + binaryCounter);
        System.out.print("-------------------------------------------------------");
    }

    /**
     * The function creates a variable of the file type with the path indication.
     * A statement is used to detect an error if a file is missing from the
     * specified path.
     * Inside, by specifying the reading stream of our file variable,
     * we go through line by line and find punctuation marks
     * 
     * @throws FileNotFoundException
     * @return count punctuation marks in the text
     */
    private static int getCount() {
        File myFile = new File("text.txt");
        int count = 0;

        try {

            Scanner myReader = new Scanner(myFile);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++) {
                    if (data.charAt(i) == '!' || data.charAt(i) == ',' ||
                            data.charAt(i) == ';' || data.charAt(i) == '.' ||
                            data.charAt(i) == '?' || data.charAt(i) == '-' ||
                            data.charAt(i) == '\'' || data.charAt(i) == '\"'
                            || data.charAt(i) == ':') {
                        count++;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * A decimal number is entered into the function.
     * A string is created to convert a decimal number to binary.
     * After that, the number of zeros in a binary number is read.
     * 
     * @return count zeros if binary number
     */
    private static int getBinaryCount() {
        System.out.print("Enter your decimal number = ");
        Scanner in = new Scanner(System.in);
        int decimalNumber = in.nextInt();
        int count = 0;

        in.close();

        String binary = Integer.toBinaryString(decimalNumber);
        System.out.println("decimal = " + decimalNumber + " = " + binary);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                count++;
            }
        }
        return count;
    }
}
