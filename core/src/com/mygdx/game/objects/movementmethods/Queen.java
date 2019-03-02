package com.mygdx.game.objects.movementmethods;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Marker;
import com.mygdx.game.objects.Piece;

import java.awt.*;

public class Queen implements MovementBehavior{
    private Chessboard chessboard;
    private boolean forcedAttack=false;

    public Queen(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    @Override
    public boolean calculateMovementOptions(Piece piece, boolean calculationModeOnly) {
        Point destinationPoint;

        for(int yAxisMove=-1; yAxisMove<=1; yAxisMove+=2) {
            int extremeRowX = (yAxisMove==-1)?0:chessboard.getSize().height;
            for (int xAxisMove = -1; xAxisMove <= 1; xAxisMove += 2) {                         //LEFT/RIGHT MOVEMENT LOOP
                int extremeColumnY = (xAxisMove == -1) ? 0 : chessboard.getSize().width;      //EXTREME COLUMN

                if (piece.startingPoint.y != extremeRowX && piece.startingPoint.x != extremeColumnY) {                          //CHECKING IF CALCULATING DIAGONAL IS POSSIBLE
                    destinationPoint = new Point(piece.startingPoint.x + xAxisMove, piece.startingPoint.y + yAxisMove);     //SETTING DESTINATIONPOINT AS RIGHT DIAGONAL FROM CURRENT PIECE POSITION
                    if (!isValid(destinationPoint)) continue;
                    if (!detectEnemy(destinationPoint, piece.getPieceColor())) {
                        if (chessboard.getField(destinationPoint.x, destinationPoint.y).isEmpty() && !calculationModeOnly)
                            drawMarker(chessboard, destinationPoint, Field.STATE.CHECK_MOVE, piece);    //DRAWING MOVE INDICATOR IF THERE'S NO ENEMY
                    } else {
                        if (destinationPoint.y != extremeRowX && destinationPoint.x != extremeColumnY) {  //CHECKING IF ENEMY STANDING ON EXTREME FIELD
                            destinationPoint = new Point(destinationPoint.x + xAxisMove, destinationPoint.y + yAxisMove);       //SETTING DESTINATION POINT TO DIAGONAL FROM ENEMY
                            if (!isValid(destinationPoint)) continue;
                            if (!detectEnemy(destinationPoint, piece.getPieceColor())) {
                                try {
                                    if (!calculationModeOnly) {
                                        drawMarker(chessboard, destinationPoint, Field.STATE.CHECK_HIT, piece, chessboard.getField(destinationPoint.x - xAxisMove, destinationPoint.y - yAxisMove).getPiece());     //DRAWING HIT INDICATOR IF FIELD IS EMPTY

                                    }
                                    forcedAttack = true;
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                }
            }
        }
        if(forcedAttack) {
            if(calculationModeOnly) {
                forcedAttack=false;
                return true;
            }
            removeMoveMarkers(chessboard);
            removeMoveMarkers(chessboard);
            forcedAttack=false;
        }
        return false;
    }
    private boolean isValid(Point point){
        if (point.x < 0 ||
                point.y < 0 ||
                point.x >= chessboard.getSize().width ||
                point.y >= chessboard.getSize().height)
            return false;
        return true;
    }

    @Override
    public boolean detectEnemy(Point fieldLocation, Piece.COLOR color) {
        try {
            if(chessboard.getField(fieldLocation.x, fieldLocation.y).getPiece().getPieceColor() != color)
                return true;
            else
                return false;
        } catch(Exception ex){
            return false;
        }
    }

    @Override
    public void movePiece(Piece piece, Point point) {
        try {
            Marker clickedMarker = chessboard.getField(point.x, point.y).getMarker();
            if(clickedMarker.markerType()==Field.STATE.CHECK_MOVE) {
                chessboard.getField(piece.startingPoint.x, piece.startingPoint.y).removePiece();
                piece.startingPoint = point;
                chessboard.getStage().draw();
                removeMarkers(chessboard);
                removeMarkers(chessboard);
                chessboard.getField(point.x, point.y).setPiece(piece);
                chessboard.swapTurn();
                checkForHit();
            }
            else if (clickedMarker.markerType()==Field.STATE.CHECK_HIT){
                chessboard.getField(piece.startingPoint.x, piece.startingPoint.y).removePiece();
                piece.startingPoint = point;
                removeMarkers(chessboard);
                removeMarkers(chessboard);
                chessboard.getField(point.x, point.y).setPiece(piece);
                chessboard.getField(clickedMarker.getPieceToKill().getCurrentLocation().x,
                        clickedMarker.getPieceToKill().getCurrentLocation().y).setCurrentState(Field.STATE.EMPTY);
                chessboard.getField(clickedMarker.getPieceToKill().getCurrentLocation().x,
                        clickedMarker.getPieceToKill().getCurrentLocation().y).removePiece();

                clickedMarker.getPieceToKill().remove();
                chessboard.getStage().draw();
                checkForEndOfTurn(piece);
            }
        } catch (Exception ex){

        }
    }

    @Override
    public void checkForHit() {
        Array<Piece> piecesWithHitAvailable = new Array<Piece>();
        Array<Actor> actors = chessboard.getStage().getActors();
        for(Actor actor : actors){
            if(actor instanceof Piece){
                Piece piece = (Piece)actor;
                if((piece.getPieceColor()==chessboard.TurnColor())){
                    piece.isClickable(false);
                    if(calculateMovementOptions(piece, true)){
                        piecesWithHitAvailable.add(piece);
                    }
                }
            }
        }
        if(!piecesWithHitAvailable.isEmpty())
            for(Piece piece : piecesWithHitAvailable){
                piece.isClickable(true);
            }
        else {
            for(Actor actor : chessboard.getStage().getActors()){
                if(actor instanceof Piece){
                    Piece piece = (Piece)actor;
                    if((piece.getPieceColor()==chessboard.TurnColor())){
                        piece.isClickable(true);
                    }
                }
            }
        }
    }

    @Override
    public void checkForEndOfTurn(Piece piece) {
        if(!(calculateMovementOptions(piece, true))) {
            chessboard.swapTurn();
            checkForHit();
        }
        else calculateMovementOptions(piece, false);
    }

    @Override
    public void drawMarker(Chessboard chessboard, Point destinationPoint, Field.STATE state, Piece piece) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state, piece);
        chessboard.getStage().addActor(marker);
    }

    @Override
    public void drawMarker(Chessboard chessboard, Point destinationPoint, Field.STATE state, Piece piece, Piece pieceToKill) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state, piece, pieceToKill);
        chessboard.getStage().addActor(marker);
    }

    @Override
    public void removeMarkers(Chessboard chessboard) {
        for(Actor actor : chessboard.getStage().getActors()){
            if(actor instanceof Marker)  {
                Point point = ((Marker)actor).getPosition();
                chessboard.getField(point.x, point.y).removeMarker();
                actor.remove();
            }
        }
    }

    private void removeMoveMarkers(Chessboard chessboard){
        Array<Actor> actorsCollection = chessboard.getStage().getActors();
        for(Actor actor : actorsCollection){
            if(actor instanceof Marker){
                if(((Marker) actor).markerType()==Field.STATE.CHECK_MOVE)
                    actor.remove();
            }
        }
    }
}
