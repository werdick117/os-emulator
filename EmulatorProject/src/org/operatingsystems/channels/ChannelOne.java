/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.channels;

import java.util.Iterator;

/**
 *
 * @author Steve
 */
public class ChannelOne {
    
    private boolean interruptRaised;
    private int timer;
    
    private Iterator<String> cardReader;
    private String[] buffer;
    private boolean isReady;
    
    public ChannelOne(Iterator i)
    {
        isReady = false;
        interruptRaised = false;
        timer = 0;
        this.cardReader = i;
    }
    
    public boolean hasCards()
    {
        return cardReader.hasNext();
    }

    public void checkTimer()
    {
        if(timer == 5)
        {
            interruptRaised = true;
        }
        else
        {
            timer++;
        }
    }
    
    public void start()
    {
        interruptRaised = false;
        timer = 0;
        
        String temp = cardReader.next();
        
   //     System.out.println("read card: " + temp);
        while(temp.length() < 40)
            temp += " ";
        
        for(int i = 0; i < 10; i++)
        {
            buffer[i] = temp.substring(0,4); 
            temp = temp.substring(4);
        }
        
        
        
        isReady = true;
    }
    
    /**
     * 
     * @return The channel's buffer and resets isReady so that
     * on future cycles the buffer isn't picked up prematurely
     */
    public String[] getBuffer() {
        String[] b = buffer;
        buffer = null;
        
        isReady = false;
        
        return b;
    }

    public void setBuffer(String[] buffer) {
        this.buffer = buffer;
    }

    public Iterator getCardReader() {
        return cardReader;
    }

    public void setCardReader(Iterator cardReader) {
        this.cardReader = cardReader;
    }
    
    public boolean isInterruptRaised() {
        return interruptRaised;
    }

    public void setInterruptRaised(boolean interruptRaised) {
        this.interruptRaised = interruptRaised;
    }
    
    public boolean isReady()
    {
        return isReady;
    }
}
