package com.salikoon.emulator8086.handlers;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.utility.AssemblyCode;

interface CodeHandler
{
    static void setCode(String[] userCode)
    {
        AssemblyCode.setCode(userCode);
    }
    static String getCode(int lineNumber)
    {
        return AssemblyCode.getCode(lineNumber);
    }
}    
    