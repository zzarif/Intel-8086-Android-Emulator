
package com.salikoon.emulator8086.hardware;

import java.util.HashMap;

/**
 *
 * @author Mohammed Mohaimen
 */
public class Logger
{
     private HashMap<String,Short> updateHistory = new HashMap<>();
            
    /**
     *
     * @param copy
     */
    public Logger(Logger copy)
    {
        updateHistory.putAll(copy.updateHistory);
    }

    Logger()
    {
       
    }
    
    public HashMap<String,Short> getNewValues()
    {
        return updateHistory;
    }
    
    public void clear()
    {
        updateHistory.clear();
    }
     
    public void insert(String inp, short value)
    {
        updateHistory.put(inp, (short)value);
    }
}
