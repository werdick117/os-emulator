/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Steve
 */
public class Computer {

    private MainMemory myMemory;
    private String accumulator;
    private boolean toggle;
    private int instructionCounter;
    private int executionTime;
    private int linesPrinted;
    private String instructionRegister;
    private Program program;

    public Computer() {
        myMemory = new MainMemory();
        toggle = false;
        executionTime = 0;
        linesPrinted = 0;
        instructionCounter = 0;
        accumulator = "    ";
        instructionRegister = "    ";
        program = null;

    }

    /**
     * Executes the given instruction
     * @param line - The instruction to be executed
     */
    private void executeInstruction() {
        String instruction = instructionRegister.substring(0, 2);
        int operand = Integer.parseInt(instructionRegister.substring(2));

        if (instruction.equals("LR")) {
            accumulator = myMemory.getWord(operand);
        } else if (instruction.equals("SR")) {
            myMemory.setWord(operand, accumulator);
        } else if (instruction.equals("CR")) {
            toggle = accumulator.equals(myMemory.getWord(operand));
        } else if (instruction.equals("BT")) {
            if (toggle) {
                instructionCounter = operand;
            }
        } else if (instruction.equals("GD")) {
            myMemory.setBlock(operand, program.getNextDataLine());
        } else if (instruction.equals("PD")) {
            linesPrinted++;
            String[] myBlock = myMemory.getBlock(operand);
            int i = 0;
            String output = "";
            String word;
            
            while((word = myBlock[i++]) != null)
                output += word;
            
            program.addToQueue(output);
        } 
    }

    /**
     * Runs the program represented by the text file
     * @param program - The program to be run
     */
    public void runProgram(Program myProgram) {
        this.program = myProgram;

        myProgram.addToQueue("id: " + program.getId() + "    exit code: normal termination");
         myProgram.addToQueue("    ");
        
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
        while (!(instructionRegister = program.getInstruction(instructionCounter)).equals("H   ")) {
            instructionCounter++;
            executionTime++;
            this.executeInstruction();
        }
        
        myProgram.setPrintQueue(1, "IC: " + instructionCounter + "    IR: " + instructionRegister + "    R: " + 
                accumulator + "    C: " + toggle + "    time: " + executionTime + "    lines printed: " + linesPrinted);
        
         myProgram.addToQueue("    ");
         myProgram.addToQueue("    ");
        
        myProgram.printAll();
    }
}
