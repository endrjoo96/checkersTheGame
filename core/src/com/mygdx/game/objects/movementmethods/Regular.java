package com.mygdx.game.objects.movementmethods;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Marker;
import com.mygdx.game.objects.Piece;

import java.awt.*;

public class Regular implements MovementBehavior {
    private Chessboard chessboard;

    public Regular(Chessboard chessboard){
        this.chessboard=chessboard;
    }

    @Override
    public void calculateMovementOptions(Piece piece) {
        Point destinationPoint;
        if(piece.getPieceColor() == Piece.COLOR.BLACK) {
            if(piece.startingPoint.y != chessboard.getSize().width) {
                destinationPoint = new Point(piece.startingPoint.x + 1, piece.startingPoint.y + 1);
                if(!detectEnemy(destinationPoint)){
                    drawIndicator(chessboard, destinationPoint, Field.STATE.CHECK_MOVE);
                } else {
                    if(destinationPoint.y!=chessboard.getSize().width && destinationPoint.x!=chessboard.getSize().height){
                        destinationPoint = new Point(destinationPoint.x+1, destinationPoint.y+1);
                        if(!detectEnemy(destinationPoint)){
                            drawIndicator(chessboard, destinationPoint, Field.STATE.CHECK_HIT);
                        }
                    }
                }
            }
            if(piece.startingPoint.y != 0){
                destinationPoint = new Point(piece.startingPoint.x + 1, piece.startingPoint.y - 1);
                if(!detectEnemy(destinationPoint)){
                    drawIndicator(chessboard, destinationPoint, Field.STATE.CHECK_MOVE);
                } else {
                    if(destinationPoint.y!=chessboard.getSize().width && destinationPoint.x!=chessboard.getSize().height){
                        destinationPoint = new Point(destinationPoint.x+1, destinationPoint.y-1);
                        if(!detectEnemy(destinationPoint)){
                            drawIndicator(chessboard, destinationPoint, Field.STATE.CHECK_HIT);
                        }
                    }
                }
            }
        } else {

        }
    }

    @Override
    public boolean detectEnemy(Point fieldLocation) {
        if (chessboard.getFieldState(fieldLocation.x, fieldLocation.y) != Field.STATE.EMPTY)
            return false;
        return true;
    }

    @Override
    public void movePiece(Piece piece) {

    }

    @Override
    public void checkForEndOfTurn(Piece piece) {

    }

    @Override
    public void drawIndicator(Chessboard chessboard, Point destinationPoint, Field.STATE state) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state);
        chessboard.getStage().addActor(marker);
    }

    @Override
    public void removeIndicators(Chessboard chessboard) {
        for(Actor actor : chessboard.getStage().getActors()){
            if(actor instanceof Marker){
                actor.remove();
            }
        }
    }
}
