package com.mygdx.game.objects;

public class Field {
    public enum STATE{
        EMPTY, ISBLACK, ISWHITE, CHECK_MOVE, CHECK_HIT
    }
    public enum COLOR{
        BLACK, WHITE
    }
    private STATE currentState;
    private final COLOR color;
    private Piece currentPiece = null;
    private Marker currentMarker = null;

    public Field(COLOR color){
        currentState = STATE.EMPTY;
        this.color = color;
    }

    public void setCurrentState(STATE arg){
        currentState = arg;
    }

    public void setCurrentState(Piece piece){
        currentPiece = piece;
        currentState = (piece.getPieceColor()==Piece.COLOR.WHITE) ? STATE.ISWHITE : STATE.ISBLACK;
    }
    public STATE getCurrentState(){
        return currentState;
    }

    public COLOR getColor(){
        return color;
    }

    public Piece getPiece() throws Exception{
        if(currentPiece==null) throw new Exception("Tried to get piece from empty field");
        return currentPiece;
    }

    public Marker getMarker() throws Exception{
        if(currentMarker==null) throw new Exception("Tried to get marker from empty field");
        return currentMarker;
    }

    public void setMarker(Marker marker){
        currentMarker=marker;
    }

    public void setPiece(Piece piece){

        currentPiece=piece;
        setCurrentState(piece);
    }

    public void removeMarker(){
        currentMarker=null;
        setCurrentState(STATE.EMPTY);
    }

    public void removePiece(){
        currentPiece=null;
        setCurrentState(STATE.EMPTY);
    }

    public boolean isEmpty(){
        if(currentState==STATE.ISBLACK || currentState==STATE.ISWHITE) return false;
        else return true;
    }
}
