//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ
package com.salikoon.emulator8086.instructions;

import com.salikoon.emulator8086.hardware.MemoryHandler;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.parser.Parser;

public interface ArithmeticAndBitManipulationInstructions 
{
    public default void ADD(String destination, String source)
    {
        var isWordOperation=Parser.Analyser.is16BitOperation(destination, source);
        var augend = MemoryHandler.getValue(destination, isWordOperation);
        var addend = MemoryHandler.getValue(source, isWordOperation);
        var sum = augend + addend;
        MemoryHandler.setValue(destination, (short) sum);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(sum, isWordOperation);

        //CarryFlag
        if (isWordOperation)
        {
            if (sum > 65535)
            {
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
            }
        }
        else
        {
            if (sum > 255)
            {
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
            }
        }

        //Overflow flag
        if (isWordOperation)
        {
            if (sum > 32767 || sum < -32767)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }
        else
        {
            if (sum > 127 || sum < -127)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }

        //Auxiliary Flag
        int temp;
        if ((augend & 0xf) + (addend & 0xf) > 0xf)
        {
            temp = 1;
        }
        else
        {
            temp = 0;
        }
        MemoryHandler.setValue(StringParameter.AuxiliaryFlag, (short) temp);

    }

    public default void SUB(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var minuend = MemoryHandler.getValue(destination, isWordOperation);
        var subtrahend = MemoryHandler.getValue(source, isWordOperation);
        var difference = minuend - subtrahend;
        MemoryHandler.setValue(destination, (short) difference);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(difference, isWordOperation);

        //CarryFlag
        if (subtrahend > minuend)
        {
            MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
        }
        else
        {
            MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        }

        //Overflow flag
        if (isWordOperation)
        {
            if (difference > 32767 || difference < -32767)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }
        else
        {
            if (difference > 127 || difference < -127)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }

        //Auxiliary Flag
        int temp;
        if ((minuend & 0xf) < (subtrahend & 0xf))
        {
            temp = 1;
        }
        else
        {
            temp = 0;
        }
        MemoryHandler.setValue(StringParameter.AuxiliaryFlag, (short) temp);
    }

   public default void CMP(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var minuend = MemoryHandler.getValue(destination, isWordOperation);
        var subtrahend = MemoryHandler.getValue(source, isWordOperation);
        var difference = minuend - subtrahend;
        

        //updates Parity, Zero and Sign flags
        updatePZSFlags(difference, isWordOperation);

        //CarryFlag
        if (subtrahend > minuend)
        {
            MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
        }
        else
        {
            MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        }

        //Overflow flag
        if (isWordOperation)
        {
            if (difference > 32767 || difference < -32767)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }
        else
        {
            if (difference > 127 || difference < -127)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }

        //Auxiliary Flag
        int temp;
        if ((minuend & 0xf) < (subtrahend & 0xf))
        {
            temp = 1;
        }
        else
        {
            temp = 0;
        }
        MemoryHandler.setValue(StringParameter.AuxiliaryFlag, (short) temp);
    }


    public default void MUL(String multiplierMemoryElement)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(multiplierMemoryElement);
        var multiplier = MemoryHandler.getValue(multiplierMemoryElement, isWordOperation);
        short multiplicand;
        int product;
        if (!isWordOperation)
        {
            multiplicand = MemoryHandler.getValue(StringParameter.AL);
            product = multiplicand * multiplier;
            MemoryHandler.setValue(StringParameter.AX, (short) product);
        }
        else
        {
            multiplicand = MemoryHandler.getValue(StringParameter.AX);
            product = multiplicand * multiplier;
            MemoryHandler.setValue(StringParameter.AX, (short) product);
            MemoryHandler.setValue(StringParameter.DX, (short) (product >> 16));
        }
        
