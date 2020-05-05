package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.game.graphics.PieceGraphics;

import java.awt.*;

public class WhitePiece extends CheckersPiece {
    public WhitePiece(Point positionOnChessboard) {
        super(positionOnChessboard, PieceGraphics.whiteRegularPixmap, COLOR_VARIANT.WHITE);
    }

    @Override
    protected Pixmap promotionPixmap() {
        return PieceGraphics.whiteQueenPixmap;
    }
}
