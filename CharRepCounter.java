package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
* Reads a file and calculates mean median and mode.
*
* @author  Mr. Riscalas
* @version 1.0
* @since   2023-03-22
*/

public final class CharRepCounter {
    /**
     * This is a private constructor used to satisfy the
     * style checker.
     *
     * @exception IllegalStateException Utility class.
     * @see IllegalStateException
     */
    private CharRepCounter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is the repCalc method.
     *
     * @param characters //characters
     * @return maxNumRep
     *
     */
    public static int repCalc(char[] characters) {
        // set the max num to a default of 1
        int maxNumRep = 1;
        int currentNumRep = 1;
        // store the data of the first character in currentChar
        char currentChar = characters[0];
        for (int i = 1; i < characters.length; i++) {
            // If it repeats increase the repeat by one
            if (characters[i] == currentChar) {
                currentNumRep++;
            } else {
                // if it doesn't repeat set a new default checker
                currentChar = characters[i];
                currentNumRep = 1;
            }
            // if the repetitions outway the maximum repitition set the new one
            if (currentNumRep > maxNumRep) {
                maxNumRep = currentNumRep;
            }
        }
        return maxNumRep;
    }

    /**
     * This is the main method.
     *
     * @param args //unused
     *
     */

    public static void main(final String[] args) {
        // Constants to appease checkstyle
        final String FILE_INPUT_STRING = "1";
        final String CONSOLE_INPUT_STRING = "2";
        final String OUTPUT_FILE_NAME = "output.txt";
        final String ENTER = "\n";
        // open new scanner input
        try (Scanner SCANNER = new Scanner(System.in)) {
            // Check if they want to input from a file or the console
            System.out.println("Press 1 if you have a file of inputs or press 2"
                    + " just for a console input");
            final String INPUT_STRING = SCANNER.nextLine();
            if (FILE_INPUT_STRING.equals(INPUT_STRING)) {
                System.out.println("What is the name of the file?");
                final String INPUT_FILE_NAME = SCANNER.nextLine();
                // Ask what they would like to output to
                System.out.println("Press 1 if you would like to output to a "
                        + "file or 2 for the console");
                String outputType = SCANNER.nextLine();
                // If they have entered an invalid input then set to a default
                if (!outputType.equals(FILE_INPUT_STRING) && !outputType.equals(
                                                    CONSOLE_INPUT_STRING)) {
                    System.out.println("The type you have entered is invalid");
                    System.out.println("Now defaulting to file type");
                    outputType = FILE_INPUT_STRING;
                }
                // Create a new File object representing the file to be read
                // Input file
                final File FILE = new File(INPUT_FILE_NAME);
                final File OUTPUT_FILE = new File(OUTPUT_FILE_NAME);

                // Create a new Scanner object to read from the file
                String[] stringsArray = null;
                try (Scanner FILE_SCAN = new Scanner(FILE)) {
                    // split the file into an array by newline
                    final String DELIMITER = "\\Z";
                    final String STRINGS = FILE_SCAN.useDelimiter(
                                                        DELIMITER).next();
                    stringsArray = STRINGS.split(
                                                    System.lineSeparator());
                } catch (FileNotFoundException error) {
                    System.out.println("File not found!");
                }
                int repeatedChar = 0;
                try (FileWriter WRITER = new FileWriter(OUTPUT_FILE)) {
                    // convert to character array then calculate the problem
                    for (int i = 0; i < stringsArray.length; i++) {
                        final char[] CHARACTER_LINE_ARRAY =
                                            stringsArray[i].toCharArray();
                        repeatedChar = repCalc(CHARACTER_LINE_ARRAY);
                        // dependent on how the user wants to output, output
                        if (outputType.equals(FILE_INPUT_STRING)) {
                            WRITER.write(String.valueOf(repeatedChar + ENTER));
                            WRITER.flush();
                        } else {
                            System.out.println(repeatedChar);
                        }
                    }
                } catch (IOException error) {
                    System.out.println(
                        "An error occurred while writing to file: ");
                    error.printStackTrace();
                }
            } else if (INPUT_STRING.equals(CONSOLE_INPUT_STRING)) {
                // Ask the user for a string
                System.out.println("What is the string you'd like to check?");
                // Convert it to character Array
                final char[] CHARACTER_ARRAY = SCANNER.nextLine().toCharArray();
                // Check the desired output type
                System.out.println("Press 1 if you would like the output to be "
                        + "to a file or 2 for the console");
                String outputType = SCANNER.nextLine();
                // Check if the input is valid
                if (!outputType.equals(FILE_INPUT_STRING) && !outputType.equals(
                                                        CONSOLE_INPUT_STRING)) {
                    System.out.println("You have entered an invalid type");
                    System.out.println("Defaulting now to file type");
                    outputType = FILE_INPUT_STRING;
                }
                final File OUTPUT_FILE = new File(OUTPUT_FILE_NAME);
                int charRepeatedNum = 0;
                // Call the function
                charRepeatedNum = repCalc(CHARACTER_ARRAY);
                // Check the desired output type and properly display
                if (outputType.equals(FILE_INPUT_STRING)) {
                    try (FileWriter WRITER = new FileWriter(OUTPUT_FILE)) {
                        WRITER.write(String.valueOf(charRepeatedNum + ENTER));
                        WRITER.flush();
                    } catch (IOException error) {
                        System.out.println(
                            "An error occurred while writing to the file: ");
                        error.printStackTrace();
                    }
                } else {
                    System.out.println(charRepeatedNum);
                }
            // If the user enters an invalid input at the beginning:
            } else {
                System.out.println("That's not a correct input.");
            }
        }
    }
}
