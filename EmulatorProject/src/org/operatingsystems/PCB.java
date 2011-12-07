/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.operatingsystems.memory.MainMemory;
import org.operatingsystems.memory.VirtualMemory;

/**
 *
 * @author Steve
 */
public class PCB {
    LinkedList<Integer> data;
    LinkedList<Integer> instructions;
    
    String id;
    ArrayList<Integer> printQueue;
    String exitCode;
    
    int lineLimit;
    int timeLimit;
    int timeRun;
    
    private int SI;
    private int TI;
    private int PI;
    
    int instructionCounter;
    String instructionRegister;
    String accumulator;
    
    private String savedAccumulator;
    private boolean savedToggle;
    private int savedInstructionCounter;
    private int executionTime;
    private int linesPrinted;
    private boolean toggle;
    
    private Iterator<Integer> dataItr;
    private Iterator<Integer> instructionsItr;
    private Iterator<Integer> printQueueItr;
    
    private String headerLineOne;
    private String headerLineTwo;

    private VirtualMemory myMemory;
    
    private Computer myComputer;
    
    boolean alreadyLoaded;
    ArrayList<Integer> usedFrames;
    
    
    public PCB(String i, int t, int l, MainMemory m, Computer c)
    {
        this.id = i;
        this.myComputer = c;
        this.timeLimit = t;
        this.lineLimit = l;
        this.myMemory = new VirtualMemory(m);
        exitCode = "Normal Termination";
        printQueue = new ArrayList<Integer>();
        data = new LinkedList<Integer>();
        instructions = new LinkedList<Integer>();
        this.printQueueItr = null;
        this.timeRun = 0;
        alreadyLoaded = false;
        
        SI = 0;
        TI = 0;
        PI = 0;
        accumulator = "    ";
        instructionRegister = "    ";
        instructionCounter = 0;
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
    
    public void incrementTimeRun()
    {
        this.timeRun++;
    }
    
    public void terminate()
    {
        
        this.printQueueItr = printQueue.iterator();
        
        
        System.out.println();
        System.out.println();
        System.out.println("id: " + id + "      " + exitCode);
        System.out.println("IC: " + instructionCounter + "    IR: " + instructionRegister + "    R: " + accumulator + "    C: " + toggle + "    time: " + timeRun + "    lines printed: " + linesPrinted);
        System.out.println("----------------------------------------------------------------------------------\n");
        
    }
    
    public void finish()
    {
        this.dataItr = this.data.iterator();
        this.instructionsItr = instructions.iterator();
        
        String[] temp;
        
        int j = 0;
        
        while(instructionsItr.hasNext())
        {
            temp = myComputer.getMyDrum().readFromDrum(instructionsItr.next());
            
            int i = 0;
            String output = "";
            String word;

            while(i < 10) {
                word = temp[i++];
               if (word == null)
                  output += "    ";
               else
                 output += word;
              }
            
            myMemory.setBlock(j*10, output);
            
            j++;
        } 
        
    }
    
    public boolean hasMorePrintLines()
    {
        return this.printQueueItr.hasNext();
    }
    
    public int getNextPrintLine()
    {
        return this.printQueueItr.next();
    }
    
    public int getNextDataLine()
    {
        return this.dataItr.next();
    }
    
    public boolean hasMoreDataLines()
    {
        return this.dataItr.hasNext();
    }
    
    public int startPrinting()
    {
        this.printQueueItr = this.printQueue.iterator();
        return this.printQueueItr.next();
    }

    
    public void addToPrintQueue(int i)
    {
        printQueue.add(i);
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String s)
    {
        id = s.substring(4,8);
        timeLimit = Integer.parseInt(s.substring(8,12));
        lineLimit = Integer.parseInt(s.substring(12,16));
    }
    
    public void addInstruction(int s)
    {
        instructions.add(s);
    }
    
    public void addData(int s)
    {
        data.add(s);
    }
    
    public int getLineLimit() {
        return lineLimit;
    }

    public void setLineLimit(int lineLimit) {
        this.lineLimit = lineLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public boolean isAlreadyLoaded() {
        return alreadyLoaded;
    }

    public void setAlreadyLoaded(boolean alreadyLoaded) {
        this.alreadyLoaded = alreadyLoaded;
    }

    public LinkedList<Integer> getData() {
        return data;
    }

    public void setData(LinkedList<Integer> data) {
        this.data = data;
    }

    public String getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(String accumulator) {
        this.accumulator = accumulator;
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

    public boolean getToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public Iterator<Integer> getDataItr() {
        return dataItr;
    }

    public void setDataItr(Iterator<Integer> dataItr) {
        this.dataItr = dataItr;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public String getHeaderLineOne() {
        return headerLineOne;
    }

    public void setHeaderLineOne(String headerLineOne) {
        this.headerLineOne = headerLineOne;
    }

    public String getHeaderLineTwo() {
        return headerLineTwo;
    }

    public void setHeaderLineTwo(String headerLineTwo) {
        this.headerLineTwo = headerLineTwo;
    }

    public LinkedList<Integer> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Integer> instructions) {
        this.instructions = instructions;
    }

    public Iterator<Integer> getInstructionsItr() {
        return instructionsItr;
    }

    public void setInstructionsItr(Iterator<Integer> instructionsItr) {
        this.instructionsItr = instructionsItr;
    }

    public int getLinesPrinted() {
        return linesPrinted;
    }

    public void setLinesPrinted(int linesPrinted) {
        this.linesPrinted = linesPrinted;
    }

    public VirtualMemory getMyMemory() {
        return myMemory;
    }

    public void setMyMemory(VirtualMemory myMemory) {
        this.myMemory = myMemory;
    }

    public ArrayList<Integer> getPrintQueue() {
        return printQueue;
    }

    public void setPrintQueue(ArrayList<Integer> printQueue) {
        this.printQueue = printQueue;
    }

    public Iterator<Integer> getPrintQueueItr() {
        return printQueueItr;
    }

    public void setPrintQueueItr(Iterator<Integer> printQueueItr) {
        this.printQueueItr = printQueueItr;
    }

    public String getSavedAccumulator() {
        return savedAccumulator;
    }

    public void setSavedAccumulator(String savedAccumulator) {
        this.savedAccumulator = savedAccumulator;
    }

    public int getSavedInstructionCounter() {
        return savedInstructionCounter;
    }

    public void setSavedInstructionCounter(int savedInstructionCounter) {
        this.savedInstructionCounter = savedInstructionCounter;
    }

    public boolean isSavedToggle() {
        return savedToggle;
    }

    public void setSavedToggle(boolean savedToggle) {
        this.savedToggle = savedToggle;
    }

    public int getTimeRun() {
        return timeRun;
    }

    public void setTimeRun(int timeRun) {
        this.timeRun = timeRun;
    }

    public ArrayList<Integer> getUsedFrames() {
        return usedFrames;
    }

    public void setUsedFrames(ArrayList<Integer> usedFrames) {
        this.usedFrames = usedFrames;
    }
    
    
}
