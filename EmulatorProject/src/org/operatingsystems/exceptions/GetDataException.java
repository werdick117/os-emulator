/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.exceptions;

/**
 *
 * @author Steve
 */
public class GetDataException extends BaseException {
    int operand;
    
    public GetDataException(int i)
    {
        super("SI1");
        operand = i;
    }
    
    public int getOperand()
    {
        return operand;
    }
}
