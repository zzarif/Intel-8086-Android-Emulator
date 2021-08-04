package com.salikoon.emulator8086.execution;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.instructions.InstructionLibrary;

public class OpcodeCaller
{
    public static void invoke(String[] tokens) throws Exception
    {
        if(tokens.length==0) throw new RuntimeException("No tokens found; So could not call instruction from library.");
        
        var numberOfOperands=tokens.length-1; // first token is opcode
        var opcode=tokens[0];
        switch(numberOfOperands)
        {
            
            case 0:
            InstructionLibrary.class.getMethod(opcode).invoke(null);
            break;
            case 1:
            InstructionLibrary.class.getMethod(opcode,String.class).invoke(null, tokens[1]);
            break;
            case 2:
            InstructionLibrary.class.getMethod(opcode,String.class,String.class).invoke(null, tokens[1], tokens[2]);
            break;
            default:
            throw new RuntimeException("Too many tokens received; hence could not call instruction from library");
            
        }//end of switch
        
    }
    




}//end of file