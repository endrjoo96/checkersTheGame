package com.mygdx.game.objects.movementmethods;

import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Piece;

public abstract class Regular {
    public static void calculateMovementOptions(Piece piece){
        if(piece.chessboard.fieldState(piece.startingPoint.x+1, piece.startingPoint.y+1)== Field.STATE.EMPTY){
            //TODO:
        }
        if(piece.chessboard.fieldState(piece.startingPoint.x-1, piece.startingPoint.y+1)== Field.STATE.EMPTY){

        }
        if(piece.chessboard.fieldState(piece.startingPoint.x+1, piece.startingPoint.y-1)== Field.STATE.EMPTY){

        }
        if(piece.chessboard.fieldState(piece.startingPoint.x-1, piece.startingPoint.y-1)== Field.STATE.EMPTY){

        }
    }
}
