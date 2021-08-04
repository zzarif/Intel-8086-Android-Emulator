package com.salikoon.emulator8086.ui_helper;//بِسْمِ اللَّهِ الرَّحْمٰنِ الرَّحِيْمِ

import com.salikoon.emulator8086.execution.ExecutionHandler;
import com.salikoon.emulator8086.user_code.CodeHandler;

public interface UIHandler
{

    static void setCode(String[] code)
    {
        CodeHandler.setCode(code);
    }

    static UIPacket execute()
    {
        return ExecutionHandler.execute();
    }

}//end of file