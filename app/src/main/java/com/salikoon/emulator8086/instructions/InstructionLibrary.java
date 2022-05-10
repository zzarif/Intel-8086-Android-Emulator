//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيم
package com.salikoon.emulator8086.instructions;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.parser.Parser;
import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.ui_helper.UIHandler;


public class InstructionLibrary implements DataTransferInstructions, ArithmeticAndBitManipulationInstructions, FlagInstructions , ControlTransferInstructions
{    
    public void HLT()
    {
       com.salikoon.emulator8086.ui_helper.UIHandler.finishedExecution=true;
    }
    
    public void NOP()
    {
        // do nothing
    }
    
    public void INT(String interruptNumber)
    {
        
        switch (Parser.Analyser.immediateValueToInt(interruptNumber))
        {
           
           case 0x21:
           {
               var valueInAH= (MemoryHandler.getValue(StringParameter.AH));
               switch (valueInAH)
                {
                  case 1:
                   {short value=UIHandler.getChar();
                   MemoryHandler.setValue(StringParameter.AL,value);
                   break;}
                  case 2:
                   {short value=MemoryHandler.getValue(StringParameter.DL);
                   UIHandler.putChar(value);
                   MemoryHandler.setValue(StringParameter.AL,value);
                   break;}
                  default:
                       throw new IllegalArgumentException(
                       "AH= "+valueInAH+" , this function number is not supported");          
                } 
               break;         
           }          
           default:
              throw new IllegalArgumentException(java.text.MessageFormat.format("INT {0} is not supported",interruptNumber));
        }
    
    
          MemoryHandler.setValue(StringParameter.InterruptFlag,(short)0);
          MemoryHandler.setValue(StringParameter.TrapFlag,(short)0);
    
    }
    
}//end of class