package org.operatingsystems.exceptions;

public class PageFault extends BaseException {
    
    private boolean isValid;
    
    public PageFault()
    {
        super("PI3");
        
        isValid = false;
    }
    
    public PageFault(boolean bool)
    {
        super("PI3");
        isValid = bool;
    }
    
    public boolean isValid()
    {
        return isValid;
    }
    
    public void setIsValid(Boolean bool)
    {
        this.isValid = bool;
    }
}