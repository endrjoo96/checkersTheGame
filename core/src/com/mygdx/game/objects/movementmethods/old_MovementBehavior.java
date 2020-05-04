package com.mygdx.game.objects.movementmethods;

import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.objects.old_Field;
import com.mygdx.game.objects.old_Piece;

import java.awt.*;

public interface old_MovementBehavior {             //BLACK PIECES ALWAYS AT BOTTOM, STAGE IS ROTATED
    boolean calculateMovementOptions(old_Piece oldPiece, boolean calculationModeOnly);
    boolean detectEnemy(Point fieldLocation, old_Piece.COLOR color);
    void movePiece(old_Piece oldPiece, Point point);
    void checkForHit();
    void checkForEndOfTurn(old_Piece oldPiece);
    void drawMarker(Chessboard chessboard, Point destinationPoint, old_Field.STATE state, old_Piece oldPiece);
    void drawMarker(Chessboard chessboard, Point destinationPoint, old_Field.STATE state, old_Piece oldPiece, old_Piece oldPieceToKill);
    void removeMarkers(Chessboard chessboard);
}
