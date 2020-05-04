package com.mygdx.game.gameplay.mechanics.movement;

import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;

public class RegularMovement implements MovementBehavior {
    Piece piece;

    public RegularMovement(Piece piece){
        this.piece=piece;
    }

    @Override
    public void calculatePossibleMoves() {

    }

    @Override
    public boolean canBeClicked() {
        return false;
    }

    @Override
    public void clickMarker() {

    }

    @Override
    public void movePiece() {

    }
}
