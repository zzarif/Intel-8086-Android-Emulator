package com.salikoon.emulator8086.utility;

import com.salikoon.emulator8086.ui_helper.UIPacket;

import java.util.LinkedList;

public class ExecutionDirector {
    java.util.LinkedList<UIPacket> trail=new LinkedList<UIPacket>();
    int index=-1;

    public void add(UIPacket newPacket){
        if(canExecute()) {
            index++;
            trail.add(newPacket);
        }
        else {

            throw new IllegalStateException("Illegal operation, please do more redo operations");
        }
    }
    public UIPacket stepBack()
    {
        return trail.get(index--);
    }
    public UIPacket stepForward()
    {
        return trail.get(index++);
    }

    public boolean canExecute(){
        return index==trail.size()-1;
    }
}
