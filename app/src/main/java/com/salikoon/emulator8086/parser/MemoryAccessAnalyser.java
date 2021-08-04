package com.salikoon.emulator8086.parser;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

interface MemoryAccessAnalyser
{

    //@refactor
	public default boolean isMemoryAccess(String operand)
	{
	    return operand.matches("^\\[.*\\]$");
	}
	
	//@refactor
	public default boolean isWordSizedMemoryAccess(String operand)
	{
	    return false;
	}
	



}//end of file