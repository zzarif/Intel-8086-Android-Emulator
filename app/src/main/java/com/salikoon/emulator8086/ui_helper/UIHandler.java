//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.ui_helper;

import com.salikoon.emulator8086.execution.ExecutionHandler;
import com.salikoon.emulator8086.user_code.CodeHandler;
import com.salikoon.emulator8086.syntax_check.ValidationHandler;
import java.util.List;
import com.salikoon.emulator8086.syntax_check.SyntaxSlip;
import java.lang.annotation.*;


public class UIHandler
{
    public static boolean finishedExecution=false;
    
    public static List<SyntaxSlip> setCode(String[] code)
    {
        CodeHandler.setCode(code);     
        return ValidationHandler.checkCode();
    }

    public static UIPacket execute()
    {
        return ExecutionHandler.execute();
    }

    public static boolean executionIncomplete()
    {
        return !finishedExecution;
    }
    
    public static List<String> getImplementedInstructions()
    {
        return ValidationHandler.getAllImplementedInstructions();
    }
    
    
    @IncompleteWork(assignedTo="Zarif", 
    task= "kindly replace this driver function with the actual one")   
    public static short getChar()
    {
        /**
        This function fetches a character from the user keyboard
        @author Zarif
        @return the ascii value of the character, if the user presses a non-character then zero is returned
        */
                
        return 0x30;
    }
    
    @IncompleteWork(assignedTo="Zarif",
    task="kindly replace this stub with the actual function")
    public static void putChar(short asciiValue)
    {
        /**
        This function displays a character to the user monitor/screen
        @author Zarif
        @param asciiValue The ASCII value of the character to be displayed, the character can be alphanumeric or new line or carriage return etc
        */
          System.out.println((char) asciiValue);
    }
}//end of class



@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD,ElementType.TYPE})
@interface
IncompleteWork{
    public String assignedTo();
    public int priority() default 0;
    public String task();
}