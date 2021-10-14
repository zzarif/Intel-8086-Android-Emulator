// بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.user_code;
import java.util.ArrayList;

public class AssemblyCode 
{
    private static ArrayList<String> userCode;

    public static void setCode(String[] input)
    {

        userCode=new ArrayList<String>(java.util.Arrays.asList(input));
        for(int index=1;index<userCode.size();index++)
        {
            userCode.set(index,userCode.get(index).trim().toUpperCase());
        }
    }

    public static String getCode(int lineNumber)
    {
        if(lineNumber==0) throw new IndexOutOfBoundsException("0 is not a valid index");
        return userCode.get(lineNumber);
    }
    
    public static int getCodeLength()
    {
        return userCode.size();
    }
    
} //end of class

