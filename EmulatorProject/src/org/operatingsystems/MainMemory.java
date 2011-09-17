/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

/**
 *
 * @author Steve
 */
public class MainMemory {
    private String[][] memory;
    
    public MainMemory()
    {
        memory = new String[10][10];
    }
    
    public String[] getBlock(int index)
    {
        return memory[index/10];
    }
    
    public String getWord(int index)
    {
        return memory[index/10][index%10];
    }
    
    public void setBlock(int index, String data)
    {
        String[] block = new String[10];

        while(data.length()%4 != 0)
            data += " ";
        
        for(int i = 0, j = 0; i < data.length() && j < block.length; i+= 4, j++)
            block[j] = data.substring(i, i+4);
        
        memory[index/10] = block;
    }
    
    public void setWord(int index, String data)
    {
        while(data.length()%4 != 0)
            data += " ";
        
        memory[index/10][index%10] = data;
    }
}
