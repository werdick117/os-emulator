/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems;

/**
 *
 * @author Steve
 */
public class Drum {
    
    protected String[] contents;
    
    public Drum()
    {
        contents = new String[100];
    }
    
    /**
     * 
     * @return The valid track number or -1 if none are available
     */
    public int hasTrack()
    {
        for(int i = 0; i < 100; i++)
            if(contents[i] == null)
                return i;
        
        return -1;
    }
    
    public void writeToDrum(String s, int i)
    {
        contents[i] = s;
    }
    
    public String readFromDrum(int i)
    {
        return contents[i];
    }
    
    public void removeFromDrum(int i)
    {
        contents[i] = null;
    }
}
