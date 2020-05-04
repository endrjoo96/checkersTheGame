package com.mygdx.game.gameplay.mechanics.movement;

import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;

public interface MovementBehavior {
    default void selectPiece(){
        if(canBeClicked())
            calculatePossibleMoves();
    }
    void calculatePossibleMoves();
    boolean canBeClicked();
    default void clickMarker(){
        movePiece();
    }
    void movePiece();

}
