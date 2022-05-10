// بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.user_code;

import java.util.ArrayList;

public class AssemblyCode 
{
    private static String[] userCode;
    
    public static void setCode(String[] input)
    {           
           userCode= input;     
    }
    public static String getCode(int lineNumber)
    {
        if(lineNumber==0) throw new IndexOutOfBoundsException("0 is not a valid index");
        return userCode[lineNumber];
    }
    
    public static int getCodeLength()
    {
        return userCode.length;
    }
    
} //end of class

