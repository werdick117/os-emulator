/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import org.operatingsystems.exceptions.BaseException;
import org.operatingsystems.exceptions.GetDataException;
import org.operatingsystems.exceptions.LineLimitException;
import org.operatingsystems.exceptions.OutOfDataCardsException;
import org.operatingsystems.exceptions.PageFault;
import org.operatingsystems.exceptions.PrintDataException;
import org.operatingsystems.exceptions.TimeLimitException;

/**
 *
 * @author Steve
 */
public class OperatingSystem {
    private BaseException interrupt;
    private Computer myComputer;
    private int linesPrinted;
    private int executionTime;
    
    
    public OperatingSystem(Computer c)
    {
        myComputer = c;
        interrupt = null;
    }
    
    public void handleInterrupt(BaseException e)
    {
        interrupt = e;
        
        if(e.getCode().equals("SI1"))
        {
            loadDataCard();
        }else if(e.getCode().equals("SI2")){
            printNewLine();
        }else if(e.getCode().equals("SI3")){
            myComputer.releaseProgramMemory();
            myComputer.setSI(3);
        }else if(e.getCode().equals("TI2"))
        {
            myComputer.setTI(2);
        }else if(e.getCode().equals("PI3"))
        {
            determineValidity();
        }else if((e.getCode().equals("TI1"))){
            myComputer.setTI(1);
        }else if((e.getCode().equals("SI4")))
        {
            myComputer.setSI(4);
        }
    }
    
    private void determineValidity()
    {
        if(!myComputer.getInstructionRegister().equals("GD") || myComputer.getInstructionRegister().equals("SR"))
            myComputer.setPI(3);
    }
    
    private void loadDataCard()
    {
        GetDataException e = (GetDataException)interrupt;
        String s = myComputer.getProgram().getNextDataLine();
        
        if(s == null)
            this.handleInterrupt(new OutOfDataCardsException());
        else      
           myComputer.getProgramMemory().setBlock(e.getOperand(), s);
    }
    
    private void printNewLine()
    {
        try
        {
            myComputer.setLinesPrinted(myComputer.getLinesPrinted() + 1);
            PrintDataException p = (PrintDataException)interrupt;
            
            String[] myBlock = myComputer.getProgramMemory().getBlock(p.getOperand());
            int i = 0;
            String output = "";
            String word;
            
            while(i < 10) {
                word = myBlock[i++];
                if (word == null)
                    output += "    ";
                else
                    output += word;
            }
            
            if(++linesPrinted > myComputer.getProgram().getLineLimit())
                throw new LineLimitException();
            else
                myComputer.getProgram().addToQueue(output);
            
        }catch(PageFault e)
        {
            this.handleInterrupt(new PageFault(false));
        }
    }
    
    public int getLinesPrinted() {
        return linesPrinted;
    }

    public void setLinesPrinted(int linesPrinted) {
        this.linesPrinted = linesPrinted;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int time) {
        this.executionTime = time;
    }
    
    public void startNextCycle()
    {
        this.executionTime++;
        
        if(this.executionTime > myComputer.getProgram().getTimeLimit())
            throw new TimeLimitException();
    }
}
