package org.operatingsystems.memory;

public class Frame {
    
    private boolean inUse;
    private String[] contents;
    
    public Frame() {
        inUse = false;
        contents = new String[10];
    }
    
    public boolean isInUse() {
        return inUse;
    }
    
    public String[] getBlock() {
        return contents;
    }
    
    public String getWord(int index) {
        return contents[index];
    }
    
    public void setBlock(String data) {
        // This way when we overwrite a block it gets wiped out instead of 
        // potentially having leftover data at the end
        contents = new String[10];
        
        while(data.length()%4 != 0)
            data += " ";
        
        for(int i = 0, j = 0; i < data.length() && j < contents.length; i+= 4, j++)
            this.setWord(j, data.substring(i, i+4));
    }
    
    public void setWord(int index, String data) {
        
        while(data.length()%4 != 0)
            data += " ";
        
        contents[index] = data;
    }
    
    public void reserve() {
        inUse = true;
    }
    
    public void recycle() {
        inUse = false;
        contents = new String[10];
    }
}
