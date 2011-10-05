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
    private Program program;
    
    public OperatingSystem(Computer c)
    {
        myComputer = c;
        program = c.getProgram();
        interrupt = null;
    }
    
    public void handleInterrupt(BaseException e)
    {
        interrupt = e;
        program = myComputer.getProgram();
        
        program.addToTrace("\nMaster Mode assumed");
        
        if(e.getCode().equals("SI1"))
        {
            program.addToTrace("Get Data interrupt handled");
            loadDataCard();
        }else if(e.getCode().equals("SI2"))
        {
            program.addToTrace("Print Data interrupt handled");
            printNewLine();
        }else if(e.getCode().equals("SI3")){
            program.addToTrace("Halt interrupt handled");
            myComputer.releaseProgramMemory();
            myComputer.breakExecution();
            myComputer.setSI(3);
        }else if(e.getCode().equals("TI2"))
        {
            program.setExitCode("Time Limit Exceeded");
            myComputer.breakExecution();
            myComputer.setTI(2);
        }else if(e.getCode().equals("PI3"))
        {
            program.addToTrace("Page fault interrupt handled, determining validity:");
            determineValidity();
        }else if((e.getCode().equals("TI1"))){
            myComputer.breakExecution();
            linesPrinted--;
            program.setExitCode("Line Limit Exceeded");
        }else if((e.getCode().equals("SI4")))
        {
            myComputer.breakExecution();
            program.setExitCode("Out of Data Cards");
        }else if((e.getCode().equals("PI2")))
        {
            myComputer.breakExecution();
            myComputer.setPI(2);
        }
        
        program.addToTrace("Resuming Slave Mode execution");
    }
    
    private void determineValidity()
    {
        if(!(myComputer.getInstructionRegister().equals("GD") || myComputer.getInstructionRegister().equals("SR")))
        {
            program.addToTrace("...Page fault is invalid");
            program.setExitCode("Invalid Page Fault");
            myComputer.breakExecution();
            myComputer.setPI(3);
        }
        else
            program.addToTrace("...Page fault is valid");
    }
    
    private void loadDataCard()
    {
        GetDataException e = (GetDataException)interrupt;
        String s = myComputer.getProgram().getNextDataLine();
        
        if(s == null)
        {
            myComputer.breakExecution();
            program.setExitCode("Out of Data Cards");
            this.handleInterrupt(new OutOfDataCardsException());
        }
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
            
        }catch(BaseException e)
        {
            if(e.getCode().equals("PI3"))
            {
                myComputer.breakExecution();
                program.setExitCode("Invalid Page Fault");
                this.handleInterrupt(new PageFault(false));
            }
            else
            {
                myComputer.breakExecution();
                program.setExitCode("Line Limit Exceeded");
                this.handleInterrupt(new LineLimitException());
            }
            
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
