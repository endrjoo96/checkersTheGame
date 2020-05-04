package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.mygdx.game.graphics.PieceGraphics;

import java.awt.*;

public class WhitePiece extends Piece {
    public WhitePiece(Point positionOnChessboard) {
        super(positionOnChessboard, PieceGraphics.whiteRegularPixmap, COLOR_VARIANT.WHITE);
    }
}
