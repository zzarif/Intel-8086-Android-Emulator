package com.salikoon.emulator8086.hardware;

public class Registers {
    
    //The indexes of the registers in the array
    public static final int AX_INDEX = 0;
    public static final int BX_INDEX = 1;
    public static final int CX_INDEX = 2;
    public static final int DX_INDEX = 3;
    public static final int SP_INDEX = 4;
    public static final int BP_INDEX = 5;
    public static final int SI_INDEX = 6;
    public static final int DI_INDEX = 7;
    
    public static final int ES_INDEX = 0;
    public static final int CS_INDEX = 1;
    public static final int SS_INDEX = 2;
    public static final int DS_INDEX = 3;
    
    // Will be used to set and and get  flags from the flag integer
    private static final int CARRY_FLAG_MASK = 0x0001;
    private static final int PARITY_FLAG_MASK = 0x0004;
    private static final int AUXILIARY_FLAG_MASK = 0x0010;
    private static final int ZERO_FLAG_MASK = 0x0040;
    private static final int SIGN_FLAG_MASK = 0x0080;
    private static final int TRAP_FLAG_MASK = 0x0100;
    private static final int INTERRUPT_FLAG_MASK = 0x0200;
    private static final int DIRECTION_FLAG_MASK = 0x0400;
    private static final int OVERFLOW_FLAG_MASK = 0x0800;
    
    public static int[] reg = new int[8];
    private static int[] sreg = new int[4];
    
    private static int ip;
    
    private static int flags;
    
    //Constructor to set the sizes and values of the registers
    public Registers() {
        reset();
    }
    
    //sets values of all registers to 0
    public static void reset() {
        for (int i = 0; i < 8; ++i) {
            reg[i] = 0;
        }
        
        for (int i = 0; i < 4; ++i) {
            sreg[i] = 0;
        }
    }
    
    //will return the values of the 16bit registers
    public static int getAX() {
        return reg[AX_INDEX];
    }
    
    public static int getBX() {
        return reg[BX_INDEX];
    }
    
    public static int getCX() {
        return reg[CX_INDEX];
    }
    
    public static int getDX() {
        return reg[DX_INDEX];
    }
    
    public static int getSP() {
        return reg[SP_INDEX];
    }
    
    public static int getBP() {
        return reg[BP_INDEX];
    }
    
    public static int getSI() {
        return reg[SI_INDEX];
    }
    
    public static int getDI() {
        return reg[DI_INDEX];
    }
    
    public static int getES() {
        return sreg[ES_INDEX];
    }
    
    public static int getCS() {
        return sreg[CS_INDEX];
    }
    
    public static int getSS() {
        return sreg[SS_INDEX];
    }
    
    public static int getDS() {
        return sreg[DS_INDEX];
    }
    
    public static int getReg(int index) {
        return reg[index];
    }
    
    public static int getSegReg(int index) {
        return sreg[index];
    }
    
    //sets values of the registers -- only takes last 16 bits of input integer
    public static void setAX(int v) {
        reg[AX_INDEX] = v & 0xffff;
    }
    
    public static void setCX(int v) {
        reg[CX_INDEX] = v & 0xffff;
    }

    public static void setDX(int v) {
        reg[DX_INDEX] = v & 0xffff;
    }
    
    public static void setBX(int v) {
        reg[BX_INDEX] = v & 0xffff;
    }
    
    public static void setSP(int v) {
        reg[SP_INDEX] = v & 0xffff;
    }

    public static void setBP(int v) {
        reg[BP_INDEX] = v & 0xffff;
    }

    public static void setSI(int v) {
        reg[SI_INDEX] = v & 0xffff;
    }

    public static void setDI(int v) {
        reg[DI_INDEX] = v & 0xffff;
    }

    public static void setES(int v) {
        sreg[ES_INDEX] = v & 0xffff;
    }
    
    public static void setCS(int v) {
        sreg[CS_INDEX] = v & 0xffff;
    }
    
    public static void setSS(int v) {
        sreg[SS_INDEX] = v & 0xffff;
    }
    
    public static void setDS(int v) {
        sreg[DS_INDEX] = v & 0xffff;
    }
    
    public static void setReg(int index, int v) {
        reg[index] = v & 0xffff;
    }
    
    public static void setSegReg(int index, int v) {
        sreg[index] = v & 0xffff;
    }
    
    public static int getIP() {
        return ip;
    }
    
    public static void setIP(int v) {
        ip = v & 0xffff;
    }
    
    //gets and sets values of the higher or lower 8 bits, the other part remains same
    public static byte getAL() {
        return (byte) reg[AX_INDEX];
    }
    
