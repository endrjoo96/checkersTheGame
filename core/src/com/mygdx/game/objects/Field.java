package com.mygdx.game.objects;

public class Field {
    public enum STATE{
        EMPTY, ISBLACK, ISWHITE, CHECKED
    }
    private STATE currentState;

    public Field(){
        currentState = STATE.EMPTY;
    }

    public void setCurrentState(STATE arg){
        currentState = arg;
    }
    public STATE getCurrentState(){
        return currentState;
    }

    public void checkField(){
        if(currentState == STATE.EMPTY)
            currentState = STATE.CHECKED;
        else currentState = STATE.EMPTY;
    }
}
