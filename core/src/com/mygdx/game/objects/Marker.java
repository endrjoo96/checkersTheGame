package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.files.FILES;
import com.mygdx.game.graphics.Chessboard;

import java.awt.*;

public class Marker extends Actor {
    private Texture img;
    private Sprite sprite;
    private Chessboard chessboard;
    private Point position;
    private Piece markerOwner;
    private Piece pieceToKill;
    private Field.STATE markerType;

    public Marker(int xPos, int yPos, Chessboard chessboard, Field.STATE state, Piece markerOwner, Piece pieceToKill){
        this(xPos, yPos, chessboard, state,markerOwner);
        this.pieceToKill=pieceToKill;
    }

    public Marker(int xPos, int yPos, Chessboard chessboard, Field.STATE state, Piece markerOwner){
        this.chessboard = chessboard;
        this.markerOwner = markerOwner;
        markerType = state;
        Pixmap tempPixmap = new Pixmap(Gdx.files.internal(FILES.ASSETS.MARKER));
        Pixmap scaledPixmap= new Pixmap(chessboard.getSegmentSize(), chessboard.getSegmentSize(), tempPixmap.getFormat());
        scaledPixmap.drawPixmap(tempPixmap, 0, 0, tempPixmap.getWidth(), tempPixmap.getHeight(),
                0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight());
        img = new Texture(scaledPixmap);
        tempPixmap.dispose();
        scaledPixmap.dispose();

        position = new Point(xPos, yPos);

        sprite = new Sprite(img);
        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        markField(xPos, yPos, chessboard, state);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(position.x<chessboard.getSize().width && position.y<chessboard.getSize().height){
            Point positionInPixels = chessboard.getPosition(position.x, position.y);
            sprite.setPosition(positionInPixels.x, positionInPixels.y);
            sprite.draw(batch);
        }
    }

    private void markField(int xPos, int yPos, Chessboard chessboard, Field.STATE state){
        chessboard.getField(xPos,yPos).setCurrentState(state);
        chessboard.getField(xPos, yPos).setMarker(this);
    }

    public Field.STATE markerType(){
        return markerType;
    }

    public Piece getMarkerOwner() {
        return markerOwner;
    }

    public Point getPosition(){
        return position;
    }
    public Piece getPieceToKill(){
        return pieceToKill;
    }
}
