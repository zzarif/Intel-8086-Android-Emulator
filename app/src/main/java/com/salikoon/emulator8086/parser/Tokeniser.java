//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

package com.salikoon.emulator8086.parser;

import java.util.StringTokenizer;

public class Tokeniser {
	/** This class breaks a line of code into separate tokens and removes comments
	*@author Fida
	*@purpose Tokenise various types of Strings containing one or more tokens
	*
	*/

	protected static String removeComments(String input) 	{
		/**This function removes comments from a line of user code.
		*@author Fida
		*@param A String containing a line of user code
		*@return It returns code that is stripped of comments in sha Allah
        *It is necessary to understand {@link java.util.String#split(String s) split} method to understand the code
		*Mechanism:
		*Whatever if written after semi-colon is a comment in ASL-8086, so this functions splits the input string across the semi-colon and then returns the first fragment.
		*If there was no semi-colon then the function returns the original string
		*/
		
		String[] fragments = input.split(";", 2);
		return fragments[0];
	}

	public static String[] tokeniseCode(String code) {
			/**This function converts a line of user code into separate String tokens. User comments are removed and not considered for tokenisation. It also removes all leading and trailing whitespace of each token that is returned in sha Allah.
		*@author Fida
		*@param A String containing a line of user code
		*@return A String array of ASL-8086 tokens
        *@see StringTokenizer
		*Mechanism:
		*The function removes ASL-8086 comments at first because otherwise they will also tokenised by the code that follows it. 
		*Then it uses StringTokenizer class to tokenize/tokenise and then puts the tokens in a String array and then returns the array.StringTokenizer also removes all leading and trailing whitespace in sha Allah.
		*/
		
		code = removeComments(code);
		String delimiters = " ,";
		var tokenizer = new StringTokenizer(code, delimiters);
		
		
        String[] tokens=getAllTokensAsStringArray(tokenizer);
        
        return tokens;

		}
	
	protected static String[] getAllTokensAsStringArray(StringTokenizer tokenizer)
	{
		/**This function extracts all tokens inside an initialised StringTokenizer and returns all tokens inside it. All tokens have their leading/trailing whitespace trimmed.
		*@author Fida
		*@param An initialised StringTokenizer object
		*@return A String[] containing all the tokens in sha Allah
		*@see StringTokenizer
		*/


        // Create a String[] with length equal to the number of tokens
		var tokens = new String[tokenizer.countTokens()];
		

        //Fetch all tokens from inside StringTokenizer and place them in tokens array
        //tokenizer.hasMoreTokens() returns false when there are no more tokens and is the breaking condition for the loop
		for (int index = 0; tokenizer.hasMoreTokens(); index++)
			tokens[index] = tokenizer.nextToken().trim();
		return tokens;
	}// end of getAllTokensAsStringArray()
	
	public static String[] tokeniseOperand(String operand) {
			/**This function converts an operand into separate String tokens. Useful to breakdown the operand in offset modes to get individual sub-operands. It also removes all leading and trailing whitespace of each token that is returned in sha Allah.
		*@author Fida
		*@param A String containing an operand
		*@return A String array of sub-operands
        *@see StringTokenizer
		*Mechanism:
		*Then it uses StringTokenizer class to tokenize/tokenise and then puts the tokens in a String array and then returns the array.It also removes all leading and trailing whitespace in sha Allah.
		*/
		
		String delimiters = ":[+]";
		var tokenizer = new StringTokenizer(operand, delimiters);
		
		
        String[] tokens=getAllTokensAsStringArray(tokenizer);
        

        
        return tokens;

		}//end of tokeniseOperand ()
	
	
		
		
		
		
	
		
		
		
		
	
	

}// end of class Tokeniser