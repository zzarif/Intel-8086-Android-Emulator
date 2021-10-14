//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;
import com.salikoon.emulator8086.parser.Parser;
import java.util.Optional;

    /**This class stores syntax validation/checking information for various opcodes
    @author Watheeq
    */

public class ValidationLibrary
{
    public static int getNumberOfOperands(String opcode)
    {
        switch(opcode)
        {
            case "MOV":
            case "ADD":
            case "SUB":
            case "AND":
            case "OR":
            case "XOR":
            case "TEST":
             return 2;
            case "INC":
            case "DEC":
            case "NEG":
            case "NOT":
            case "MUL":
            case "DIV":
            return 1;
            default: 
            throw new IllegalArgumentException("Opcode is unknown");
        }
    }
    
    
    
}//end of class