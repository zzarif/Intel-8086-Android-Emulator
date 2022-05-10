//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.instructions;

import com.salikoon.emulator8086.execution.ExecutionHandler;
import com.salikoon.emulator8086.user_code.CodeHandler;
import static com.salikoon.emulator8086.hardware.StringParameter.*;
import com.salikoon.emulator8086.hardware.MemoryHandler;



public interface ControlTransferInstructions{
    
        
    default void JMP(String label){
        int nextLineNumber=CodeHandler.resolveLabelToLineNumber(label);
        ExecutionHandler.jumpExecutorToLineNumber(nextLineNumber);
    }
    
    private void conditionalJump(String label, java.util.function.BooleanSupplier condition)
    {
        if(condition.getAsBoolean())
            JMP(label);    
    }
    
    default void JA(String label)
    {
        conditionalJump(label,() -> MemoryHandler.getValue(CarryFlag) ==0 && MemoryHandler.getValue(ZeroFlag) == 0);
    }
    
    default void JNBE(String label){
        JA(label);
    }
    
    default void JNC(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(CarryFlag) == 0);     
    }
    
    default void JAE(String label)
    {
        JNC(label);
    }
    
    default void JNB(String label)
    {
        JNC(label);
    }
    
    default void JC(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(CarryFlag) == 1);        
    }
    
    default void JB(String label)
    {
        JC(label);
    }
    
    default void JNAE(String label)
    {
        JC(label);
    }
    
    default void JBE(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(CarryFlag) ==1 || MemoryHandler.getValue(ZeroFlag) == 1);
    }
    
    default void JNA(String label)
    {
        JBE(label);
    }
    
    default void JCXZ(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(CX) == 0 );
    }
    
    default void JZ(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(ZeroFlag) == 1);
    }
    
    default void JE(String label)
    {
        JZ(label);       
    }
    
    default void JG(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(ZeroFlag) == 0 && MemoryHandler.getValue(SignFlag) == MemoryHandler.getValue(OverflowFlag) );
    }
    
    default void JNLE(String label)
    {
        JG(label);
    }
    
    default void JGE(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(SignFlag) == MemoryHandler.getValue(OverflowFlag));
    }
    
    default void JNL(String label)
    {
        JGE(label);
    }
    
    default void JL(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(SignFlag) == MemoryHandler.getValue(OverflowFlag));
    }
    
    default void JNGE(String label)
    {
        JL(label);
    }
    
    default void JLE(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(ZeroFlag) == 0 || MemoryHandler.getValue(SignFlag) != MemoryHandler.getValue(OverflowFlag));
    }
    
    default void JNZ(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(ZeroFlag) == 0);
    }
    
    default void JNE(String label)
    {
        JNZ(label);
    }
    
    default void JNO(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(OverflowFlag) == 0);
    }
    default void JNP(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(ParityFlag) == 0);
    }
    default void JPO(String label)
    {
        JNP(label);
    }
    
    default void JNS(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(SignFlag) == 0);
    }
    
    default void JO(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(OverflowFlag) == 1);
    }
    
    default void JP(String label)
    {
        conditionalJump(label,
        ()-> MemoryHandler.getValue(ParityFlag) == 1);
    }
    
    default void JPE(String label)
    {
        JP(label);
    }
    
    default void JS(String label)
    {
        conditionalJump(label,
        () -> MemoryHandler.getValue(SignFlag) == 1);
    }
    
    
    private void decrementCXThenJumpIfConditionFulfilled(String label, java.util.function.BooleanSupplier extraCondition)
    {
        /**
            This is a skeleton function which does the same operation as LOOP()
            but it also checks an extra condition along with CX !=0 before jumping
            @author Watheeq
            @param label A String label
            @param extraCondition a BooleanSupplier lambda which specifies the extra condition to fulfill 
        
        
        */        
                     
        var cxValue=MemoryHandler.getValue(CX);
        cxValue--; // do not use DEC(CX) as this will affect the flags too
        MemoryHandler.setValue(CX,cxValue);        
        
        if(cxValue != 0 && extraCondition.getAsBoolean())
            JMP(label);
     
    }
    
    default void LOOP(String label)
    {
        var cxValue=MemoryHandler.getValue(CX);
        cxValue--; // do not use DEC(CX) as this will affect the flags too
        MemoryHandler.setValue(CX,cxValue);        
        
        if(cxValue != 0)
            JMP(label);
     
    }
        
    default void LOOPZ(String label)
    {
        decrementCXThenJumpIfConditionFulfilled(label,
        () -> MemoryHandler.getValue(ZeroFlag) == 1);
    }
    
    default void LOOPE(String label)
    {
        LOOPZ(label);
    }
    
    default void LOOPNZ(String label)
    {
        decrementCXThenJumpIfConditionFulfilled(label,
        () -> MemoryHandler.getValue(ZeroFlag) == 0);
    }
    
    default void LOOPNE(String label)
    {
        LOOPNZ(label);
    }
    
    
    
    
}//end of interface