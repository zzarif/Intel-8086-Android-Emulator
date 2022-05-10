//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.execution;
import static com.salikoon.emulator8086.parser.Parser.Tokeniser;

import com.salikoon.emulator8086.hardware.Logger;
import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.ui_helper.UIPacket;
import com.salikoon.emulator8086.ui_helper.UIHandler;
import com.salikoon.emulator8086.user_code.CodeHandler;
import java.util.OptionalInt;


public class Executor
{
    private static int currentLineUnderExecution=1;
    private static OptionalInt nextLineToExecute=OptionalInt.empty(); // usually the next line is currentLineUnderExecution++ but in case of jump this field contains the the next line to jump to
    
    public static void setNextLineToExecute(int lineNumber)
    {
        nextLineToExecute=OptionalInt.of(lineNumber);
    }
    public static UIPacket execute()
    {   
        var lineOfCode= CodeHandler.getCode(currentLineUnderExecution);
        Logger log= executeLineAndGetLogger(lineOfCode);
        var returnPacket= new UIPacket(log,currentLineUnderExecution);
        updateCurrentLineUnderExecution();
        return returnPacket;
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
            throw new RuntimeException(problem);
        }
        
        return MemoryHandler.getLogger();
    }

    public static void reset()
    {
        currentLineUnderExecution=1;
    }
    
    private static void updateCurrentLineUnderExecution()
    {
        if(nextLineToExecute.isPresent())
            {
            currentLineUnderExecution=nextLineToExecute.getAsInt();
            nextLineToExecute=OptionalInt.empty();
            }
        else if(currentLineUnderExecution==CodeHandler.getLastLineNumberOfCode())
            UIHandler.finishedExecution=true;
        else
            currentLineUnderExecution++;
    }



}//end of file