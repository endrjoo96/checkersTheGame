package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.game.gameplay.mechanics.movement.QueenMovement;

import java.awt.*;

public abstract class CheckersPiece extends Piece {
    public CheckersPiece(Point positionOnChessboard, Pixmap piecePixmap, COLOR_VARIANT variant) {
        super(positionOnChessboard, piecePixmap, variant);
    }

    public void promotePiece(){
        changePixmap(promotionPixmap());
        setMovementBehavior(QueenMovement.class);
    }

    protected abstract Pixmap promotionPixmap();
}
