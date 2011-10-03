/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.operatingsystems.exceptions;

/**
 *
 * @author Steve
 */
public class PrintDataException extends BaseException{
    
    private int operand;

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }
    
    public PrintDataException(int i)
    {
        super("SI2");
        operand = i;
    }
}
