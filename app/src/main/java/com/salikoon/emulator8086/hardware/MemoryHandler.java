package com.salikoon.emulator8086.hardware;

import java.util.Vector;

/**
 *
 * @author Mohammed Mohaimen
 */
public class MemoryHandler
{

    /**
     *
     */
    public MemoryHandler()
    {
        
    }

    //returns value of all registers and segment registers
    private static int callRegister(String inp) throws InvalidRegisterNameException
    {

        String[] reg_array = new String[]
        {
            "AX", "BX", "CX", "DX", "SP", "BP", "SI", "DI"
        };
        String[] sreg_array = new String[]
        {
            "ES", "CS", "SS", "DS"
        };

        for (int x = 0; x < 8; x++)
        {
            if (inp.equals(reg_array[x]))
            {
                //System.out.print("Call GetReg " + x);
                return Registers.getReg(x);
            }
        }
        for (int x = 0; x < 4; x++)
        {
            if (inp.equals(sreg_array[x]))
            {
                //System.out.print("Call GetSegReg " + x);
                return Registers.getSegReg(x);
            }
        }
        switch (inp)
        {
            case "IP":
                return (int) Registers.getIP();
            case "AL":
                return (int) Registers.getAL();
            case "BL":
                return (int) Registers.getBL();
            case "CL":
                return (int) Registers.getCL();
            case "DL":
                return (int) Registers.getDL();
            case "AH":
                return (int) Registers.getAH();
            case "BH":
                return (int) Registers.getBH();
            case "CH":
                return (int) Registers.getCH();
            case "DH":
                return (int) Registers.getDH();
            default:
                break;
        }

        throw new InvalidRegisterNameException(inp + " is not a valid Register Name");

    }
    
