/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.channels;

import org.operatingsystems.Computer;
import org.operatingsystems.PCB;

/**
 *
 * @author Steve
 */
public class ChannelThree {
    
    protected int timer;
    protected boolean busy;
    protected boolean interruptRaised;
    protected Computer myComputer;
    protected PCB terminatingPCB;
    protected PCB generatingPCB;
    protected boolean generatingPCBReady;
    protected char incomingCards;
    
    public ChannelThree(Computer c)
    {
        this.generatingPCBReady = false;
        this.myComputer = c;
        this.timer = 0;
        this.busy = false;
        this.interruptRaised = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isInterruptRaised() {
        return interruptRaised;
    }

    public void setInterruptRaised(boolean interruptRaised) {
        this.interruptRaised = interruptRaised;
    }
    
    public void load()
    {
        
    }
    
    public void checkTimer()
    {
        if(timer == 2)
        {
            interruptRaised = true;
            busy = false;
        }
        else
        {
            interruptRaised = false;
            busy = true;
            timer++;
        }
    }

    public PCB getTerminatingPCB() {
        return terminatingPCB;
    }

    
    public boolean isGeneratingPCBReady() {
        return generatingPCBReady;
    }

    public void setGeneratingPCBReady(boolean generatingPCBReady) {
        this.generatingPCBReady = generatingPCBReady;
    }
    
    public void setTerminatingPCB(PCB terminatingPCB) {
        this.terminatingPCB = terminatingPCB;
    }
    
    public void outputSpool(PCB p, String[] buffer)
    {
        this.terminatingPCB = p;
        this.terminatingPCB.terminate();
        
       buffer = myComputer.getMyDrum().readFromDrum(this.terminatingPCB.getNextPrintLine());
       myComputer.getOutputfulBuffers().add(buffer);
       
       if(!this.terminatingPCB.hasMorePrintLines())
           this.terminatingPCB = null;
    }
    
    public void resumeOutputSpool(String[] buffer)
    {
        buffer = myComputer.getMyDrum().readFromDrum(this.terminatingPCB.getNextPrintLine());        
        myComputer.getOutputfulBuffers().add(buffer);
        
        if(!this.terminatingPCB.hasMorePrintLines())
           this.terminatingPCB = null;
    }
    
    public void read()
    {
        
        int i = myComputer.getRunningProgram().getNextDataLine();
        String[] block = myComputer.getMyDrum().readFromDrum(i);
        
        int j = 0;
        String output = "";
        String word;
        
        while(j < 10) {
                word = block[j++];
                if (word == null)
                    output += "    ";
                else
                    output += word;
            }
        
        myComputer.getProgramMemory().setBlock(myComputer.getGetOperand(), output);
        myComputer.setSI(0);
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
    
    public void write(int track)
    {

            String[] myBlock = myComputer.getProgramMemory().getBlock(myComputer.getPrintOperand());
            
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
            
            String[] line = new String[10];
            line[0] = output;
            
            
                myComputer.getMyDrum().writeToDrum(line, track);
                myComputer.getRunningProgram().addToPrintQueue(track);
            
                myComputer.setSI(0);
/*
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
            */
        
    }

    
    public void inputSpool(String[] buffer, int track)
    {
        /*
        for(String s : buffer)
            System.out.print(s);
        System.out.println();
        */
        
        if(buffer[0].equals("$AMJ"))
        {
            // make the new PCB object
            
            int timeLimit;
            int lineLimit;
            String id;
            
            id        = buffer[1];
            timeLimit = Integer.parseInt(buffer[2]);
            lineLimit = Integer.parseInt(buffer[3]);
            
            generatingPCBReady = false;
            this.incomingCards = 'I';
            generatingPCB = new PCB(id,timeLimit,lineLimit, myComputer.getComputerMemory(), myComputer);
            
            return;
            
        }else if (buffer[0].equals("$DTA"))
        {
            this.incomingCards = 'D';
            
            return;
        }
        else if (buffer[0].equals("$EOJ"))
        {
            generatingPCB.finish();
            
            System.out.println("finished generating PCB: " + generatingPCB.getId());
            if(myComputer.getReadyQueue().peek() == null)
                myComputer.setRunningProgram(generatingPCB);
            else
                myComputer.getReadyQueue().add(generatingPCB);
            
            
            
            this.generatingPCB = null;     
            
            return;
        }
        
        
        switch (this.incomingCards)
        {
            case 'I':
                generatingPCB.addInstruction(track);
                myComputer.getMyDrum().writeToDrum(buffer, track);
                break;
            case 'D':
                generatingPCB.addData(track);
                myComputer.getMyDrum().writeToDrum(buffer, track);
                break;
        }
        
    }

    public PCB getGeneratingPCB() {
        return generatingPCB;
    }

    public void setGeneratingPCB(PCB generatingPCB) {
        this.generatingPCB = generatingPCB;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    
}
