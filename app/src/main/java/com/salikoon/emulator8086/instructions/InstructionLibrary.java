package com.salikoon.emulator8086.instructions;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.parser.OperandAnalyser;
import com.salikoon.emulator8086.parser.Parser;

public class InstructionLibrary implements OperandAnalyser
{
    
    public static void MOV(String destination, String source)
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
    public static void ADD(String destination, String source)
    {
       var isWordOperation=Parser.Analyser.is16BitOperation(destination, source);
       var augend=MemoryHandler.getValue(destination,isWordOperation);
       var addend=MemoryHandler.getValue(source,isWordOperation);
       var sum=augend+addend;
       MemoryHandler.setValue(destination,(short)sum);
    }
    public static void SUB(String destination, String source)
    {
       var isWordOperation=Parser.Analyser.is16BitOperation(destination, source);
       var minuend=MemoryHandler.getValue(destination,isWordOperation);
       var subtrahend=MemoryHandler.getValue(source,isWordOperation);
       var difference=minuend-subtrahend;
       MemoryHandler. setValue (destination,(short)difference);
    }

    public static void MUL(String multiplierMemoryElement)
    {
       var isWordOperation=Parser.Analyser.is16BitOperation(multiplierMemoryElement);
       var multiplier=MemoryHandler.getValue(multiplierMemoryElement,isWordOperation);
       short multiplicand;
         if(! isWordOperation)  
       {
         multiplicand=MemoryHandler.getValue(StringParameter.AL);
         var product=multiplicand*multiplier;
         MemoryHandler.setValue(StringParameter.AX,(short) product);
       }
         else
      { 
         multiplicand=MemoryHandler.getValue(StringParameter.AX);
         int product=multiplicand*multiplier;
         MemoryHandler.setValue(StringParameter.AX, (short) product);
         MemoryHandler.setValue(StringParameter.DX, (short) (product>>16));
      }
     
    }
     public static void DIV(String divisorMemoryElement)
    {
       var isWordOperation=Parser.Analyser.is16BitOperation(divisorMemoryElement);
       var divisor=MemoryHandler.getValue(divisorMemoryElement,isWordOperation);
       int dividend;
         if(!isWordOperation)  
       {
         dividend=MemoryHandler.getValue(StringParameter.AX);
         var quotient=dividend/divisor;
         var remainder=dividend%divisor;
         MemoryHandler.setValue(StringParameter.AL,(short)quotient);
         MemoryHandler.setValue(StringParameter.AH,(short)remainder);
       }
         else
      { 
         dividend=MemoryHandler.getValue(StringParameter.DX)*0xFFFF+MemoryHandler.getValue(StringParameter.AX);
         var quotient=dividend/divisor;
         var remainder=dividend%divisor;
         MemoryHandler.setValue(StringParameter.AX,(short)quotient);
         MemoryHandler.setValue(StringParameter.DX,(short)remainder);
        
      }
    }
    
    
    
    
    
}//end of class