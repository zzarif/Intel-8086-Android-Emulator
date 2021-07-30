package com.salikoon.emulator8086.handlers;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.models.UIPacket;

public class UIHandler
{

    public static void setCode(String[] code)
    {
        CodeHandler.setCode(code);
    }

    public static UIPacket execute()
    {
        return ExecutionHandler.execute();
    }


}//end of file