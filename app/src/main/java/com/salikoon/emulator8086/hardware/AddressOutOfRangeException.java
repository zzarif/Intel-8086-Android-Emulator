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
public class AddressOutOfRangeException extends IllegalArgumentException
{
    public AddressOutOfRangeException()
    {
        super("Address should be between 0h and 100,000h");
    }
    
    public AddressOutOfRangeException(String message)
    {
        super(message);
    }
}
