/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salikoon.emulator8086.handlers;

import com.salikoon.emulator8086.models.Logger;
import com.salikoon.emulator8086.models.RAM;
import com.salikoon.emulator8086.models.Registers;
import com.salikoon.emulator8086.utility.StringParameter;

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
    private static int callRegister(String inp)
    {
        String[] reg_array = new String[]{"AX","BX","CX","DX","SP","BP","SI","DI"};
        String[] sreg_array = new String[]{"ES","CS","SS","DS"};
        
        for(int x=0;x<8;x++)
        {
            if(inp.equals(reg_array[x]))
            {
                //System.out.print("Call GetReg " + x);
                return Registers.getReg(x);
            }
        }
        for(int x=0;x<4;x++)
        {
            if(inp.equals(sreg_array[x]))
            {
                //System.out.print("Call GetSegReg " + x);
                return Registers.getSegReg(x);
            }
        }
        switch (inp)
        {
            case "IP":
                return (int)Registers.getIP();
            case "AL":
                return (int)Registers.getAL();
            case "BL":
                return (int)Registers.getBL();
            case "CL":
                return (int)Registers.getCL();
            case "DL":
                return (int)Registers.getDL();
            case "AH":
                return (int)Registers.getAH();
            case "BH":
                return (int)Registers.getBH();
            case "CH":
                return (int)Registers.getCH();
            case "DH":
                return (int)Registers.getDH();
            default:
                break;
        }
        
        //throw exception -- use throwable
        return -1;
    }
    
    private static void setRegister(String inp, int value)
    {
        String[] reg_array = new String[]{"AX","BX","CX","DX","SP","BP","SI","DI"};
        String[] sreg_array = new String[]{"ES","CS","SS","DS"};
        
        for(int x=0;x<8;x++)
        {
            if(inp.equals(reg_array[x]))
            {
                Registers.setReg(x,value);
                LoggerManager.insert_into_Log(inp,(short)value);
                //System.out.print("Call SetReg " + x);
                //return;
            }
        }
        for(int x=0;x<4;x++)
        {
            if(inp.equals(sreg_array[x]))
            {
                Registers.setSegReg(x,value);
                LoggerManager.insert_into_Log(inp,(short)value);
                //System.out.print("Call SetSegReg " + x);
                //return;
            }
        }
        switch (inp)
        {
            case "AL":
                Registers.setAL((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "BL":
                Registers.setBL((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "CL":
                Registers.setCL((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "DL":
                Registers.setDL((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "AH":
                Registers.setAH((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "BH":
                Registers.setBH((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "CH":
                Registers.setCH((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "DH":
                Registers.setDH((byte) value);
                LoggerManager.insert_into_Log(inp,(byte)value);
                break;
            case "IP":
                Registers.setIP(value);
                LoggerManager.insert_into_Log(inp,(short)value);
                break;
            default:
                break;
        }       
        //System.out.println("here");
    }
    
    
    public static short getValue(String inp_string)
    {
        inp_string=inp_string.trim();
        
        //for returning ascii values of characters
        if(inp_string.charAt(0)=='\'')
        {
            int tempv=(int)inp_string.charAt(1);
            return (short)tempv;
        }
        String orginp = new String();
        orginp = inp_string;
        inp_string = inp_string.toUpperCase();
        int len=inp_string.length();

        //For RAM addresses ie with []
        if(inp_string.charAt(0)=='[')
        {
            Vector<String> list = new Vector<String>();
            int current=1;
            for (int x=1;x<len;x++)
            {
                if(inp_string.charAt(x)=='[' || inp_string.charAt(x)==']' || inp_string.charAt(x)=='+')
                {
                    list.add(inp_string.substring(current, x));
                    current=x+1;
                    if(current<len && inp_string.charAt(current)=='[')
                    {
                        current++;
                        x++;
                    }
                }
                
            }
            //System.out.println("Vector list is " + list);
            
            int total_add=0;
            for (int x=0;x<list.size();x++)
            {
                //direct value in dec, hex or bin
                if(Character.isDigit(list.get(x).charAt(0)) || list.get(x).charAt(list.get(x).length()-1)=='H' || list.get(x).charAt(list.get(x).length()-1)=='B')
                {
                    if((list.get(x).charAt(list.get(x).length()-1))=='H') total_add+=Integer.parseInt(list.get(x).substring(0, list.get(x).length()-1),16);
                    else if((list.get(x).charAt(list.get(x).length()-1))=='B') total_add+=Integer.parseInt(list.get(x).substring(0, list.get(x).length()-1),2);
                    else total_add+=Integer.parseInt(list.get(x));
                }
                
                //for register calls
                else if(Character.isAlphabetic(list.get(x).charAt(0)))
                {
                    total_add+=callRegister(list.get(x));
                }
            }
            total_add+=(Registers.getDS()*16);
            //System.out.println("RAM address get word " + total_add);
            return (short) RAM.getWord(total_add);
        }
        
        //For direct register calls
        else if(Character.isAlphabetic(inp_string.charAt(0)) && len == 2)
        {
            return (short) callRegister(inp_string);
        }
        
        //For direct value in dec, hex or bin
        else if(Character.isDigit(inp_string.charAt(0)) || inp_string.charAt(len-1)=='H')
        {
            if(inp_string.charAt(len-1)=='H') return (short) Integer.parseInt(inp_string.substring(0, len-1),16);
            else if(inp_string.charAt(len-1)=='B') return (short) Integer.parseInt(inp_string.substring(0, len-1),2);
            else if(inp_string.charAt(len-1)=='D') return (short) Integer.parseInt(inp_string.substring(0, len-1));
            else return (short) Integer.parseInt(inp_string);
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
            if(boolconvert==true)return (short) 1;
            else return (short) 0;
            
        }
        
    }
    
    public static void setValue(String inp_string, short value)
    {
        inp_string=inp_string.trim();
        String orginp = new String();
        orginp = inp_string;
        inp_string = inp_string.toUpperCase();
        int len=inp_string.length();
        
        //For RAM addresses ie with []
        if(inp_string.charAt(0)=='[')
        {
            Vector<String> list = new Vector<String>();
            int current=1;
            for (int x=1;x<len;x++)
            {
                if(inp_string.charAt(x)=='[' || inp_string.charAt(x)==']' || inp_string.charAt(x)=='+')
                {
                    list.add(inp_string.substring(current, x));
                    current=x+1;
                    if(current<len && inp_string.charAt(current)=='[')
                    {
                        current++;
                        x++;
                    }
                }
                
            }
            //System.out.println("Vector list is " + list);
            
            int total_add=0;
            for (int x=0;x<list.size();x++)
            {
                //direct value in dec, hex or bin
                if(Character.isDigit(list.get(x).charAt(0)) || list.get(x).charAt(list.get(x).length()-1)=='H' || list.get(x).charAt(list.get(x).length()-1)=='B')
                {
                    if((list.get(x).charAt(list.get(x).length()-1))=='H') total_add+=Integer.parseInt(list.get(x).substring(0, list.get(x).length()-1),16);
                    else if((list.get(x).charAt(list.get(x).length()-1))=='B') total_add+=Integer.parseInt(list.get(x).substring(0, list.get(x).length()-1),2);
                    else total_add+=Integer.parseInt(list.get(x));
                }
                
                //for register calls
                else if(Character.isAlphabetic(list.get(x).charAt(0)))
                {
                    total_add+=callRegister(list.get(x));
                }
            }
            total_add+=(Registers.getDS()*16);
            int tempv=(value >> 8);
            if(tempv==0)
            {
                RAM.setByte(total_add,(byte)value);
                LoggerManager.insert_into_Log(String.valueOf(total_add), value);
            }
            else 
            {
                RAM.setWord(total_add,(short)value);
                LoggerManager.insert_into_Log(String.valueOf(total_add), (short)(byte)value);
                LoggerManager.insert_into_Log(String.valueOf(total_add+1), (short)(byte)(value >> 8));
            }
            //System.out.println("RAM address set word " + total_add);
        }
        
        //For direct register calls
        else if(Character.isAlphabetic(inp_string.charAt(0)) && len == 2)
        {
            setRegister(inp_string,(int)value);
        }
        else
        {
            boolean boolconvert = false;
            if(value==1)boolconvert=true;
            else boolconvert=false;
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
            LoggerManager.insert_into_Log(orginp, (short)value);
            
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
    public static void setValue (String inp_string, short value, boolean sizeIsWord)
    {
        if(! sizeIsWord) setValue(inp_string,(short) (byte) value);
        else setValue(inp_string,(short)value);
        
     }
     public static short getValue (String inp_string, boolean sizeIsWord)
    {
         if(! sizeIsWord) return (short)(byte) getValue(inp_string);
        else return (short) getValue(inp_string);
        
     }
} //end of file