    private static void setRegister(String inp, int value) throws InvalidRegisterNameException
    {

        String[] reg_array = new String[]
        {
            "AX", "BX", "CX", "DX", "SP", "BP", "SI", "DI"
        };
        String[] sreg_array = new String[]
        {
            "ES", "CS", "SS", "DS"
        };

        for (int x = 0; x < 8; x++)
        {
            if (inp.equals(reg_array[x]))
            {
                Registers.setReg(x, value);
                LoggerManager.insert_into_Log(inp, (short) value);
                //System.out.print("Call SetReg " + x);
                return;
            }
        }
        for (int x = 0; x < 4; x++)
        {
            if (inp.equals(sreg_array[x]))
            {
                Registers.setSegReg(x, value);
                LoggerManager.insert_into_Log(inp, (short) value);
                //System.out.print("Call SetSegReg " + x);
                return;
            }
        }
        switch (inp)
        {
            case "AL":
                Registers.setAL((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "BL":
                Registers.setBL((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "CL":
                Registers.setCL((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "DL":
                Registers.setDL((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "AH":
                Registers.setAH((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "BH":
                Registers.setBH((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "CH":
                Registers.setCH((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "DH":
                Registers.setDH((byte) value);
                LoggerManager.insert_into_Log(inp, (byte) value);
                return;
            case "IP":
                Registers.setIP(value);
                LoggerManager.insert_into_Log(inp, (short) value);
                return;
        }
        throw new InvalidRegisterNameException(inp + " is not a valid Register Name");

        //System.out.println("here");
    }
    
    
    public static short getValue(String inp_string) throws AddressOutOfRangeException, InvalidRegisterNameException
    {

        inp_string = inp_string.trim();

        //for returning ascii values of characters
        if (inp_string.length() < 2 && Character.isAlphabetic(inp_string.charAt(0)))
        {
            throw new InvalidRegisterNameException(inp_string + " is not a valid Register Name");
        }
        if (inp_string.charAt(0) == '\'')
        {
            int tempv = (int) inp_string.charAt(1);
            return (short) tempv;
        }
        String orginp = new String();
        orginp = inp_string;
        inp_string = inp_string.toUpperCase();
        int len = inp_string.length();

        //For RAM addresses ie with []
        if (inp_string.charAt(0) == '[')
        {
            Vector<String> list = new Vector<String>();
            int current = 1;
            for (int x = 1; x < len; x++)
            {
                if (inp_string.charAt(x) == '[' || inp_string.charAt(x) == ']' || inp_string.charAt(x) == '+')
                {
                    list.add(inp_string.substring(current, x));
                    current = x + 1;
                    if (current < len && inp_string.charAt(current) == '[')
                    {
                        current++;
                        x++;
                    }
                }

            }
            //System.out.println("Vector list is " + list);

            int total_add = 0;
            for (int x = 0; x < list.size(); x++)
            {
                //direct value in dec, hex or bin
                if (Character.isDigit(list.get(x).charAt(0)) || list.get(x).charAt(list.get(x).length() - 1) == 'H' || list.get(x).charAt(list.get(x).length() - 1) == 'B')
                {
                    if ((list.get(x).charAt(list.get(x).length() - 1)) == 'H')
                    {
                        total_add += Integer.parseInt(list.get(x).substring(0, list.get(x).length() - 1), 16);
                    }
                    else if ((list.get(x).charAt(list.get(x).length() - 1)) == 'B')
                    {
                        total_add += Integer.parseInt(list.get(x).substring(0, list.get(x).length() - 1), 2);
                    }
                    else
                    {
                        total_add += Integer.parseInt(list.get(x));
                    }
                }

                //for register calls
                else if (Character.isAlphabetic(list.get(x).charAt(0)))
                {
                    total_add += callRegister(list.get(x));
                }
            }
            total_add += (Registers.getDS() * 16);
            if (total_add < 0 || total_add > 1048576)
            {
                throw new AddressOutOfRangeException(total_add + "d is not suitable for 1MB RAM");
            }
            //System.out.println("RAM address get word " + total_add);
            return (short) RAM.getWord(total_add);
        }

        //For direct register calls
        else if (Character.isAlphabetic(inp_string.charAt(0)) && len == 2)
        {
            return (short) callRegister(inp_string);
        }

        //For direct value in dec, hex or bin
        else if (Character.isDigit(inp_string.charAt(0)) || inp_string.charAt(len - 1) == 'H' || inp_string.charAt(0) == '-')
        {
            if (inp_string.charAt(len - 1) == 'H')
            {
                return (short) Integer.parseInt(inp_string.substring(0, len - 1), 16);
            }
            else if (inp_string.charAt(len - 1) == 'B')
            {
                return (short) Integer.parseInt(inp_string.substring(0, len - 1), 2);
            }
            else if (inp_string.charAt(len - 1) == 'D')
            {
                return (short) Integer.parseInt(inp_string.substring(0, len - 1));
            }
            else
            {
                return (short) Integer.parseInt(inp_string);
            }
        }

        //For flags 
        else
        {
            boolean boolconvert = false;
            switch (orginp)
            {
                case StringParameter.CarryFlag:
                    boolconvert = Registers.getCarryFlag();
                    break;
                case StringParameter.ParityFlag:
                    boolconvert = Registers.getParityFlag();
                    break;
                case StringParameter.AuxiliaryFlag:
                    boolconvert = Registers.getAuxiliaryFlag();
                    break;
                case StringParameter.ZeroFlag:
                    boolconvert = Registers.getZeroFlag();
                    break;
                case StringParameter.SignFlag:
                    boolconvert = Registers.getSignFlag();
                    break;
                case StringParameter.TrapFlag:
                    boolconvert = Registers.getTrapFlag();
                    break;
                case StringParameter.InterruptFlag:
                    boolconvert = Registers.getInterruptFlag();
                    break;
                case StringParameter.DirectionFlag:
                    boolconvert = Registers.getDirectionFlag();
                    break;
                case StringParameter.OverflowFlag:
                    boolconvert = Registers.getOverflowFlag();
                    break;
                default:
                    break;
            }
            if (boolconvert == true)
            {
                return (short) 1;
            }
            else
            {
                return (short) 0;
            }

        }

    }
    
    public static void setValue(String inp_string, short value) throws InvalidRegisterNameException, AddressOutOfRangeException
    {

        inp_string = inp_string.trim();
        if (inp_string.length() < 2)
        {
            throw new InvalidRegisterNameException(inp_string + " is not a valid Register Name");
        }
        String orginp = new String();
        orginp = inp_string;
        inp_string = inp_string.toUpperCase();
        int len = inp_string.length();

        //For RAM addresses ie with []
        if (inp_string.charAt(0) == '[')
        {
            Vector<String> list = new Vector<String>();
            int current = 1;
            for (int x = 1; x < len; x++)
            {
                if (inp_string.charAt(x) == '[' || inp_string.charAt(x) == ']' || inp_string.charAt(x) == '+')
                {
                    list.add(inp_string.substring(current, x));
                    current = x + 1;
                    if (current < len && inp_string.charAt(current) == '[')
                    {
                        current++;
                        x++;
                    }
                }

            }
            //System.out.println("Vector list is " + list);

            int total_add = 0;
            for (int x = 0; x < list.size(); x++)
            {
                //direct value in dec, hex or bin
                if (Character.isDigit(list.get(x).charAt(0)) || list.get(x).charAt(list.get(x).length() - 1) == 'H' || list.get(x).charAt(list.get(x).length() - 1) == 'B')
                {
                    if ((list.get(x).charAt(list.get(x).length() - 1)) == 'H')
                    {
                        total_add += Integer.parseInt(list.get(x).substring(0, list.get(x).length() - 1), 16);
                    }
                    else if ((list.get(x).charAt(list.get(x).length() - 1)) == 'B')
                    {
                        total_add += Integer.parseInt(list.get(x).substring(0, list.get(x).length() - 1), 2);
                    }
                    else
                    {
                        total_add += Integer.parseInt(list.get(x));
                    }
                }

                //for register calls
                else if (Character.isAlphabetic(list.get(x).charAt(0)))
                {
                    total_add += callRegister(list.get(x));
                }
            }
            total_add += (Registers.getDS() * 16);
            if (total_add < 0 || total_add > 1048576)
            {
                throw new AddressOutOfRangeException(total_add + "d is not suitable for 1MB RAM");
            }
            int tempv = (value >> 8);
            if (tempv == 0)
            {
                RAM.setByte(total_add, (byte) value);
                LoggerManager.insert_into_Log(String.valueOf(total_add), value);
            }
            else
            {
                RAM.setWord(total_add, (short) value);
                LoggerManager.insert_into_Log(String.valueOf(total_add), (short) (byte) value);
                LoggerManager.insert_into_Log(String.valueOf(total_add + 1), (short) (byte) (value >> 8));
            }
            //System.out.println("RAM address set word " + total_add);
        }

        //For direct register calls
        else if (Character.isAlphabetic(inp_string.charAt(0)) && len == 2)
        {
            setRegister(inp_string, (int) value);
        }
        else
        {
            boolean boolconvert = false;
            if (value == 1)
            {
                boolconvert = true;
            }
            else
            {
                boolconvert = false;
            }
            switch (orginp)
            {
                case StringParameter.CarryFlag:
                    Registers.setCarryFlag(boolconvert);
                    break;
                case StringParameter.ParityFlag:
                    Registers.setParityFlag(boolconvert);
                    break;
                case StringParameter.AuxiliaryFlag:
                    Registers.setAuxiliaryFlag(boolconvert);
                    break;
                case StringParameter.ZeroFlag:
                    Registers.setZeroFlag(boolconvert);
                    break;
                case StringParameter.SignFlag:
                    Registers.setSignFlag(boolconvert);
                    break;
                case StringParameter.TrapFlag:
                    Registers.setTrapFlag(boolconvert);
                    break;
                case StringParameter.InterruptFlag:
                    Registers.setInterruptFlag(boolconvert);
                    break;
                case StringParameter.DirectionFlag:
                    Registers.setDirectionFlag(boolconvert);
                    break;
                case StringParameter.OverflowFlag:
                    Registers.setOverflowFlag(boolconvert);
                    break;
                default:
                    break;
            }
            LoggerManager.insert_into_Log(orginp, (short) value);
        }

    }
    
    public static void resetLogger()
    {
        LoggerManager.resetLog();  
    }
    
    public static Logger getLogger()
    {
        return LoggerManager.getLog();       
    }
    
    public static void setValue (String inp_string, short value, boolean sizeIsWord) throws InvalidRegisterNameException, AddressOutOfRangeException
    {
        if(! sizeIsWord) setValue(inp_string,(short) (byte) value);
        else setValue(inp_string,(short)value);
        
    }
    
    public static short getValue (String inp_string, boolean sizeIsWord) throws AddressOutOfRangeException, InvalidRegisterNameException
    {
         if(! sizeIsWord) return (short)(byte) getValue(inp_string);
        else return (short) getValue(inp_string);
        
    }
    
    public static void resetHardware()
    {
        LoggerManager.resetLog();
        Registers.reset();
        RAM.reset();
    }
    
    public static void updateFlags(int input, boolean isWord) throws InvalidRegisterNameException, AddressOutOfRangeException
    {
        //CarryFlag
        if(isWord)
        {
            if(input > 65535)MemoryHandler.setValue(StringParameter.CarryFlag, (short)1);
            else MemoryHandler.setValue(StringParameter.CarryFlag, (short)0);
        }
        else
        {
            if(input > 255)MemoryHandler.setValue(StringParameter.CarryFlag, (short)1);
            else MemoryHandler.setValue(StringParameter.CarryFlag, (short)0);
        }
        
        //Set ParityFlag -- only checks low 8 bits even if it is a word -- according to 8086 documentation
        if(Integer.bitCount((int)(byte)input) % 2 == 0)MemoryHandler.setValue(StringParameter.ParityFlag, (short)1);
        else MemoryHandler.setValue(StringParameter.ParityFlag, (short)0);
        
        
        
        //ZeroFlag
        if(input == 0)MemoryHandler.setValue(StringParameter.ZeroFlag, (short)1);
        else MemoryHandler.setValue(StringParameter.ZeroFlag, (short)0);
        
        
        
        //Set SignFlag 
        if(isWord)
        {
            if((32768 & input) == 32768)MemoryHandler.setValue(StringParameter.SignFlag, (short)1);
            else MemoryHandler.setValue(StringParameter.SignFlag, (short)0);
        }
        else
        {
            if((128 & input) == 128)MemoryHandler.setValue(StringParameter.SignFlag, (short)1);
            else MemoryHandler.setValue(StringParameter.SignFlag, (short)0);
        }
        
        //TrapFlag        
        //InterruptFlag
        //DirectionFlag        
        //AuxiliaryFlag 
        //OverflowFlag
    }
     
} //end of file
