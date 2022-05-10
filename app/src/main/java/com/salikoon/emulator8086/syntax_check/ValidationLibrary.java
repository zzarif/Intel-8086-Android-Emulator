//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.reflect.Method;

    /**This class stores syntax validation/checking information for various opcodes
    @author Watheeq
    */

public class ValidationLibrary
{
    private static java.util.Map<String,Integer> numberOfOperands;
    
    /*Static Initialisation block : Initialises static variables by Allah's will */
   static {
     /* Fetches all methods of InstructionLibrary
        Keeps only the Opcode methods i.e. methods which have names in ALLCAPs e.g. MOV
        & others like equals(), hash() are filtered out
        Prepares a HashMap based on Opcode name & number of operands
    */  
        numberOfOperands =
        Stream.of(com.salikoon.emulator8086.instructions.InstructionLibrary.class.getMethods() )
		       .filter(item -> item.getName().matches("[A-Z]*"))
        .collect(Collectors.toMap(method -> method.getName(), method -> method.getParameterCount()));
    }
    
    
    public static int getNumberOfOperands(String opcode)
    {   
        Integer returnValue = numberOfOperands.get(opcode);
        
        try{
            return returnValue.intValue();
            }
        catch(NullPointerException problem){            
            // catch block executed when returnValue == null
              throw new IllegalArgumentException("Opcode is unknown");       
            }
        
    }
    
    public static java.util.List<String> getListOfImplementedInstructions()
    {
        return new java.util.ArrayList<String>(numberOfOperands.keySet());
    }
    
}//end of class