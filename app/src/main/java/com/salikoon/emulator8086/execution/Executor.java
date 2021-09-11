//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.execution;
import static com.salikoon.emulator8086.parser.Parser.Tokeniser;

import com.salikoon.emulator8086.hardware.Logger;
import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.ui_helper.UIPacket;
import com.salikoon.emulator8086.user_code.CodeHandler;

public class Executor
{
    private static int currentLineUnderExecution=0;
    
    public static void setNextLineToExecute(int lineNumber)
    {
        currentLineUnderExecution=lineNumber-1;
        //it is lineNumber-1 because in execute() the currentLineUnderExecution is incremented first then that new line is executed
    }
    public static UIPacket execute()
    {
        currentLineUnderExecution++;
        var lineOfCode= CodeHandler.getCode(currentLineUnderExecution);
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

    public static void reset()
    {
        currentLineUnderExecution=0;
    }
    



}//end of file