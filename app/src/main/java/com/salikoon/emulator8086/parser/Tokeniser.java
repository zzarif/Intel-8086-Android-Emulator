//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.parser;

import java.util.StringTokenizer;

/** This class breaks a line of code into separate tokens and also removes comments on the process
	*@author Fida
	*
	*/
public class Tokeniser {

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

 /**This function converts a line of user code into separate String tokens. User comments are removed and not considered for tokenisation. It also removes all leading and trailing whitespace of each token that is returned in sha Allah.
		*<b>Mechanism:</b><br/> The function removes ASL-8086 comments at first because otherwise they will also tokenised by the code that follows it. 
		*Then it uses StringTokenizer class to tokenize/tokenise and then puts the tokens in a String array and then returns the array.StringTokenizer also removes all leading and trailing whitespace in sha Allah.
		*
		*@author Fida
		*@param code A String containing a line of user code
		*@return A String array of ASL-8086 tokens
		*/
    public static String[] tokeniseCode(String code)
    {
        code = removeComments(code);
        String delimiters = " ,";
        var tokenizer = new StringTokenizer(code, delimiters);

        String[] tokens = getAllTokensAsStringArray(tokenizer);

        return tokens;
    }
    /**This function extracts all tokens inside an initialised StringTokenizer and returns all tokens inside it. All tokens have their leading/trailing whitespace trimmed.
		*@author Fida
		*@param tokenizer An initialised StringTokenizer object
		*@return A String[] containing all the tokens in sha Allah
		*/
    private static String[] getAllTokensAsStringArray(StringTokenizer tokenizer)
    {

        // Create a String[] with length equal to the number of tokens
        var tokens = new String[tokenizer.countTokens()];

        //Fetch all tokens from inside StringTokenizer and place them in tokens array
        //tokenizer.hasMoreTokens() returns false when there are no more tokens and is the breaking condition for the loop
        for (int index = 0; tokenizer.hasMoreTokens(); index++)
            tokens[index] = tokenizer.nextToken().trim();
        return tokens;
    } // end of getAllTokensAsStringArray()

    /**This function converts an operand into separate String tokens. Useful to breakdown the operand in offset modes to get individual sub-operands. It also removes all leading and trailing whitespace of each token that is returned in sha Allah.
		*<b>Mechanism:</b> <br/>
		*It uses StringTokenizer class to tokenize/tokenise and then puts the tokens in a String array and then returns the array. It also removes all leading and trailing whitespace in sha Allah.
	*@author Fida
		*@param operand A String containing an operand
		*@return A String array of sub-operands
		*/
    public static String[] tokeniseOperand(String operand)
    {

        String delimiters = ":[+]";
        var tokenizer = new StringTokenizer(operand, delimiters);

        String[] tokens = getAllTokensAsStringArray(tokenizer);

        return tokens;

    } //end of tokeniseOperand ()

} // end of class Tokeniser