package com.salikoon.emulator8086.user_code;
import java.util.ArrayList;

public class CodeCleaner {
    
     public static String[] clean(String[] uncleanCode)
     {
         return
             java.util.Arrays
          .stream(uncleanCode)
          .map(String::trim)
          .map(CodeCleaner::removeComments)
          .map(String::toUpperCase)
          .toArray(String[]::new);
     
     }
     
     /**This function removes comments from a line of user code.
		*		Whatever is written after semi-colon is a comment in ASL-8086, so this functions splits the input string across the semi-colon and then returns the first fragment.
		*If there was no semi-colon then the function returns the original string
		*@author Fida
		*@param input A String containing a line of user code
		*@return It returns code that is stripped of comments in sha Allah
 *It is necessary to understand {@link java.util.String#split(String s) split} method to understand the code
 */
     private static String removeComments(String input)
    {
        String[] fragments = input.split(";", 2);
        return fragments[0];
    }
    /**This function removes labels from a line of user code.
		*		Whatever is written before colon is a label in ASL-8086, so this function splits the input string across the colon and then returns the last fragment.
		*If there was no colon then the function returns the original string
		*@author Fida
		*@param input A String containing a line of user code
		*@return It returns code that is stripped of labels in sha Allah
 *It is necessary to understand {@link java.util.String#split(String s) split} method to understand the code
 */
    public static String removeLabels(String input)
    {
        String[] fragments = input.split(":", 2);
        return fragments[fragments.length-1]; // returns last element of array
    }
    
} //end of file