/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Steve
 */
public class Program {
    LinkedList<String> data;
    ArrayList<String> instructions;
    String id;
    ArrayList<String> printQueue;
    int lineLimit;
    int timeLimit;
    
    Program()
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
        id = s;
    }
    
    public String getInstruction(int index)
    {
        return instructions.get(index);
    }
    
    public String getNextDataLine()
    {
        return data.pop();
    }
    
    public void setData(LinkedList<String> l)
    {
        data = l;
    }
    
    public void setInstructions(ArrayList<String> l)
    {
        instructions = l;
    }
    
}
