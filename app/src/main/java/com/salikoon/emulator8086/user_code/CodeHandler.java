//بِسْمِ اللَّهِ الرَّحْمٰنِ


package com.salikoon.emulator8086.user_code;

import com.salikoon.emulator8086.execution.Executor;
import com.salikoon.emulator8086.hardware.MemoryHandler;

public interface CodeHandler // intra-module facade for Code sub-module
{
    static void setCode(String[] userCode)
    {
        MemoryHandler.resetHardware();
        Executor.reset();
        AssemblyCode.setCode(userCode);    
    }
    static String getCode(int lineNumber)
    {
        return AssemblyCode.getCode(lineNumber);
    }
    static int getCodeLength()
    {
        return AssemblyCode.getCodeLength();
    }
    static int getLastLineNumberOfCode()
    {
        return getCodeLength()-1;
    }
}    
    