/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Steve
 */
public class ProgramParser {
    
    public static Program parse(ArrayList<String> programText)
    {
        Program returnProgram = new Program();
        ArrayList<String> instructionsPiece = new ArrayList<String>();
        ArrayList<String> dataPiece = new ArrayList<String>();
        
        returnProgram.setId(programText.remove(0));
        programText.remove(programText.size() - 1);
        
        ArrayList<String> currentPiece = instructionsPiece;
        for(String s: programText)
        {
            if(s.equals("$DTA"))
                currentPiece = dataPiece;
            else
                currentPiece.add(s);
        }
        
        programText.remove(0);
        
        returnProgram.setInstructions(parseInstructions(instructionsPiece));
        returnProgram.setData(parseData(dataPiece));
        
        return returnProgram;
    }
    
    private static ArrayList<String> parseInstructions(ArrayList<String> instructionsPiece)
    {
        ArrayList<String> returnList = new ArrayList<String>();
        
        for(String s : instructionsPiece)
            for(int i = 0; i < s.length(); i+= 4)
                returnList.add(s.substring(i,i+4));
        
        return returnList;
    }
    
    private static LinkedList<String> parseData(ArrayList<String> dataPiece)
    {
        LinkedList<String> returnList = new LinkedList<String>();
        
        for(String s : dataPiece)
            returnList.add(s);
        
        return returnList;
    }
}
