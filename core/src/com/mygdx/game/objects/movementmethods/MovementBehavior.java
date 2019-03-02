package com.mygdx.game.objects.movementmethods;

import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Piece;

import java.awt.*;

public interface MovementBehavior {             //BLACK PIECES ALWAYS AT BOTTOM, STAGE IS ROTATED
    boolean calculateMovementOptions(Piece piece, boolean calculationModeOnly);
    boolean detectEnemy(Point fieldLocation, Piece.COLOR color);
    void movePiece(Piece piece, Point point);
    void checkForHit();
    void checkForEndOfTurn(Piece piece);
    void drawMarker(Chessboard chessboard, Point destinationPoint, Field.STATE state, Piece piece);
    void drawMarker(Chessboard chessboard, Point destinationPoint, Field.STATE state, Piece piece, Piece pieceToKill);
    void removeMarkers(Chessboard chessboard);
}
