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

    public static void main(String[] args) {
        try
        {
        //Read in the text file specified by the user
        Main myEmulator = new Main();
        ArrayList<Program>  myPrograms = null;

        Computer myComputer = new Computer();

        ArrayList<String> programText;

       Scanner reader = new Scanner(System.in);

        //Read in the program data files one file at a time
        System.out.println("Please enter in the full file path name of each program file to be executed.");
        System.out.println("When finished enter \"end\" to stop executing programs");

        String input = reader.nextLine();
                
            try {
              
                programText = myEmulator.readFile("C:\\Users\\Steve\\Desktop\\emu\\inputfinal.txt");
                myPrograms = ProgramParser.parse(programText);
               
                
                for(Program p : myPrograms)
                {
                        
                    try
                    {
                        p.addToTrace("\n\nTrace for program: " + p.getId());
                        p.addToTrace("***********************");
                        
                      myComputer.wrapExecution(p);
                      
                      
                    //  p.printAll();
                      
                      if(p.getId().equals("U602") || p.getId().equals("G102"))
                          p.printTrace();
                    }catch(Exception e)
                    {
                        e.printStackTrace();

                        System.out.println("\n\nERROR: " + p.getId() +"\n\n");
                        
                        
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error reading from file.  Check your path name or file contents for errors.");
            }
                 

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