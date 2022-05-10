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
        
        return 
        IntStream.rangeClosed(1,CodeHandler.getLastLineNumberOfCode())
        .mapToObj(ValidationHandler::checkLine)
        .map(x -> {
            if (x.isPresent())
                return x.get();
            else return null;
        })
        .filter(syntaxSlip -> syntaxSlip!=null)
        .collect(Collectors.toList());
              
    }
        
        /** This function checks a single line of ASL-8086 code for syntax errors
        @author Watheeq
        @param code The user written ASL-8086 code
        @return an Optional SyntaxSlip record, only a single SyntaxSlip is reported even though there might be multiple mistakes in a single line.
        */
    
        public static Optional<SyntaxSlip> checkLine(int lineNumber)
        {
        String[] tokens
        =Tokeniser.tokeniseCode(
                CodeHandler.getCode(lineNumber)
                );
        var mistake=Validator.findMistakeInTokens(tokens);
        if(mistake.isPresent()) return Optional.of( new SyntaxSlip(lineNumber,mistake.get() ) );         
        else return Optional.<SyntaxSlip>empty();    
        }    
    
        public static java.util.List<String> getAllImplementedInstructions()
        {
            return ValidationLibrary.getListOfImplementedInstructions();
        }
    
    
}// end of class