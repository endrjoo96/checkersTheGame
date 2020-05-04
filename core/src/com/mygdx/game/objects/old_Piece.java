package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.files.FILES;
import com.mygdx.game.objects.movementmethods.old_MovementBehavior;
import com.mygdx.game.objects.movementmethods.Regular;

import java.awt.*;

public class old_Piece extends Actor {
    public enum COLOR {
        WHITE, BLACK
    }

    public enum TYPE {
        REGULAR, MASTER
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Point positionInPixels = chessboard.getPositionInPixels(startingPoint.x, startingPoint.y);
        sprite.setPosition(positionInPixels.x, positionInPixels.y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private boolean clickable=true;
    private boolean isTouched = true;
    private final COLOR color;
    private TYPE currentType;
    private Texture img;
    private Sprite sprite;
    public Point startingPoint;
    private Chessboard chessboard;
    private old_MovementBehavior behavior;

    public old_Piece(COLOR pieceColor, int xPosition, int yPosition, Chessboard chessboard) {
        behavior = new Regular(chessboard);
        this.chessboard = chessboard;
        chessboard.getField(xPosition, yPosition).setPiece(this);   //o kurwa
        color = pieceColor;
        currentType = TYPE.REGULAR;

        Pixmap tempPixmap;
        switch (pieceColor) {
            case BLACK: {
                tempPixmap = new Pixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_BLACK));
                break;
            }
            case WHITE: {
                tempPixmap = new Pixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_WHITE));
                break;
            }
            default: {
                tempPixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
                break;
            }
        }
        Pixmap scaledPixmap = new Pixmap(chessboard.getSegmentSize(), chessboard.getSegmentSize(), tempPixmap.getFormat());
        scaledPixmap.drawPixmap(tempPixmap, 0, 0, tempPixmap.getWidth(), tempPixmap.getHeight(),
                0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight());
        img = new Texture(scaledPixmap);
        tempPixmap.dispose();
        scaledPixmap.dispose();

        sprite = new Sprite(img);

        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.setTouchable(Touchable.enabled);

        startingPoint = new Point(xPosition, yPosition);

        chessboard.setFieldState(xPosition, yPosition, this);
    }

    public void calculateMovementOptions() {
        behavior.removeMarkers(chessboard);
        behavior.removeMarkers(chessboard);
        behavior.removeMarkers(chessboard);
        behavior.calculateMovementOptions(this, false);
    }

    public void setMovement(old_MovementBehavior newBehavior) {
        behavior = newBehavior;
    }

    public COLOR getPieceColor() {
        return color;
    }   // pełna dowolność, pionek może zmienić kolor w trakcie gry XDDDD

    public TYPE getPieceType() {
        return currentType;
    }

    public Point getCurrentLocation() {
        return startingPoint;
    }

    public Point getPosition(){
        return startingPoint;
    }

    public void setPieceType(TYPE type){
        currentType=type;
    }

    public void moveTo(Point position){
        behavior.movePiece(this, position);
    }

    public boolean isClickable(){
        return clickable;
    }

    public void isClickable(boolean value){
        clickable=value;
    }

    public void setTexture(Pixmap pixmap){
        Pixmap scaledPixmap = new Pixmap(chessboard.getSegmentSize(), chessboard.getSegmentSize(), pixmap.getFormat());
        scaledPixmap.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight());
        img = new Texture(scaledPixmap);
    }
}
