/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.memory;

import java.util.Random;

/**
 *
 * @author Steve
 */
public class MainMemory {
    
    private Frame[] memory;
    private Random RNG;
    
    public MainMemory()
    {
        memory = new Frame[30];
        for (int i = 0; i < memory.length; i ++)
            memory[i] = new Frame();
        RNG = new Random();
    }
    
    public Integer nextFrame()
    {
        Frame currentFrame;
        for (int i = 0; i < memory.length; i ++) {
            currentFrame = memory[i];
            if (!currentFrame.isInUse()) {
                currentFrame.reserve();
                return i;
            }
        }
        return null;
    }
    
    public Integer randomFrame() {
        Integer frameIndex;
        Frame frame;
        do {
            frameIndex = RNG.nextInt(20)+10;
            frame = memory[frameIndex];
        } while(frame.isInUse());
        frame.reserve();
        return frameIndex;
    }
    
    public void recycleFrame(int frameIndex) {
        memory[frameIndex].recycle();
    }
    
    public String[] getBlock(int index) {
        return memory[index/10].getBlock();
    }
    
    public String getWord(int index)
    {
        return memory[index/10].getWord(index%10);
    }
    
    public void setBlock(int index, String data)
    {
        memory[index/10].setBlock(data);
    }
    
    public void setWord(int index, String data)
    {
        memory[index/10].setWord(index%10, data);
    }
}
