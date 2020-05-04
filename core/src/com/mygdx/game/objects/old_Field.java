package com.mygdx.game.objects;

public class old_Field {
    public enum STATE{
        EMPTY, ISBLACK, ISWHITE, CHECK_MOVE, CHECK_HIT
    }
    public enum COLOR{
        BLACK, WHITE
    }
    private STATE currentState;
    private final COLOR color;
    private old_Piece currentOldPiece = null;
    private Marker currentMarker = null;

    public old_Field(COLOR color){
        currentState = STATE.EMPTY;
        this.color = color;
    }

    public void setCurrentState(STATE arg){
        currentState = arg;
    }

    public void setCurrentState(old_Piece oldPiece){
        currentOldPiece = oldPiece;
        currentState = (oldPiece.getPieceColor()== old_Piece.COLOR.WHITE) ? STATE.ISWHITE : STATE.ISBLACK;
    }
    public STATE getCurrentState(){
        return currentState;
    }

    public COLOR getColor(){
        return color;
    }

    public old_Piece getPiece() throws Exception{
        if(currentOldPiece ==null) throw new Exception("Tried to get piece from empty field");
        return currentOldPiece;
    }

    public Marker getMarker() throws Exception{
        if(currentMarker==null) throw new Exception("Tried to get marker from empty field");
        return currentMarker;
    }

    public void setMarker(Marker marker){
        currentMarker=marker;
    }

    public void setPiece(old_Piece oldPiece){

        currentOldPiece = oldPiece;
        setCurrentState(oldPiece);
    }

    public void removeMarker(){
        currentMarker=null;
        setCurrentState(STATE.EMPTY);
    }

    public void removePiece(){
        currentOldPiece =null;
        setCurrentState(STATE.EMPTY);
    }

    public boolean isEmpty(){
        if(currentState==STATE.ISBLACK || currentState==STATE.ISWHITE) return false;
        else return true;
    }
}
