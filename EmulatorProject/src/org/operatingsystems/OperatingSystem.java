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
    private PCB program;
    
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
        
//        program.addToTrace("\nMaster Mode assumed");
        
        if(e.getCode().equals("SI1"))
        {
 //           program.addToTrace("Get Data interrupt handled");
            GetDataException z = (GetDataException)interrupt;
            
            if(!myComputer.getRunningProgram().hasMoreDataLines())
                this.handleInterrupt(new OutOfDataCardsException());
            
            myComputer.setSI(1);
            myComputer.setGetOperand(z.getOperand());
 //           loadDataCard();
        }else if(e.getCode().equals("SI2"))
        {
            try
            {
 //           program.addToTrace("Print Data interrupt handled");
            PrintDataException p = (PrintDataException)interrupt;
            myComputer.setPrintOperand(p.getOperand());
            myComputer.setSI(2);
            
            String[] myBlock = myComputer.getProgramMemory().getBlock(myComputer.getPrintOperand());
            
            int t = myComputer.getRunningProgram().getLinesPrinted();
            t++;
            myComputer.getRunningProgram().setLinesPrinted(t);
            
            if( t > myComputer.getRunningProgram().getLineLimit())
                throw new LineLimitException();
            
            }catch(BaseException q)
            {
                // Interrupt
                this.handleInterrupt(q);
            }
        }else if(e.getCode().equals("SI3")){
            myComputer.setSI(3);
        }else if(e.getCode().equals("TI2"))
        {
            program.setExitCode("Time Limit Exceeded");
            myComputer.breakExecution();
            myComputer.setTI(2);
        }else if(e.getCode().equals("PI3"))
        {
  //          program.addToTrace("Page fault interrupt handled, determining validity:");
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
        
 //       program.addToTrace("Resuming Slave Mode execution");
    }
    
    private void determineValidity()
    {
        if(!(myComputer.getInstructionRegister().equals("GD") || myComputer.getInstructionRegister().equals("SR")))
        {
   //         program.addToTrace("...Page fault is invalid");
            program.setExitCode("Invalid Page Fault");
            myComputer.breakExecution();
            myComputer.setPI(3);
        }
//        else
 //           program.addToTrace("...Page fault is valid");
    }
    
    private void loadDataCard()
    {
        GetDataException e = (GetDataException)interrupt;
   //     String s = myComputer.getProgram().getNextDataLine();
/*        
        if(s == null)
        {
            myComputer.breakExecution();
            program.setExitCode("Out of Data Cards");
            this.handleInterrupt(new OutOfDataCardsException());
        }
        else      
           myComputer.getProgramMemory().setBlock(e.getOperand(), s);*/
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
    
    public void handleChannelOne()
    {
        
        if(myComputer.getCh1().isReady())
        {
             myComputer.addToInputfulBufferQueue(myComputer.getCh1().getBuffer());
       //      System.out.println("handle channel 1");
        }
        
        if(myComputer.getCh1().hasCards() && myComputer.hasBuffer())
        {  
            myComputer.getCh1().setBuffer(myComputer.getBuffer());
            myComputer.getCh1().start();
        }
    }
    
    public void handleChannelThree()
    {
        if(myComputer.getInputfulBuffers().peek() != null && myComputer.getMyDrum().hasTrack())
        {
            myComputer.getCh3().inputSpool(myComputer.getInputfulBuffers().pop(), myComputer.getMyDrum().getTrack());
            myComputer.releaseBuffer();
        }
        else if(myComputer.getCh3().getTerminatingPCB() != null && myComputer.hasBuffer())
        {
            myComputer.getCh3().resumeOutputSpool(myComputer.getBuffer());
        }
        else if(myComputer.getTerminateQueue().peek() != null && myComputer.hasBuffer())
        {
          myComputer.getCh3().outputSpool(myComputer.getTerminateQueue().pop(),myComputer.getBuffer());
        }
        else if (myComputer.getSI() == 1 )
            myComputer.getCh3().read();
        else if (myComputer.getSI() == 2 && myComputer.getMyDrum().hasTrack())
            myComputer.getCh3().write(myComputer.getMyDrum().getTrack());
        else if (myComputer.getSI() == 2 && !myComputer.getMyDrum().hasTrack())
            myComputer.decrementInstructionCounter();
    }
    
    public void handleChannelTwo()
    {
        if(myComputer.getOutputfulBuffers().peek() != null)
        {
            myComputer.getCh2().startChannelTwo(myComputer.getOutputfulBuffers().pop());
            myComputer.releaseBuffer();
        }
    }
    
}
