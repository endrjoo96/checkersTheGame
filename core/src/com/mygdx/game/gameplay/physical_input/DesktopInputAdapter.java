package com.mygdx.game.gameplay.physical_input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;

import java.awt.*;


public final class DesktopInputAdapter extends InputAdapter {
    /*@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //TODO obsługa kliknięcia

        System.out.println(String.format("Kliknieto w koordynaty: (x=%s; y=%s)", screenX, screenY));
        System.out.println(String.format("Obraz ma %spx szerokosci i %spx wysokosci",
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        if(clickedOnActor(new Point(screenX, screenY))){
            Piece p = getSelectedPiece();
            System.out.print("Kliknieto na pionka w kolorze ");
            if(p.getVariant()== Piece.COLOR_VARIANT.WHITE)
                System.out.println("bialym!");
            else System.out.println("czarnym!");
        } else System.out.println("Nie znaleziono pionka");

        return super.touchDown(screenX, screenY, pointer, button);
    }*/

    private Piece cachedPiece;

    private Piece getSelectedPiece() {
        return cachedPiece;
    }

    private boolean clickedOnActor(Point position) {
        Chessboard chessboard = Chessboard.getInstance();
        for (Piece p : chessboard.getGrid().getPiecesAsArray()) {
            Scope horizontalScope = new Scope(p.getX(), p.getX() + p.getWidth());
            Scope verticalScope = new Scope(p.getY(), p.getY() + p.getHeight());
            if(positionIsWithinTheScope(position, horizontalScope, verticalScope)){
                cachedPiece = p;
                return true;
            }
        }
        return false;
    }

    private boolean positionIsWithinTheScope(Point position, Scope horizontalScope, Scope verticalScope) {
        if(position.x < verticalScope.startingPoint) return false;
        if(position.y < horizontalScope.startingPoint) return false;
        if(position.x > verticalScope.endingPoint) return false;
        if(position.y > horizontalScope.endingPoint) return false;
        return true;
    }
}

class Scope{
    float startingPoint;
    float endingPoint;

    public Scope(float startingPoint, float endingPoint){
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }
}
