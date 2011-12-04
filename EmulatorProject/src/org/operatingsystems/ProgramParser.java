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
    
    public static ArrayList<PCB> parse(ArrayList<String> programText)
    {
        ArrayList<PCB> returnPrograms = new ArrayList<PCB>();
        
        int j = 0;
        for(int i=0; i < programText.size(); i++)
        {
            if(programText.get(i).startsWith("$AMJ"))
            {
              //  System.out.println(i);
                j = i;
                
                while(!programText.get(j).startsWith("$EOJ"))
                {
                   // System.out.println(programText.get(j));
                    j++;
                    if(programText.get(j).startsWith("$EOJ"))
                        returnPrograms.add(getProgram(programText,i,j));
                }
            }
        }
        
        return returnPrograms;
    }
    
    private static PCB getProgram(ArrayList<String> programText, int startIndex, int endIndex)
    {     
        //System.out.println(startIndex + "   " + endIndex + "    " + programText.size());
        PCB retVal = new PCB();
        int j = 0;
        
        for(int i = startIndex + 1; i <= endIndex; i++)
        {
            if(programText.get(i).startsWith("$DTA"))
            {
                j = i;
                break;
            }
            
            retVal.addInstruction(programText.get(i));
        }
        
        for(int i = j+1; i < endIndex; i++)
            retVal.addData(programText.get(i));
        
        retVal.setId(programText.get(startIndex));
        
        return retVal;
    }
}
