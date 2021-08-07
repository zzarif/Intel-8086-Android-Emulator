//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.instructions;

import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.parser.Parser;

public interface ArithmeticInstructions
{
    public default void ADD(String destination, String source)
    {
       var isWordOperation= Parser.Analyser.is16BitOperation(destination, source);
       var augend= MemoryHandler.getValue(destination,isWordOperation);
       var addend=MemoryHandler.getValue(source,isWordOperation);
       var sum=augend+addend;
       MemoryHandler.setValue(destination,(short)sum);
    }
    public default void SUB(String destination, String source)
    {
       var isWordOperation=Parser.Analyser.is16BitOperation(destination, source);
       var minuend=MemoryHandler.getValue(destination,isWordOperation);
       var subtrahend=MemoryHandler.getValue(source,isWordOperation);
       var difference=minuend-subtrahend;
       MemoryHandler.setValue (destination,(short)difference);
    }

    public default void MUL(String multiplierMemoryElement)
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
     public default void DIV(String divisorMemoryElement)
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
    
    
    
}//end of file    