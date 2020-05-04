package com.mygdx.game.objects.movementmethods;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.objects.old_Field;
import com.mygdx.game.objects.Marker;
import com.mygdx.game.objects.old_Piece;

import java.awt.*;

public class Regular implements old_MovementBehavior {
    private Chessboard chessboard;
    private boolean forcedAttack=false;

    Chessboard getChessboard(){
        return chessboard;
    }

    public Regular(Chessboard chessboard){
        this.chessboard=chessboard;
    }

    @Override
    public boolean calculateMovementOptions(old_Piece oldPiece, boolean calculationModeOnly) {
        Point destinationPoint;
        final int yAxisMove = (oldPiece.getPieceColor()== old_Piece.COLOR.BLACK)?1:-1;     //SETTING UP/DOWN MOVEMENT DEPENDING ON PIECE COLOR
        int extremeRowX = (yAxisMove==-1)?0:chessboard.getSize().height;            //EXTREME ROW
        for(int xAxisMove=-1; xAxisMove<=1; xAxisMove+=2) {                         //LEFT/RIGHT MOVEMENT LOOP
            int extremeColumnY = (xAxisMove==-1)?0:chessboard.getSize().width;      //EXTREME COLUMN

            if (oldPiece.startingPoint.y != extremeRowX && oldPiece.startingPoint.x != extremeColumnY) {                          //CHECKING IF CALCULATING DIAGONAL IS POSSIBLE
                destinationPoint = new Point(oldPiece.startingPoint.x + xAxisMove, oldPiece.startingPoint.y + yAxisMove);     //SETTING DESTINATIONPOINT AS RIGHT DIAGONAL FROM CURRENT PIECE POSITION
                if(!isValid(destinationPoint)) continue;
                if (!detectEnemy(destinationPoint, oldPiece.getPieceColor())) {
                    if(chessboard.getField(destinationPoint.x, destinationPoint.y).isEmpty() && !calculationModeOnly)
                        drawMarker(chessboard, destinationPoint, old_Field.STATE.CHECK_MOVE, oldPiece);    //DRAWING MOVE INDICATOR IF THERE'S NO ENEMY
                } else {
                    if (destinationPoint.y != extremeRowX && destinationPoint.x != extremeColumnY) {  //CHECKING IF ENEMY STANDING ON EXTREME FIELD
                        destinationPoint = new Point(destinationPoint.x + xAxisMove, destinationPoint.y + yAxisMove);       //SETTING DESTINATION POINT TO DIAGONAL FROM ENEMY
                        if(!isValid(destinationPoint)) continue;
                        if (!detectEnemy(destinationPoint, oldPiece.getPieceColor())) {
                            try {
                                if(!calculationModeOnly){
                                    drawMarker(chessboard, destinationPoint, old_Field.STATE.CHECK_HIT, oldPiece, chessboard.getField(destinationPoint.x - xAxisMove, destinationPoint.y - yAxisMove).getPiece());     //DRAWING HIT INDICATOR IF FIELD IS EMPTY

                                }
                                forcedAttack = true;
                            } catch (Exception ex) {}
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
    public boolean detectEnemy(Point fieldLocation, old_Piece.COLOR color) {
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
    public void movePiece(old_Piece oldPiece, Point point) {
        try {
            Marker clickedMarker = chessboard.getField(point.x, point.y).getMarker();
            if(clickedMarker.markerType()== old_Field.STATE.CHECK_MOVE) {
                chessboard.getField(oldPiece.startingPoint.x, oldPiece.startingPoint.y).setCurrentState(old_Field.STATE.EMPTY);
                chessboard.getField(oldPiece.startingPoint.x, oldPiece.startingPoint.y).removePiece();
                oldPiece.startingPoint = point;
                chessboard.getStage().draw();
                removeMarkers(chessboard);
                removeMarkers(chessboard);
                chessboard.getField(point.x, point.y).setPiece(oldPiece);
                chessboard.swapTurn();
                checkForHit();
            }
            else if (clickedMarker.markerType()== old_Field.STATE.CHECK_HIT){
                chessboard.getField(oldPiece.startingPoint.x, oldPiece.startingPoint.y).setCurrentState(old_Field.STATE.EMPTY);
                chessboard.getField(oldPiece.startingPoint.x, oldPiece.startingPoint.y).removePiece();
                oldPiece.startingPoint = point;
                removeMarkers(chessboard);
                removeMarkers(chessboard);
                chessboard.getField(point.x, point.y).setPiece(oldPiece);
                chessboard.getField(clickedMarker.getOldPieceToKill().getCurrentLocation().x,
                                    clickedMarker.getOldPieceToKill().getCurrentLocation().y).setCurrentState(old_Field.STATE.EMPTY);
                chessboard.getField(clickedMarker.getOldPieceToKill().getCurrentLocation().x,
                        clickedMarker.getOldPieceToKill().getCurrentLocation().y).removePiece();
                clickedMarker.getOldPieceToKill().remove();
                /*for(Actor actor : chessboard.getStage().getActors()){
                    if(actor instanceof Piece){
                        if(actor == clickedMarker.getPieceToKill()){
                            actor.remove();
                        }
                    }
                }*/
                chessboard.getStage().draw();
                checkForEndOfTurn(oldPiece);
            }
        } catch (Exception ex){

        }
    }
    @Override
    public void checkForHit(){
        Array<old_Piece> piecesWithHitAvailable = new Array<old_Piece>();
        Array<Actor> actors = chessboard.getStage().getActors();
        for(Actor actor : actors){
            if(actor instanceof old_Piece){
                old_Piece oldPiece = (old_Piece)actor;
                if((oldPiece.getPieceColor()==chessboard.TurnColor())){
                    oldPiece.isClickable(false);
                    if(calculateMovementOptions(oldPiece, true)){
                        piecesWithHitAvailable.add(oldPiece);
                    }
                }
            }
        }
        if(!piecesWithHitAvailable.isEmpty())
        for(old_Piece oldPiece : piecesWithHitAvailable){
            oldPiece.isClickable(true);
        }
        else {
            for(Actor actor : chessboard.getStage().getActors()){
                if(actor instanceof old_Piece){
                    old_Piece oldPiece = (old_Piece)actor;
                    if((oldPiece.getPieceColor()==chessboard.TurnColor())){
                        oldPiece.isClickable(true);
                    }
                }
            }
        }
    }

    @Override
    public void checkForEndOfTurn(old_Piece oldPiece) {
        if(!(calculateMovementOptions(oldPiece, true))) {
            chessboard.swapTurn();
            checkForHit();
        }
        else calculateMovementOptions(oldPiece, false);
    }

    @Override
    public void drawMarker(Chessboard chessboard, Point destinationPoint, old_Field.STATE state, old_Piece oldPiece) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state, oldPiece);
        chessboard.getStage().addActor(marker);
    }

    @Override
    public void drawMarker(Chessboard chessboard, Point destinationPoint, old_Field.STATE state, old_Piece oldPiece, old_Piece oldPieceToKill) {
        Marker marker = new Marker(destinationPoint.x, destinationPoint.y, chessboard, state, oldPiece, oldPieceToKill);
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
                if(((Marker) actor).markerType()== old_Field.STATE.CHECK_MOVE)
                    actor.remove();
            }
        }
    }
}
