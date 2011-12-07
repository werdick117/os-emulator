/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.channels;

/**
 *
 * @author Steve
 */
public class ChannelTwo {
    protected int timer;
    protected boolean busy;
    protected String[] buffer;
    protected boolean interruptRaised;
    
    
    
    public String[] getBuffer() {
        return buffer;
    }

    public void setBuffer(String[] buffer) {
        this.buffer = buffer;
    }
    
    public ChannelTwo()
    {
        this.timer = 0;
    }
    
    public void incrementTimer()
    {
        this.timer++;
    }
    
    public void resetTimer()
    {
        this.timer = 0;
    }
    
    public void startChannelTwo(String[] buffer)
    {
        this.busy = true;
        
        System.out.println(buffer[0]);
        
        this.busy = false;
    }
    
    public void checkTimer()
    {
        if(timer == 5)
        {
            busy = false;
            interruptRaised = true;
        }
        else
        {
            busy = false;
            interruptRaised = false;
            timer++;
        }
    }
    
    public boolean isInterruptRaised()
    {
        return interruptRaised;
    }
    
}