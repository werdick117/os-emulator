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
    
    public void startChannelTwo()
    {
        this.busy = true;
        
        for(int i = 0; i < buffer.length; i++)
            if(buffer[i] != null)
                System.out.println(buffer[i]);
        
        this.busy = false;
    }
}