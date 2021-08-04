package com.salikoon.emulator8086.user_code;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

public interface CodeHandler
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
    