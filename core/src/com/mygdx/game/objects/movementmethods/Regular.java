package com.mygdx.game.objects.movementmethods;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Marker;
import com.mygdx.game.objects.Piece;

import java.awt.*;

public class Regular implements MovementBehavior {
    private Chessboard chessboard;
    private boolean forcedAttack=false;

    public Regular(Chessboard chessboard){
        this.chessboard=chessboard;
    }

    @Override
    public void calculateMovementOptions(Piece piece) {
        Point destinationPoint;
        final int yAxisMove = (piece.getPieceColor()== Piece.COLOR.BLACK)?1:-1;     //SETTING UP/DOWN MOVEMENT DEPENDING ON PIECE COLOR
        int extremeRowX = (yAxisMove==-1)?0:chessboard.getSize().height;            //EXTREME ROW
        for(int xAxisMove=-1; xAxisMove<=1; xAxisMove+=2) {                         //LEFT/RIGHT MOVEMENT LOOP
            int extremeColumnY = (xAxisMove==-1)?0:chessboard.getSize().width;      //EXTREME COLUMN

            if (piece.startingPoint.y != extremeRowX && piece.startingPoint.x != extremeColumnY) {                          //CHECKING IF CALCULATING DIAGONAL IS POSSIBLE
                destinationPoint = new Point(piece.startingPoint.x + xAxisMove, piece.startingPoint.y + yAxisMove);     //SETTING DESTINATIONPOINT AS RIGHT DIAGONAL FROM CURRENT PIECE POSITION
                if (!detectEnemy(destinationPoint, piece.getPieceColor())) {
                    drawMarker(chessboard, destinationPoint, Field.STATE.CHECK_MOVE, piece);    //DRAWING MOVE INDICATOR IF THERE'S NO ENEMY
                } else {
                    if (destinationPoint.y != extremeRowX && destinationPoint.x != extremeColumnY) {  //CHECKING IF ENEMY STANDING ON MOST RIGHT FIELD
                        destinationPoint = new Point(destinationPoint.x + 1, destinationPoint.y + 1);       //SETTING DESTINATION POINT TO RIGHT DIAGONAL FROM ENEMY
                        if (!detectEnemy(destinationPoint, piece.getPieceColor())) {
                            drawMarker(chessboard, destinationPoint, Field.STATE.CHECK_HIT, piece);     //DRAWING HIT INDICATOR IF FIELD IS EMPTY
                            forcedAttack=true;
                        }
                    }
                }
            }
            //TODO: REMOVE ANY MOVE INDICATORS IF ANY OF HIT INDICATOR HAS BEEN DRAWN.
            if(forcedAttack) removeMoveMarkers(chessboard);
        }

    }

    @Override
    public boolean detectEnemy(Point fieldLocation, Piece.COLOR color) {
        try {
            return (chessboard.getField(fieldLocation.x, fieldLocation.y).getPiece().getPieceColor() != color)?
                    true:
                    false;
        } catch(Exception ex){
            return false;
        }
    }

    @Override
    public void movePiece(Piece piece) {

    }

    @Override
    public void checkForEndOfTurn(Piece piece) {

    }

    @Override
    public void drawMarker(Chessboard chessboard, Point destinationPoint, Field.STATE state, Piece piece) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state, piece);
        chessboard.getStage().addActor(marker);
    }

    @Override
    public void removeMarkers(Chessboard chessboard) {
        for(Actor actor : chessboard.getStage().getActors()){
            if(actor instanceof Marker){
                actor.remove();
            }
        }
    }

    public void removeMoveMarkers(Chessboard chessboard){
        Array<Actor> actorsCollection = chessboard.getStage().getActors();
        for(Actor actor : actorsCollection){
            if(actor instanceof Marker){
                if(((Marker) actor).markerType()==Field.STATE.CHECK_MOVE)
                    actor.remove();
            }
        }
    }
}
