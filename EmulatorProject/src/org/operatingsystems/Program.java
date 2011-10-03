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
public class Program {
    LinkedList<String> data;
    ArrayList<String> instructions;
    String id;
    ArrayList<String> printQueue;
    String exitCode;
    int lineLimit;
    int timeLimit;
    
    public Program()
    {
        printQueue = new ArrayList<String>();
        data = new LinkedList<String>();
        instructions = new ArrayList<String>();
        id = "    ";
    }
    
    public void printAll()
    {
        for(String s : printQueue)
            System.out.println(s);
    }
    
    public void addToQueue(String s)
    {
        printQueue.add(s);
    }
    
    public void setPrintQueue(int i, String s)
    {
        printQueue.set(i,s);
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
    
    public String getNextDataLine()
    {
        if(data.peek() == null)
            return null;
        
        return data.pop();
    }
    
    public void setData(LinkedList<String> l)
    {
        data = l;
    }
    
    public void setInstructions(List<String> l)
    {
        instructions = new ArrayList<String>();
        
        for(String s : l)
        {
            while(s.length() < 40)
                s = s + " ";

            instructions.add(s);
        }
    }
    
    public void addInstruction(String s)
    {
        instructions.add(s);
    }
    
    public void addData(String s)
    {
        data.add(s);
    }
    
    public ArrayList<String> getInstructions()
    {
        return instructions;
    }
    
    public void printInstructions()
    {
        for(String s: instructions)
            System.out.println(s);
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
    
    
}
