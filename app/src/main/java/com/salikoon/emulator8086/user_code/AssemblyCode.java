package com.salikoon.emulator8086.user_code;// بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

public class AssemblyCode 
{
    private static String[] userCode;
    
    public static void setCode(String[] input)
    {
          userCode=input;
          trimAllLinesOfUserCode();
         convertToUpperCaseAllLinesOfUserCode();
       
    }
    public static String getCode(int lineNumber)
    {
        return userCode[lineNumber];
    }
    private static void trimAllLinesOfUserCode()
    {
    	for(int index=0; index<userCode.length; index++)
    	userCode[index]=userCode[index].trim();
    	
    }
    private static void convertToUpperCaseAllLinesOfUserCode()
    {
    		for(int index=0; index<userCode.length; index++)
    	userCode[index]=userCode[index].toUpperCase();
 
    	
    }
 } //end of class

