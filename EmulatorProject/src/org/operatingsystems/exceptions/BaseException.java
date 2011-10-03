/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.exceptions;

/**
 *
 * @author Steve
 */
public class BaseException extends RuntimeException{
    
    private String code;
    
    public String getCode()
    {
        return code;
    }
    
    public BaseException(String i)
    {
        code = i;
    }
    
}
