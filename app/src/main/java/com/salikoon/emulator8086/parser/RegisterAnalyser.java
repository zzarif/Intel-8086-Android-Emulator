//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.parser;
interface RegisterAnalyser {
	/**This class reveals register information regarding a particular token 
	*@author Fida
	*
	*
	*
	*/
	public default boolean isGeneralPurposeRegister(String token)
	{
		/**Informs if a particular token is a general purpose register or not.
		*@author Fida
		*@param Token to be analysed
		*@return A boolean variable which is true of the token is as such, and false otherwise
		*Mechanism: Use regex matching
		*General purpose registers are AX(AL:AH),BX(BL:BH),CX(CL:CH),DX(DL:DH)
		*So,the first character is either A or B or C or D and the last character of either L or H or X.
		*I have also used regex anchors ^ and $ for perfection.
		*/
		return token.matches("^[A-D][LHX]$");
		
	}

	public default boolean isSegmentRegister(String token)
	{
		/**Informs if a particular token is a segment register or not.
		*@author Fida
		*@param Token to be analysed
		*@return A boolean variable which is true of the token is as such, and false otherwise
		*Mechanism: Use regex matching
		*Segment registers are CS,ES,DS,SS
		*So,the first character is either C or D or E or S.And the last character is always S.
		*I have also used regex anchors ^ and $ for perfection.
		*/
		return token.matches("^[CDES]S$");
		
	}

	public default boolean isPointerRegister(String token)
	{
		/**Informs if a particular token is a pointer register or not.
		*@author Fida
		*@param Token to be analysed
		*@return A boolean variable which is true of the token is as such, and false otherwise
		*Mechanism: Use regex matching
		*Pointer registers are IP, BP,SP
		*So,the first character is either B or I or S.And the last character is always P.
		*I have also used regex anchors ^ and $ for perfection.
		*/
		return token.matches("^[BIS]P$");
		
	}
	
	
	public default boolean isIndexRegister(String token)
	{
		/**Informs if a particular token is an index register or not.
		*@author Fida
		*@param Token to be analysed
		*@return A boolean variable which is true of the token is as such, and false otherwise
		*Mechanism: Use regex matching
		*Index registers are SI and DI
		*So, the first character is either D or S.And the last character is always I.
		*I have also used regex anchors ^ and $ for perfection.
		*/
		return token.matches("^[DS]I$");
		
	}
	
	public default boolean isRegister(String token)
	{
		/**Informs if a particular token is a register or not.
		*@author Fida
		*@param Token to be analysed
		*@return A boolean variable which is true of the token is as such, and false otherwise
		*Mechanism: Use regex matching
		*Registers are General Purpose or Segment or Pointer or Index or Flag
		*But the Flag Register is not accessible to the ASL-8086 coder
		*/
		return isGeneralPurposeRegister(token) || isSegmentRegister(token) || isPointerRegister(token) || isIndexRegister(token);
		
	}
    public default boolean isWordSizedRegister(String token)
	{
	    /**
	     * Mechanism: Only H or L registers are 8 bit
	     * 
	    */
	    if(token.matches("^[A-D][LH]$")) return false;
	    else if(isRegister(token)) return true;
	    else throw new RuntimeException("Input parameter is not a register; Hence could not resolve size");
	}
	
}//end of interface