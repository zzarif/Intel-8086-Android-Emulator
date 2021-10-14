//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;
import java.util.Optional;
import com.salikoon.emulator8086.parser.Parser;

/** This class contains the functions which are used to validate tokens
 @author Watheeq
 */


public class Validator
{
    public enum OperandEnums{SegmentRegister,NonSegmentRegister,MemoryAddress,ImmediateValue}

    /**
     This function checks a single tokenised line of code for invalid opcode or invalid operands
     The checking mechanism is independent of the opcode i.e. it is the same for all opcodes
     @param a String array which contains the opcode followed by its operands
     @return an Optional String which contains an error message is there are syntax errors present
     */
    public static String findMistakeInTokens(String [] tokens)
    {
        var opcode=tokens[0];
        try{
            var expectedNumberOfTokens=1+ValidationLibrary.getNumberOfOperands(opcode);

            if(tokens.length!=expectedNumberOfTokens)
                return String.format("Opcode %s requires exactly %d operands", opcode, expectedNumberOfTokens-1);
        /* if expectedNumberOfTokens=1,
         then this loop will not be executed,
           if expectedNumberOfTokens=2,
         then it will be executed only upon token[1]
           if expectedNumberOfTokens=3
         then this loop will be executed upon token[1] and turn[2]
        */
            for(int index=1;index<expectedNumberOfTokens;index++)
            {
                if(! Parser.Analyser.isValidOperand(tokens[index]) )
                    return "Operand "+tokens[index]+" is an unrecognised operand";
            }
            return "No Error";
        }

        catch(IllegalArgumentException exception)
        {
            return "Opcode "+opcode+" is either invalid or not yet implemented";
        }
    }//end of function


}//end of class