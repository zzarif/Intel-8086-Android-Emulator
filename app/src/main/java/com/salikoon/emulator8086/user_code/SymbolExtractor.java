//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.user_code;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Map;

public class SymbolExtractor {
    /*This class holds functions to extract and process symbols such as labels, macros, functions etc*/
    
    /** This function checks a single line of ASL-8086 code for syntax errors
        @author Watheeq
        @param code The user written ASL-8086 code
        @return A Map<String,Integer> is returned where the keys are the labels and the values are the corresponding line-numbers referenced by these labels
       
    */
            
    public static Map<String,Integer> prepareLabelChart(String[] code)
    {
        /*
            Mechanism: A stream containing all valid indices/line-numbers are created,
            subsequently only those line-numbers contains labels are kept
            then the labels are extracted and paired with the line-numbers
        */
        
        return IntStream.range(1,code.length)
        .parallel()
        .filter( lineNumber -> containsLabel(code[lineNumber]))
        .boxed()
        .collect(Collectors.toMap( lineNumber -> extractLabel(code[lineNumber]) , lineNumber -> lineNumber ));
    }
    
    private static String extractLabel(String input)
    {
         String[] fragments = input.split(":", 2);
         return fragments[0];
    }
    
    private static boolean containsLabel(String input)
    {
        /* A label bearing line must have a colon symbol in it
            e.g. label: mov ax,bx
        */
        return input.contains(":");
    }
}