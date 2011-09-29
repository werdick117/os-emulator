/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Steve
 */
public class ProgramParser {
    
    public static Program parse(ArrayList<String> programText)
    {
        Program returnProgram = new Program();
        ArrayList<String> instructionLines = new ArrayList<String>();
        LinkedList<String> dataLines = new LinkedList<String>();
        
        returnProgram.setId(programText.remove(0));
        programText.remove(programText.size() - 1);
        
        List<String> currentPiece = instructionLines;
        for(String s: programText)
        {
            if(s.equals("$DTA"))
                currentPiece = dataLines;
            else
                currentPiece.add(s);
        }
        
        returnProgram.setInstructions(instructionLines);
        returnProgram.setData(dataLines);
        
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
