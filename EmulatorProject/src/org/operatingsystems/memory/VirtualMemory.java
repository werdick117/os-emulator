package org.operatingsystems.memory;

import java.util.ArrayList;
import org.operatingsystems.exceptions.PageFault;

public class VirtualMemory {
    
    private Integer PTR;
    private MainMemory memory;
    private ArrayList<Integer> pageTable;
    
    public VirtualMemory(MainMemory realMemory) 
    {
        this.pageTable = new ArrayList<Integer>();
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
        int q = translateAddress(index);
      //  return memory.getWord(translateAddress(index));
        return memory.getWord(q);
    }

    
    public void setBlock(int index, String data) 
    {
        int q = getAddress(index);
        memory.setBlock(q, data);
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
    
    public void setDataBlock(int operand, String data)
    {
        int i = memory.randomFrame();
        int j = operand / 10;
        
        memory.setDataWord(PTR, j, i);
        
        memory.setDataBlock(i, data);
    }
}