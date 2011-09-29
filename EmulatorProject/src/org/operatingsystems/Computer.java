/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import org.operatingsystems.memory.VirtualMemory;
import org.operatingsystems.memory.MainMemory;

/**
 *
 * @author Steve
 */
public class Computer {

    private MainMemory computerMemory;
    private VirtualMemory programMemory;
    private String accumulator;
    private boolean toggle;
    private int instructionCounter;
    private int executionTime;
    private int linesPrinted;
    private String instructionRegister;
    private Program program;

    public Computer() {
        computerMemory = new MainMemory();
    }
    
    private void initExecutionEnvironment(Program myProgram) {
        toggle = false;
        executionTime = 0;
        linesPrinted = 0;
        instructionCounter = 0;
        accumulator = "    ";
        instructionRegister = "    ";
        
        program = myProgram;
        programMemory = new VirtualMemory(computerMemory);
        loadProgram();
    }

    /**
     * Executes the given instruction
     * @param line - The instruction to be executed
     */
    private void executeInstruction() {
        String instruction = instructionRegister.substring(0, 2);
        int operand = Integer.parseInt(instructionRegister.substring(2));

        if (instruction.equals("LR")) {
            accumulator = programMemory.getWord(operand);
        } else if (instruction.equals("SR")) {
            programMemory.setWord(operand, accumulator);
        } else if (instruction.equals("CR")) {
            toggle = accumulator.equals(programMemory.getWord(operand));
        } else if (instruction.equals("BT")) {
            if (toggle) {
                instructionCounter = operand;
            }
        } else if (instruction.equals("GD")) {
            programMemory.setBlock(operand, program.getNextDataLine());
        } else if (instruction.equals("PD")) {
            linesPrinted++;
            String[] myBlock = programMemory.getBlock(operand);
            int i = 0;
            String output = "";
            String word;
            
            while((word = myBlock[i++]) != null)
                output += word;
            
            program.addToQueue(output);
        } 
    }
    
    private void loadProgram() {
        for (int i = 0; i < program.instructions.size(); i ++)
            programMemory.setBlock(i*10, program.instructions.get(i));
    }

    /**
     * Runs the program represented by the text file
     * @param program - The program to be run
     */
    public void runProgram(Program myProgram) {
        this.initExecutionEnvironment(myProgram);

        myProgram.addToQueue("id: " + program.getId() + "    exit code: normal termination");
         myProgram.addToQueue("    ");
        
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
        while (!(instructionRegister = programMemory.getWord(instructionCounter)).equals("H   ")) {
            instructionCounter++;
            executionTime++;
            this.executeInstruction();
        }
        
        myProgram.setPrintQueue(1, "IC: " + instructionCounter + "    IR: " + instructionRegister + "    R: " + 
                accumulator + "    C: " + toggle + "    time: " + executionTime + "    lines printed: " + linesPrinted);
        
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
        myProgram.printAll();
        this.programMemory.recycle();
    }
}
