package com.mygdx.game.objects.movementmethods;

import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Piece;

import java.awt.*;

public interface MovementBehavior {             //BLACK PIECES ALWAYS AT BOTTOM, STAGE IS ROTATED
    void calculateMovementOptions(Piece piece);
    boolean detectEnemy(Point fieldLocation);
    void movePiece(Piece piece);
    void checkForEndOfTurn(Piece piece);
    void drawIndicator(Chessboard chessboard, Point destinationPoint, Field.STATE state);
    void removeIndicators(Chessboard chessboard);
}
