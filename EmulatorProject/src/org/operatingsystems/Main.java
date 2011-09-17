    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Steve
 */
public class Main {

    int CostOfThisProgramBecomingSkynet = Integer.MAX_VALUE;

    public static void main(String[] args) {
        try
        {
        //Read in the text file specified by the user
        Main myEmulator = new Main();
        Program myPrograms = null;

        Computer myComputer = new Computer();

        ArrayList<String> programText;

        Scanner reader = new Scanner(System.in);

        //Read in the program data files one file at a time
        System.out.println("Please enter in the full file path name of each program file to be executed.");
        System.out.println("When finished enter \"end\" to stop executing programs");

        String input = reader.nextLine();

        while (!input.equals("end")) {
                
            try {
              
                programText = myEmulator.readFile(input);
                myPrograms = ProgramParser.parse(programText);
                

                myComputer.runProgram(myPrograms);
                
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error reading from file.  Check your path name or file contents for errors.");
            }

            System.out.println("Next program file path name: ");
            input = reader.nextLine();

        }

        System.out.println("Program execution ceased.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    /*
     * 
     */
    private ArrayList<String> readFile(String filePath) {
        ArrayList<String> returnValue = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;

            while ((line = br.readLine()) != null) {
                //Process the data, here we just print it out
                returnValue.add(line);
            }
            // dispose all the resources after using them.
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }
}