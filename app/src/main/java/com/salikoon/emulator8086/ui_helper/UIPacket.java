//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.ui_helper;

import com.salikoon.emulator8086.hardware.Logger;

public class UIPacket
{
    public int lineJustExecuted;
    public Logger updatedMemoryElements;
    
    public UIPacket(Logger log , int lineNumber)
    {
        updatedMemoryElements=log;
        lineJustExecuted=lineNumber;
    }
    @Override
    public String toString()
    {
        return String.format("Line-Number: %d Changes are %s",lineJustExecuted,updatedMemoryElements.getNewValues());
    }
}//end of file