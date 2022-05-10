//بِسْمِ اللَّهِ الرَّحْمٰنِ


package com.salikoon.emulator8086.user_code;

import com.salikoon.emulator8086.execution.Executor;
import com.salikoon.emulator8086.hardware.MemoryHandler;

public interface CodeHandler // intra-module facade for Code sub-module
{
    static void setCode(String[] userCode)
    {
        MemoryHandler.resetHardware();
        Executor.reset();
        storeCode(userCode);
    }
    static String getCode(int lineNumber)
    {
        return AssemblyCode.getCode(lineNumber);
    }
    static int getCodeLength()
    {
        return AssemblyCode.getCodeLength();
    }
    static int getLastLineNumberOfCode()
    {
        return getCodeLength()-1;
    }
    static int resolveLabelToLineNumber(String label)
    {
        return SymbolCharts.getLineNumberOfLabel(label);
    }
    static boolean labelExists(String label)
    {
        return SymbolCharts.isValidLabel(label);
    }
    
    
    
    private static void storeCode(String[] rawCode)
    {
           var cleanedCode=CodeCleaner.clean(rawCode);
           var pureCode=separateLabels(cleanedCode);      
           AssemblyCode.setCode(pureCode);    
    }
    private static String[] separateLabels(String[] input)
    {
        SymbolCharts.labelChart=SymbolExtractor.prepareLabelChart(input);
        return java.util.stream.Stream.of(input)
        .map(CodeCleaner:: removeLabels)
        .toArray(String[]::new);
    }
}    
    