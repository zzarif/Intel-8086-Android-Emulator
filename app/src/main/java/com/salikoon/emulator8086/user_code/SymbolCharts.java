//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.user_code;


/*This class contains data structures holding information on data extracted from user code */

public class SymbolCharts {
    
     static java.util.Map<String, Integer> labelChart;
    
    public static int getLineNumberOfLabel(String label)
    {
        return labelChart.get(label);
    }
    
    public static boolean isValidLabel(String label)
    {
        return labelChart.containsKey(label);
    }
}