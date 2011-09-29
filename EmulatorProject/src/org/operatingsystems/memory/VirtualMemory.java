package org.operatingsystems.memory;

import org.operatingsystems.exceptions.PageFault;

public class VirtualMemory {
    
    private Integer PTR;
    private MainMemory memory;
    
    public VirtualMemory(MainMemory realMemory) 
    {
        this.memory = realMemory;
        this.PTR = memory.nextFrame();
    }
    
    private Integer translateAddress(int index) 
    {
        try {
            return Integer.valueOf(memory.getWord(PTR + index/10).trim())*10 + index%10;
        } catch (final NullPointerException e) {
            throw new PageFault();
        }
    }
    
    private Integer getAddress(int index) 
    {
        Integer realAddress;
        try {
            realAddress = translateAddress(index);
        } catch (final PageFault pf) {
            memory.setWord(PTR + index/10, memory.randomFrame().toString());
            realAddress = translateAddress(index);
        }
        return realAddress;
    }
    
    public String[] getBlock(int index)
    {
        return memory.getBlock(translateAddress(index));
    }
    
    public String getWord(int index) 
    {
        return memory.getWord(translateAddress(index));
    }
    
    public void setBlock(int index, String data) 
    {
        memory.setBlock(getAddress(index), data);
    }
    
    public void setWord(int index, String data) 
    {
        memory.setWord(getAddress(index), data);
    }
    
    public void recycle() 
    {
        for (String frameIndex : memory.getBlock(PTR*10))
            if (frameIndex != null)
                memory.recycleFrame(Integer.valueOf(frameIndex.trim()));
        memory.recycleFrame(PTR);
    }
}