package com.salikoon.emulator8086.models;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

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