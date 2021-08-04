package com.salikoon.emulator8086.parser;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

public interface OperandAnalyser extends RegisterAnalyser,ImmediateValueAnalyser, MemoryAccessAnalyser
 {
		/**This class reveals operand information regarding a particular token 
	*@author Fida
	*
	*
	*
	*/
	public default boolean isImmediateValueMode(String operand)
	{
		return isImmediateValue(operand);
	}
	
	public default boolean isRegisterImmediateAddressingMode(String operand)
	{
		/** This functions informs if the operand is a register e.g. AX,SS, SI
		*@author Fida
		*@param A String token to be analysed
		*@return A Boolean value having value true if it is as such
		*Mechanism: Using isRegister function of RegisterAnalyser
		*
		*/
        return isRegister(operand);
		
	}
	//@refactor
	public default boolean isMemoryAccessMode(String operand)
	{
	    return isMemoryAccess(operand);
	}
	public default boolean is16BitOperation(String operand)
	{
		if(isRegister(operand) || isImmediateValueMode(operand) || isMemoryAccessMode(operand) )
        return isWordSizedOperand(operand);
        else throw new RuntimeException("Invalid operand, so could not resolve operation size");
	}
	public default boolean is16BitOperation(String destination,String source)
	{
	    /**
	     * 
	     *Mechanism: if one of the operands is a register then the register determines the size of operation
	     *          otherwise if the source operand is a immediate value then its size determines the operation size.
	     * 
	    */
		if      (isRegister(destination)) return isWordSizedOperand(destination);
		else if (isRegister(source))      return isWordSizedOperand(source);
		else if (isImmediateValueMode(source)) return isWordSizedOperand(source);
		else throw new RuntimeException("Invalid operands , could not resolve operation size");
	}
	
	public default boolean isWordSizedOperand(String operand)
	{
	    if     (isRegister(operand))        return isWordSizedRegister(operand);
	    else if(isImmediateValue(operand))  return isWordSizedImmediateValue(operand);
	    else if(isMemoryAccess(operand))    return isWordSizedMemoryAccess(operand);
	    else throw new RuntimeException("Invalid operand; Could not resolve operation size");
	}
	

}//end of interface