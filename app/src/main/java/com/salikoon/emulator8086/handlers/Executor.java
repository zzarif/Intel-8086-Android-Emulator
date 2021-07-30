package com.salikoon.emulator8086.handlers;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.models.Logger;
import com.salikoon.emulator8086.models.UIPacket;

public class Executor
{
    private static int currentLineUnderExecution=0;

    public static UIPacket execute()
    {
        currentLineUnderExecution++;
        String lineOfCode=CodeHandler.getCode(currentLineUnderExecution);
        Logger log= executeLineAndGetLogger(lineOfCode);
        return new UIPacket(log,currentLineUnderExecution);
    }
    private static Logger executeLineAndGetLogger(String code)
    {
        String[] tokens =Tokeniser.tokeniseCode(code);
        MemoryHandler.resetLogger();
        try
        {
            OpcodeCaller.invoke(tokens);
        }
        catch(Exception problem)
        {
            System.out.println(problem);
        }
        
        return MemoryHandler.getLogger();
    }





}//end of file