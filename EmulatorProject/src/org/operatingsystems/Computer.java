/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.operatingsystems.channels.ChannelOne;
import org.operatingsystems.channels.ChannelThree;
import org.operatingsystems.channels.ChannelTwo;
import org.operatingsystems.exceptions.BaseException;
import org.operatingsystems.exceptions.GetDataException;
import org.operatingsystems.exceptions.HaltException;
import org.operatingsystems.exceptions.InvalidOperandException;
import org.operatingsystems.exceptions.InvalidOperationException;
import org.operatingsystems.exceptions.PrintDataException;
import org.operatingsystems.exceptions.TimeLimitException;
import org.operatingsystems.memory.VirtualMemory;
import org.operatingsystems.memory.MainMemory;

/**
 *
 * @author Steve
 */
public class Computer {

    public String getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(String accumulator) {
        if(accumulator != null)
            this.accumulator = accumulator;
        else
            this.accumulator = "    ";
    }

    public MainMemory getComputerMemory() {
        return computerMemory;
    }

    public void setComputerMemory(MainMemory computerMemory) {
        this.computerMemory = computerMemory;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getInstructionCounter() {
        return instructionCounter;
    }

    public void setInstructionCounter(int instructionCounter) {
        this.instructionCounter = instructionCounter;
    }

    public String getInstructionRegister() {
        return instructionRegister;
    }

    public void setInstructionRegister(String instructionRegister) {
        this.instructionRegister = instructionRegister;
    }

    public int getLinesPrinted() {
        return linesPrinted;
    }

    public void setLinesPrinted(int linesPrinted) {
        this.linesPrinted = linesPrinted;
    }

    public OperatingSystem getOs() {
        return os;
    }

    public void setOs(OperatingSystem os) {
        this.os = os;
    }

    public PCB getProgram() {
        return runningProgram;
    }

    public void setProgram(PCB program) {
        this.runningProgram = program;
    }

    public VirtualMemory getProgramMemory() {
        return programMemory;
    }

    public void setProgramMemory(VirtualMemory programMemory) {
        this.programMemory = programMemory;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }
    

    public int getPI() {
        return PI;
    }

    public void setPI(int PI) {
        this.PI = PI;
    }

    public int getSI() {
        return SI;
    }

    public void setSI(int SI) {
        this.SI = SI;
    }

    public int getTI() {
        return TI;
    }

    public void setTI(int TI) {
        this.TI = TI;
    }
    
    private int PI;
    private int SI;
    private int TI;
    private MainMemory computerMemory;
    private VirtualMemory programMemory;
    private String accumulator;
    private boolean toggle;
    private int instructionCounter;
    private int executionTime;
    private int linesPrinted;
    private int printOperand;
    private int getOperand;
    private String instructionRegister;
    private PCB runningProgram;
    private OperatingSystem os;
    private boolean breakExecution;
    private LinkedList<String[]> emptyBuffers;
    private LinkedList<String[]> inputfulBuffers;
    private LinkedList<String[]> outputfulBuffers;
    private ChannelOne ch1;
    private ChannelTwo ch2;
    private ChannelThree ch3;   
    private LinkedList<PCB> readyQueue;

    public int getPrintOperand() {
        return printOperand;
    }

    public int getGetOperand() {
        return getOperand;
    }

    public void setGetOperand(int getOperand) {
        this.getOperand = getOperand;
    }

    public PCB getRunningProgram() {
        return runningProgram;
    }

    public void setRunningProgram(PCB runningProgram) {
        this.runningProgram = runningProgram;
        this.programMemory = runningProgram.getMyMemory();
    }

    public void setPrintOperand(int printOperand) {
        this.printOperand = printOperand;
    }

    
    public Drum getMyDrum() {
        return myDrum;
    }

    public ChannelOne getCh1() {
        return ch1;
    }

    public void setCh1(ChannelOne ch1) {
        this.ch1 = ch1;
    }

    public ChannelTwo getCh2() {
        return ch2;
    }

    public void setCh2(ChannelTwo ch2) {
        this.ch2 = ch2;
    }

    public ChannelThree getCh3() {
        return ch3;
    }

    public void setCh3(ChannelThree ch3) {
        this.ch3 = ch3;
    }

    public void setMyDrum(Drum myDrum) {
        this.myDrum = myDrum;
    }
    private LinkedList<PCB> terminateQueue;
    private int timer;
    private int timeSlice;
    private Drum myDrum;
    
    public Computer(Iterator i) {
        ch1 = new ChannelOne(i);
        ch2 = new ChannelTwo();
        ch3 = new ChannelThree(this);
        
        myDrum = new Drum();
        
        timer = 0;
        timeSlice = 0;
        
        instructionRegister = "    ";
        
        emptyBuffers = new LinkedList<String[]>();
        for(int j = 0; j < 10; j++)
            emptyBuffers.add(new String[10]);
        
        inputfulBuffers = new LinkedList<String[]>();
        outputfulBuffers = new LinkedList<String[]>();
        
        readyQueue = new LinkedList<PCB>();
        terminateQueue = new LinkedList<PCB>();
        
        os = new OperatingSystem(this);
        computerMemory = new MainMemory();
        
        beginExecution();
    }
    
    private void initExecutionEnvironment(PCB myProgram) {
        emptyBuffers = new LinkedList<String[]>();
        for(int i = 0; i < 10; i++)
            emptyBuffers.add(new String[10]);
        
        breakExecution = false;
        toggle = false;
        os.setExecutionTime(0);
        os.setLinesPrinted(0);
        instructionCounter = 0;
        accumulator = "    ";
        instructionRegister = "    ";
        PI = 0;
        SI = 0;
        TI = 0;
        runningProgram = myProgram;
        programMemory = new VirtualMemory(computerMemory);
        loadProgram();
    }
    
    public boolean hasBuffer()
    {
        if(emptyBuffers.peek() != null)
            return true;
        
        return false;
    }

    public String[] getBuffer()
    {
        return emptyBuffers.pop();
    }
    
    public void releaseBuffer()
    {
        emptyBuffers.add(new String[10]);
    }
    
    /**
     * Executes the given instruction
     * @param line - The instruction to be executed
     */
    private void executeInstruction() {
        String instruction = instructionRegister.substring(0, 2);
        
//        program.addToTrace("Executing instruction: " + instructionRegister);
        
        
        if(instruction.equals("H "))
            throw new HaltException();
        
        int operand;
        
        try
        {
            operand = Integer.parseInt(instructionRegister.substring(2));
        //    System.out.println(instructionRegister);
        }
        catch(Exception e)
        {
            runningProgram.setExitCode("Invalid Operand");
            throw new InvalidOperandException();
        }
        
        
        if (instruction.equals("LR")) {
            setAccumulator(programMemory.getWord(operand));
        } else if (instruction.equals("SR")) {
            programMemory.setWord(operand, accumulator);
        } else if (instruction.equals("CR")) {
            toggle = accumulator.equals(programMemory.getWord(operand));
        } else if (instruction.equals("BT")) {
            if (toggle) {
                instructionCounter = operand;
            }
        } else if (instruction.equals("GD")) {
            //programMemory.setBlock(operand, program.getNextDataLine());
            throw new GetDataException(operand);
        } else if (instruction.equals("PD")) {
            throw new PrintDataException(operand);
        }
        else
        {
            runningProgram.setExitCode("Invalid Operation");
            throw new InvalidOperationException();
        }
    }

    public LinkedList<String[]> getEmptyBuffers() {
        return emptyBuffers;
    }

    public void setEmptyBuffers(LinkedList<String[]> emptyBuffers) {
        this.emptyBuffers = emptyBuffers;
    }

    public LinkedList<String[]> getInputfulBuffers() {
        return inputfulBuffers;
    }

    public void setInputfulBuffers(LinkedList<String[]> inputfulBuffers) {
        this.inputfulBuffers = inputfulBuffers;
    }

    public LinkedList<String[]> getOutputfulBuffers() {
        return outputfulBuffers;
    }

    public void setOutputfulBuffers(LinkedList<String[]> outputfulBuffers) {
        this.outputfulBuffers = outputfulBuffers;
    }

    public LinkedList<PCB> getReadyQueue() {
        return readyQueue;
    }

    public void setReadyQueue(LinkedList<PCB> readyQueue) {
        this.readyQueue = readyQueue;
    }
    
    private void loadProgram() {
       // for (int i = 0; i < program.instructions.size(); i ++)
//            programMemory.setBlock(i*10, program.instructions.get(i));
    }
    
    /**
     * Wraps program run to ensure memory is released
     *      This is an ad-hoc implementation meant to be a quick fix
     * @param myProgram 
     */
    public void wrapExecution(PCB myProgram) {
        this.initExecutionEnvironment(myProgram);
        try {
            this.runProgram(myProgram);
        } finally {
            this.releaseProgramMemory();
        }
    }

    
    /**
     * Runs the program represented by the text file
     * @param program - The program to be run
     */
    public void runProgram(PCB myProgram) {
        
      
        
        // Reserving space at the top for the program header
//         myProgram.addToQueue("    ");
    //     myProgram.addToQueue("    ");
        
    //     myProgram.addToQueue("    ");
   //      myProgram.addToQueue("    ");
        
        do
        {
            try
            {
//                program.addToTrace("\n\n***Cycle: " + os.getExecutionTime() + "***");
 //               program.addToTrace("Slave Mode Execution");
              // Reset interrupt registers
                TI = 0;
                SI = 0;
                PI = 0;
              
    //          os.startNextCycle();
            
              // Fetch
              instructionRegister = programMemory.getWord(instructionCounter);
            
              // Increment
              instructionCounter++;   
            
              // Execute


              this.executeInstruction();
                
            }catch(BaseException e)
            {
                // Interrupt
                os.handleInterrupt(e);
            }
            
            
  //          program.addToTrace("\nFinal cycle salues of registers");
 //           program.addToTrace("IC: " + instructionCounter +  "   IR: " + instructionRegister + "   R: " + accumulator + "   C: " + toggle);
  //          program.addToTrace("SI: " + SI + "   PI: " + PI + "   TI: " + TI);
            
        }while(!breakExecution);
        
        
//        myProgram.setPrintQueue(0, "id: " + program.getId() + "      " + program.getExitCode());
 //       myProgram.setPrintQueue(1, "IC: " + instructionCounter + "    IR: " + instructionRegister + "    R: " + 
 //               accumulator + "    C: " + toggle + "    time: " + os.getExecutionTime() + "    lines printed: " + os.getLinesPrinted());
        
 //       myProgram.addToQueue("    ");
 //       myProgram.addToQueue("    ");
        
      //  myProgram.printAll();
    }
    
    private String determineExitCode()
    {
        if(TI != 0)
            return "Time Limit Exceeded";
        else if(PI != 0)
            return "PI" + PI;
        else if(SI == 1 || SI == 2 || SI == 4)
            return "SI" + SI;
        else
            return "Normal Termination";
    }
    
    /**
     * This method is just so that the OS object can
     * be responsible for recycling the frame.  That 
     * way the Halt interrupt actually does something
     */
    public void releaseProgramMemory()
    {
        this.programMemory.recycle();
    }
    
    public void breakExecution()
    {
        this.breakExecution = true;
    }
    
    public void beginExecution()
    {
        System.out.println("begin execution");
        while(readyQueue.peek() != null | terminateQueue.peek() != null | ch1.hasCards() | ch3.getGeneratingPCB() != null | this.runningProgram != null
                | this.getOutputfulBuffers().peek() != null | this.getInputfulBuffers().peek() != null)
        {
            
          //  System.out.println("    TimeSlice: " + timeSlice);
            
            ch1.checkTimer();
            ch2.checkTimer();
            ch3.checkTimer();
            
            if(programMemory != null)
              instructionRegister = programMemory.getWord(instructionCounter);
            
            if(!instructionRegister.equals("    ") && timeSlice < 10)
            {
               
                try
                {
                    timeSlice++;
                    runningProgram.incrementTimeRun();

                    // Fetch
                    instructionRegister = programMemory.getWord(instructionCounter);
  
               //     System.out.println("        Instruction: " + instructionRegister);
                    
                    // Increment
                    instructionCounter++;   
                    this.executeInstruction();
                    
                    if(runningProgram.getTimeRun() > runningProgram.getTimeLimit())
                        throw new TimeLimitException();
                    
                }catch(BaseException e)
                {
                    // Interrupt
                    os.handleInterrupt(e);
                }
            
            }
            else if (runningProgram != null)
                swapProgram();
            
            handleInterrupts();
            
 //           System.out.println(readyQueue.peek() + "     " + terminateQueue.peek() + "     " + ch1.hasCards());
        }
    }
    
    private void swapProgram()
    {
        System.out.println("swap");
        runningProgram.setAccumulator(accumulator);
        runningProgram.setToggle(toggle);
        runningProgram.setInstructionCounter(instructionCounter);
        runningProgram.setInstructionRegister(instructionRegister);
        runningProgram.setMyMemory(programMemory);
        
        runningProgram.setSI(SI);
        runningProgram.setTI(TI);
        runningProgram.setPI(PI);
        
        
        readyQueue.add(runningProgram);
        runningProgram = readyQueue.pop();
        
        accumulator = runningProgram.getAccumulator();
        toggle = runningProgram.getToggle();
        instructionCounter = runningProgram.getInstructionCounter();
        instructionRegister = runningProgram.getInstructionRegister();
        
        SI = runningProgram.getSI();
        TI = runningProgram.getTI();
        PI = runningProgram.getPI();
        
        programMemory = runningProgram.getMyMemory();
        
        timeSlice = 0;
    }
    
    public LinkedList<PCB> getTerminateQueue() {
        return terminateQueue;
    }

    public void setTerminateQueue(LinkedList<PCB> terminateQueue) {
        this.terminateQueue = terminateQueue;
    }
    
    private void handleInterrupts()
    {
        if(ch1.isInterruptRaised())
            os.handleChannelOne();
        
        if(ch2.isInterruptRaised())
            os.handleChannelTwo();
        
        if(ch3.isInterruptRaised())
            os.handleChannelThree();
        
        if(SI == 2 | SI == 1)
            this.decrementInstructionCounter();
        
        if(SI == 3)
            terminateRunningProgram();
        
    }
    
    private void terminateRunningProgram()
    {   
        terminateQueue.add(runningProgram);
        
        runningProgram.setAccumulator(accumulator);
        runningProgram.setToggle(toggle);
        runningProgram.setInstructionCounter(instructionCounter);
        runningProgram.setInstructionRegister(instructionRegister);
        
        runningProgram.setSI(SI);
        runningProgram.setTI(TI);
        runningProgram.setPI(PI);
        
        runningProgram.getMyMemory().recycle();
        
        runningProgram = null;
        programMemory = null;
        
        
        instructionRegister = "    ";
        SI = 0;
        TI = 0;
        PI = 0;
        instructionCounter = 0;
        toggle = false;
        
    }
    
    public void decrementInstructionCounter()
    {
        this.instructionCounter--;
    }
    
    public void addToInputfulBufferQueue(String[] s)
    {
        inputfulBuffers.add(s);
    }
}
