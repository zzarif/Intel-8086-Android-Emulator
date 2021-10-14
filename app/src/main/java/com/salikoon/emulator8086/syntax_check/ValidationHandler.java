//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;
import com.salikoon.emulator8086.parser.Tokeniser;
import com.salikoon.emulator8086.user_code.CodeHandler;

/** This class is the facade for the Syntax Check package
 @author Watheeq
 */


public class ValidationHandler // it should mainly use functions implemented in other classe
{
    /** This function checks the ASL-8086 code for syntax errors.
     @author Watheeq
     @param code A single user written ASL-8086 line of code
     @return a List of SyntaxSlip records

     */

    public static List<SyntaxSlip> checkCode()
    {

        var errors=new java.util.ArrayList<SyntaxSlip>();
        for(int index=1;index<=CodeHandler.getLastLineNumberOfCode();index++)
        {
            var slip=ValidationHandler.checkLine(index);
            if(! slip.mistake().equals("No Error"))
                errors.add(slip);
        }
        return errors;
    }

    /** This function checks a single line of ASL-8086 code for syntax errors
     @author Watheeq
     @param code The user written ASL-8086 code
     @return an Optional SyntaxSlip record, only a single SyntaxSlip is reported even though there might be multiple mistakes in a single line.
     */

    public static SyntaxSlip checkLine(int lineNumber)
    {
        String[] tokens
                =Tokeniser.tokeniseCode(
                CodeHandler.getCode(lineNumber)
        );
        var mistake=Validator.findMistakeInTokens(tokens);
        return new SyntaxSlip(lineNumber,mistake);
    }

}// end of class