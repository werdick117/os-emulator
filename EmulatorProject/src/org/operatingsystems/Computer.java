/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.LinkedList;
import org.operatingsystems.exceptions.BaseException;
import org.operatingsystems.exceptions.GetDataException;
import org.operatingsystems.exceptions.HaltException;
import org.operatingsystems.exceptions.InvalidOperandException;
import org.operatingsystems.exceptions.InvalidOperationException;
import org.operatingsystems.exceptions.PrintDataException;
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
        return program;
    }

    public void setProgram(PCB program) {
        this.program = program;
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
    private String instructionRegister;
    private PCB program;
    private OperatingSystem os;
    private boolean breakExecution;
    private LinkedList<String[]> buffers;
    
    public Computer() {
        os = new OperatingSystem(this);
        computerMemory = new MainMemory();
    }
    
    private void initExecutionEnvironment(PCB myProgram) {
        buffers = new LinkedList<String[]>();
        for(int i = 0; i < 10; i++)
            buffers.add(new String[10]);
        
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
        program = myProgram;
        programMemory = new VirtualMemory(computerMemory);
        loadProgram();
    }
    
    public boolean hasBuffer()
    {
        if(buffers.peek() != null)
            return true;
        
        return false;
    }

    public String[] getBuffer()
    {
        return buffers.pop();
    }
    
    public void releaseBuffer()
    {
        buffers.add(new String[10]);
    }
    
    /**
     * Executes the given instruction
     * @param line - The instruction to be executed
     */
    private void executeInstruction() {
        String instruction = instructionRegister.substring(0, 2);
        
        program.addToTrace("Executing instruction: " + instructionRegister);
        
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
            program.setExitCode("Invalid Operand");
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
            program.setExitCode("Invalid Operation");
            throw new InvalidOperationException();
        }
    }
    
    private void loadProgram() {
        for (int i = 0; i < program.instructions.size(); i ++)
            programMemory.setBlock(i*10, program.instructions.get(i));
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
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
        do
        {
            try
            {
                program.addToTrace("\n\n***Cycle: " + os.getExecutionTime() + "***");
                program.addToTrace("Slave Mode Execution");
              // Reset interrupt registers
                TI = 0;
                SI = 0;
                PI = 0;
              
              os.startNextCycle();
            
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
            
            
            program.addToTrace("\nFinal cycle salues of registers");
            program.addToTrace("IC: " + instructionCounter +  "   IR: " + instructionRegister + "   R: " + accumulator + "   C: " + toggle);
            program.addToTrace("SI: " + SI + "   PI: " + PI + "   TI: " + TI);
            
        }while(!breakExecution);
        
        
        myProgram.setPrintQueue(0, "id: " + program.getId() + "      " + program.getExitCode());
        myProgram.setPrintQueue(1, "IC: " + instructionCounter + "    IR: " + instructionRegister + "    R: " + 
                accumulator + "    C: " + toggle + "    time: " + os.getExecutionTime() + "    lines printed: " + os.getLinesPrinted());
        
        myProgram.addToQueue("    ");
        myProgram.addToQueue("    ");
        
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
}
