package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.game.graphics.PieceGraphics;

import java.awt.*;

public class BlackPiece extends CheckersPiece {
    public BlackPiece(Point positionOnChessboard) {
        super(positionOnChessboard, PieceGraphics.blackRegularPixmap, COLOR_VARIANT.BLACK);
    }

    @Override
    public Pixmap promotionPixmap() {
        return PieceGraphics.blackQueenPixmap;
    }
}
