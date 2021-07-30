/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salikoon.emulator8086.handlers;

import com.salikoon.emulator8086.models.Logger;

/**
 *
 * @author Mohammed Mohaimen
 */
public class LoggerManager
{
    private static Logger current_log = new Logger();
    
    LoggerManager()
    {
        
    }
    
    public static void resetLog()
    {
        current_log.clear();
    }
    
    public static void insert_into_Log(String inp, short value)
    {
        current_log.insert(inp,value);
    }
    
    public static Logger getLog()
    {
        Logger newLog = new Logger(current_log);
        return newLog;
        //return current_log;
    }
}
