//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.instructions;

import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.parser.Parser;

public interface DataTransferInstructions
{
   public default void MOV(String destination, String source)
    {
        /**Executes the MOV operation on the hardware.
        *@author Fida
        *@param destination The destination operand as a String
        *@param source The source operand as a String
        *Mechanism: MOV copies a byte or a word from source to destination operands.
        *Source can be a register, memory or immediate value
        *Destination can be a register or memory.
        *
        *But it is not allowed for both source and destination to be memory simultaneously.
        *Both source and destination must be of equal with bitwidth
        *It not allowed to load immediate value into segment registers
        *It is not allowed for both source and destination to be Segment registers
        *It is not allowed for IP or CS to be destination.
        *
        *
        */


        var isWordOperation= Parser.Analyser.is16BitOperation(destination, source);
        var value= MemoryHandler.getValue(source, isWordOperation);
        MemoryHandler.setValue(destination, value,isWordOperation);
    }



}//end of file