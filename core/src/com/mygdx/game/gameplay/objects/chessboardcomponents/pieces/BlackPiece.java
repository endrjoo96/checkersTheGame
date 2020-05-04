package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.game.graphics.PieceGraphics;

import java.awt.*;

public class BlackPiece extends Piece {
    public BlackPiece(Point positionOnChessboard) {
        super(positionOnChessboard, PieceGraphics.blackRegularPixmap, COLOR_VARIANT.BLACK);
    }
}
