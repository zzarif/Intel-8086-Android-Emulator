package com.salikoon.emulator8086.hardware;

import java.util.Arrays;

public class RAM
{

    //main memory array set size of RAM to 1MB
    private static byte[] mem = new byte[0x100000];

    public RAM()
    {

    }
    
    public static void reset()
    {
        Arrays.fill(mem, (byte)0);
    }

    //copy existing byte array to mem array. Useful for loading saved data from storage.
    public static void loadData(int start_addr, byte[] data)
    {
        System.arraycopy(data, 0, mem, start_addr, data.length);
    }

    //returns the byte stored in the address
    public static byte getByte(int addr)
    {
        return mem[addr];
    }

    //returns 2 bytes - one stored at the given address, and the one after it
    public static short getWord(int addr)
    {
        return (short) ((mem[addr] & 0xff) | (mem[addr + 1] << 8));
    }

    //sets value of a given address
    public static void setByte(int addr, byte v)
    {
        mem[addr] = v;
    }

    //sets a word. The LSB is stored at given address and MSB is stored in the address after it
    public static void setWord(int addr, short v)
    {
        mem[addr] = (byte) v;
        mem[addr + 1] = (byte) (v >> 8);
    }
}
