//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.syntax_check;

    /**
    This is a class to hold the line number and the syntax mistake which was detected therein
    @author Watheeq
    */
public class SyntaxSlip{
    
    private int lineNumber;
    private String mistake;
    
    public SyntaxSlip(int lineNumber, String mistake)
    {
    if(lineNumber<=0) throw new IllegalArgumentException("lineNumber has a value="+lineNumber+"which is not accepted as line number has to be a non-zero positive number");
//    if(mistake.isBlank()) throw new IllegalArgumentException("A blank String is not accepted, please write something to identify the mistake");
    this.lineNumber=lineNumber;
    this.mistake=mistake;
    
    }
    public int lineNumber()
    {
        return lineNumber;
    }
    public String mistake()
    {
        return mistake;
    }
    @Override
    public String toString()
    {
        return java.text.MessageFormat.format("line-number: {0} mistake: {1}",lineNumber,mistake);
    }
}
