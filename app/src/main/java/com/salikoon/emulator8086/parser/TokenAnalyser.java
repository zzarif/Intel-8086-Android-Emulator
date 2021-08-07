//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.parser;
public class TokenAnalyser implements OperandAnalyser{
	

/**
	*It defines some basic functions() for general analysis
	*@author Fida
	*
	*/
	public int getTokenLength(String token)
	{
		/**Counts the number of characters in the token
		*@author Fida
		*@param Token to be analysed
		*@return An integer value of the number of characters in the token
		*Mechanism: Use String class's length function
		*/
		
		return token.length();
	}

}//end of class