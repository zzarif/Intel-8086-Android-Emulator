/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salikoon.emulator8086.hardware;


/**
 *
 * @author user
 */
public class InvalidRegisterNameException extends IllegalArgumentException
{
    public InvalidRegisterNameException()
    {
        super("The given name is not a valid Register name");
    }
    
    public InvalidRegisterNameException(String message)
    {
        super(message);
    }
}
