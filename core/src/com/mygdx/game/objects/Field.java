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

    public void setPiece(Piece piece){
        currentPiece=piece;
    }
}
