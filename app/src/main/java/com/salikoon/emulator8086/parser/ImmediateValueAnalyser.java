//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.parser;
interface ImmediateValueAnalyser
{
    public default boolean isImmediateValue(String operand)
	{
		/** This functions informs if the operand is a direct value e.g. 24H
		*@author Fida
		*@param A String token to be analysed
		*@return A Boolean value having value true if it is as such
		*Mechanism: Regex Matching
		*The 4 first characters can be any digit or A-F, but there has to be at least two characters like this
		*There can be an optional suffix of B or D or H
		*I have used anchors for perfection
		*Input: Hexa, Binary, Decimal
		*Sample Inputs: 23, FFH, 10040, 0101010B
		*/
		if( operand.matches("^(AH|BH|CH|DH)$") ) // These are registers
		return false; 
		else
		return operand.matches("^[0-9A-F]+[BDH]?$");
		
	}
	
    public default boolean isWordSizedImmediateValue(String immediateValue)
    {
        if(! isImmediateValue(immediateValue)) throw new RuntimeException("Expected an immediateValue");
        return immediateValueToInt(immediateValue)>0xFF;
    }
    
    public default int getRadixOfImmediateValue(String immediateValue)
    {
        if(immediateValue.matches("^[0-9A-F]+H$")) return 16;
        else if (immediateValue.matches ("^[\\d]+D?$")) return 10;
        else if (immediateValue.matches ("^[01]+B$")) return 2;
        else throw new RuntimeException("Radix of immediateValue could not be resolved");
    }
    public default int immediateValueToInt(String immediateValue)
    {
        var suffixRemoved=immediateValue.replaceFirst("[HDB]?$","");
        var radix=getRadixOfImmediateValue(immediateValue);
        return Integer.parseInt(suffixRemoved,radix);
    }   
} //end of file