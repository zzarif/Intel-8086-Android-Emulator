//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;
import java.util.Optional;
import com.salikoon.emulator8086.parser.Parser;
import com.salikoon.emulator8086.user_code.CodeHandler;

    /** This class contains the functions which are used to validate tokens
    @author Watheeq
    */


public class Validator
{
   
    
    /**
    This function checks a single tokenised line of code for invalid opcode or invalid operands
    The checking mechanism is independent of the opcode i.e. it is the same for all opcodes
    @param a String array which contains the opcode followed by its operands
    @return an Optional String which contains an error message is there are syntax errors present 
    */
    public static Optional<String> findMistakeInTokens(String [] tokens)
    {
       var opcode=tokens[0];
       try{
           var expectedNumberOfTokens=1+ValidationLibrary.getNumberOfOperands(opcode);
        
       if(tokens.length!=expectedNumberOfTokens)
            return Optional.of(String.format("Opcode %s requires exactly %d operands", opcode, expectedNumberOfTokens-1));
        /* if expectedNumberOfTokens=1,
         then this loop will not be executed,
           if expectedNumberOfTokens=2, 
         then it will be executed only upon token[1]
           if expectedNumberOfTokens=3
         then this loop will be executed upon token[1] and turn[2]
        */
        for(int index=1;index<expectedNumberOfTokens;index++) 
            if(! Parser.Analyser.isValidOperand(tokens[index]) && !CodeHandler.labelExists(tokens[index]))
                return Optional.of("Operand "+tokens[index]+" is an unrecognised operand");
       
            
        }
        
        catch(IllegalArgumentException exception)
        {
            return Optional.of("Opcode "+opcode+" is either invalid or not yet implemented");
        }
        
            return Optional.<String>empty();
    }//end of function
    
    
   
    
        
    
    
}//end of class