//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.instructions;
import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.hardware.StringParameter;
public interface FlagInstructions
{
    public default void STC(){
       MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1); 
    }

    public default void STD(){
       MemoryHandler.setValue(StringParameter.DirectionFlag, (short) 1); 
    }

    public default void CMC(){
       var bit=MemoryHandler.getValue(StringParameter.CarryFlag);
       bit=(short)(1-bit); //invert the bit
       MemoryHandler.setValue(StringParameter.CarryFlag,bit); 
    }
    
    public default void CLC(){
       MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0); 
    }
    
    public default void CLD(){
       MemoryHandler.setValue(StringParameter.DirectionFlag, (short) 0); 
    }
    
    public default void CLI(){
       MemoryHandler.setValue(StringParameter.InterruptFlag, (short) 0); 
    }
    
}//end of interface