        //Overflow and Carry flags
        if (isWordOperation)
        {
            if (MemoryHandler.getValue(StringParameter.DX) != 0)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
            }

            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
            }
        }
        else
        {
            if (MemoryHandler.getValue(StringParameter.AH) != 0)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 1);
            }

            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
                MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
            }
        }

    }

    public default void DIV(String divisorMemoryElement)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(divisorMemoryElement);
        var divisor = MemoryHandler.getValue(divisorMemoryElement, isWordOperation);
        int dividend;
        if (!isWordOperation)
        {
            dividend = MemoryHandler.getValue(StringParameter.AX);
            var quotient = dividend / divisor;
            var remainder = dividend % divisor;
            MemoryHandler.setValue(StringParameter.AL, (short) quotient);
            MemoryHandler.setValue(StringParameter.AH, (short) remainder);
        }
        else
        {
            dividend = MemoryHandler.getValue(StringParameter.DX) * 0xFFFF + MemoryHandler.getValue(StringParameter.AX);
            var quotient = dividend / divisor;
            var remainder = dividend % divisor;
            MemoryHandler.setValue(StringParameter.AX, (short) quotient);
            MemoryHandler.setValue(StringParameter.DX, (short) remainder);

        }

        //All flags are undefined
    }

    public default void AND(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var operand1 = MemoryHandler.getValue(destination, isWordOperation);
        var operand2 = MemoryHandler.getValue(source, isWordOperation);
        var and = operand1 & operand2;
        MemoryHandler.setValue(destination, (short) and);

        //Carry flag and Overflow flag will be 0 in all cases
        MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(and, isWordOperation);
    }

    public default void TEST(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var operand1 = MemoryHandler.getValue(destination, isWordOperation);
        var operand2 = MemoryHandler.getValue(source, isWordOperation);
        var test = operand1 & operand2;

        //Carry flag and Overflow flag will be 0 in all cases
        MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(test, isWordOperation);
    }

    public default void OR(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var operand1 = MemoryHandler.getValue(destination, isWordOperation);
        var operand2 = MemoryHandler.getValue(source, isWordOperation);
        var or = operand1 | operand2;
        MemoryHandler.setValue(destination, (short) or);

        //Carry flag and Overflow flag will be 0 in all cases
        MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(or, isWordOperation);
    }

    public default void XOR(String destination, String source)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination, source);
        var operand1 = MemoryHandler.getValue(destination, isWordOperation);
        var operand2 = MemoryHandler.getValue(source, isWordOperation);
        var xor = operand1 ^ operand2;
        MemoryHandler.setValue(destination, (short) xor);

        //Carry flag and Overflow flag will be 0 in all cases
        MemoryHandler.setValue(StringParameter.CarryFlag, (short) 0);
        MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);

        //updates Parity, Zero and Sign flags
        updatePZSFlags(xor, isWordOperation);
    }

    public default void NOT(String destination)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination);
        var operand = MemoryHandler.getValue(destination, isWordOperation);
        var not = ~operand;
        MemoryHandler.setValue(destination, (short) not);
        //NOT does not affect any flags
    }

    public default void NEG(String destination)
    {

        NOT(destination);
        INC(destination);
    }

    public default void INC(String destination)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination);
        var operand = MemoryHandler.getValue(destination, isWordOperation);
        var res = operand + 1;
        MemoryHandler.setValue(destination, (short) res);

        //Overflow flag
        if (isWordOperation)
        {
            if (res > 32767 || res < -32767)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }
        else
        {
            if (res > 127 || res < -127)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }

        //updates Parity, Zero and Sign flags
        updatePZSFlags(res, isWordOperation);

        //Auxiliary Flag
        int temp;
        if ((operand & 0xf) + 1 > 0xf)
        {
            temp = 1;
        }
        else
        {
            temp = 0;
        }
        MemoryHandler.setValue(StringParameter.AuxiliaryFlag, (short) temp);

        //INC does not affect Carry Flag
    }

    public default void DEC(String destination)
    {
        var isWordOperation = Parser.Analyser.is16BitOperation(destination);
        var operand = MemoryHandler.getValue(destination, isWordOperation);
        var res = operand - 1;
        MemoryHandler.setValue(destination, (short) res);

        //Overflow flag
        if (isWordOperation)
        {
            if (res > 32767 || res < -32767)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }
        else
        {
            if (res > 127 || res < -127)
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.OverflowFlag, (short) 0);
            }
        }

        //updates Parity, Zero and Sign flags
        updatePZSFlags(res, isWordOperation);

        //Auxiliary Flag
        int temp;
        if ((operand & 0xf) == 0)
        {
            temp = 1;
        }
        else
        {
            temp = 0;
        }
        MemoryHandler.setValue(StringParameter.AuxiliaryFlag, (short) temp);

        //DEC does not affect Carry Flag
    }

    private void updatePZSFlags(int input, boolean isWord)
    {
        //Set ParityFlag -- only checks low 8 bits even if it is a word -- according to 8086 documentation
        if (Integer.bitCount((int) (byte) input) % 2 == 0)
        {
            MemoryHandler.setValue(StringParameter.ParityFlag, (short) 1);
        }
        else
        {
            MemoryHandler.setValue(StringParameter.ParityFlag, (short) 0);
        }

        //ZeroFlag
        if (input == 0)
        {
            MemoryHandler.setValue(StringParameter.ZeroFlag, (short) 1);
        }
        else
        {
            MemoryHandler.setValue(StringParameter.ZeroFlag, (short) 0);
        }

        //Set SignFlag 
        if (isWord)
        {
            if ((32768 & input) == 32768)
            {
                MemoryHandler.setValue(StringParameter.SignFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.SignFlag, (short) 0);
            }
        }
        else
        {
            if ((128 & input) == 128)
            {
                MemoryHandler.setValue(StringParameter.SignFlag, (short) 1);
            }
            else
            {
                MemoryHandler.setValue(StringParameter.SignFlag, (short) 0);
            }
        }

    }
    
    
    
}//end of file    