    public static byte getAH() {
        return (byte) (reg[AX_INDEX] >> 8);
    }
	
    public static byte getBL() {
        return (byte) reg[BX_INDEX];
    }
    
    public static byte getBH() {
        return (byte) (reg[BX_INDEX] >> 8);
    }
	
    public static byte getCL() {
        return (byte) reg[CX_INDEX];
    }
    
    public static byte getCH() {
        return (byte) (reg[CX_INDEX] >> 8);
    }
	
    public static byte getDL() {
        return (byte) reg[DX_INDEX];
    }
    
    public static byte getDH() {
        return (byte) (reg[DX_INDEX] >> 8);
    }
    
    
    
    public static void setAL(byte v) {
        reg[AX_INDEX] &= 0xff00;
        reg[AX_INDEX] |= v & 0xff;
    }
    
    public static void setAH(byte v) {
        reg[AX_INDEX] &= 0x00ff;
        reg[AX_INDEX] |= ((v & 0xff) << 8);
    }
	
    public static void setBL(byte v) {
        reg[BX_INDEX] &= 0xff00;
        reg[BX_INDEX] |= v & 0xff;
    }
    
    public static void setBH(byte v) {
        reg[BX_INDEX] &= 0x00ff;
        reg[BX_INDEX] |= ((v & 0xff) << 8);
    }
	
    public static void setCL(byte v) {
        reg[CX_INDEX] &= 0xff00;
        reg[CX_INDEX] |= v & 0xff;
    }
    
    public static void setCH(byte v) {
        reg[CX_INDEX] &= 0x00ff;
        reg[CX_INDEX] |= ((v & 0xff) << 8);
    }
	
    public static void setDL(byte v) {
        reg[DX_INDEX] &= 0xff00;
        reg[DX_INDEX] |= v & 0xff;
    }
    
    public static void setDH(byte v) {
        reg[DX_INDEX] &= 0x00ff;
        reg[DX_INDEX] |= ((v & 0xff) << 8);
    }

    //directly sets and gets all flag values with the help of an integer
    public static void setFlags(int value) {
        flags = value;
    }
    
    public static int getFlags() {
        return flags;
    }
    
    //takes mask and bool value to change the required bit in the flag integer
    private static void setFlag(int mask, boolean value) {
        if (value) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }
    }
    
    //returns the bool value of the flag
    private static boolean getFlag(int mask) {
        return (flags & mask) == mask;
    }
    
    //gets and sets flags
    public static boolean getCarryFlag() {
        return getFlag(CARRY_FLAG_MASK);
    }
    
    public static boolean getParityFlag() {
        return getFlag(PARITY_FLAG_MASK);
    }
    
    public static boolean getAuxiliaryFlag() {
        return getFlag(AUXILIARY_FLAG_MASK);
    }
    
    public static boolean getZeroFlag() {
        return getFlag(ZERO_FLAG_MASK);
    }
    
    public static boolean getSignFlag() {
        return getFlag(SIGN_FLAG_MASK);
    }
    
    public static boolean getTrapFlag() {
        return getFlag(TRAP_FLAG_MASK);
    }
    
    public static boolean getInterruptFlag() {
        return getFlag(INTERRUPT_FLAG_MASK);
    }
    
    public static boolean getDirectionFlag() {
        return getFlag(DIRECTION_FLAG_MASK);
    }
    
    public static boolean getOverflowFlag() {
        return getFlag(OVERFLOW_FLAG_MASK);
    }
    
    public static void setCarryFlag(boolean value) {
        setFlag(CARRY_FLAG_MASK, value);
    }
    
    public static void setParityFlag(boolean value) {
        setFlag(PARITY_FLAG_MASK, value);
    }
    
    public static void setAuxiliaryFlag(boolean value) {
        setFlag(AUXILIARY_FLAG_MASK, value);
    }
    
    public static void setZeroFlag(boolean value) {
        setFlag(ZERO_FLAG_MASK, value);
    }
    
    public static void setSignFlag(boolean value) {
        setFlag(SIGN_FLAG_MASK, value);
    }
    
    public static void setTrapFlag(boolean value) {
        setFlag(TRAP_FLAG_MASK, value);
    }
    
    public static void setInterruptFlag(boolean value) {
        setFlag(INTERRUPT_FLAG_MASK, value);
    }
    
    public static void setDirectionFlag(boolean value) {
        setFlag(DIRECTION_FLAG_MASK, value);
    }
    
    public static void setOverflowFlag(boolean value) {
        setFlag(OVERFLOW_FLAG_MASK, value);
    }
           
    
}