package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.CheckersGame;
import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.files.FILES;
import com.mygdx.game.objects.movementmethods.MovementBehavior;
import com.mygdx.game.objects.movementmethods.Regular;

import java.awt.*;

public class Piece extends Actor {
    public enum COLOR {
        WHITE, BLACK
    }

    public enum TYPE {
        REGULAR, MASTER
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Point positionInPixels = chessboard.getPosition(startingPoint.x, startingPoint.y);
        sprite.setPosition(positionInPixels.x, positionInPixels.y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            input.handle(new Event());
        }
        super.act(delta);
    }

    private boolean isTouched = false;
    private final COLOR color;
    private TYPE currentType;
    private Texture img;
    private Sprite sprite;
    public Point startingPoint;
    private Chessboard chessboard;
    private MovementBehavior behavior;

    public Piece(COLOR pieceColor, int xPosition, int yPosition, Chessboard chessboard) {
        behavior = new Regular(chessboard);
        this.chessboard = chessboard;
        chessboard.getField(xPosition, yPosition).setPiece(this);
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

        input = new InputListener() {

            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (button == Input.Buttons.LEFT) {
                    isTouched = true;
                }
                return false;
            }


            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    isTouched = false;
                }
            }
        };
        addListener(input);//TODO: zrobic te handlery nooooooo
    }

    public void calculateMovementOptions() {
        behavior.removeMarkers(chessboard);
        behavior.calculateMovementOptions(this);
    }

    public void setMovement(MovementBehavior newBehavior) {
        behavior = newBehavior;
    }

    public COLOR getPieceColor() {
        return color;
    }

    public TYPE getPieceType() {
        return currentType;
    }

    public Point getCurrentLocation() {
        return startingPoint;
    }

    public void setPosition(Point newPositionOnChessboard){ startingPoint = newPositionOnChessboard;}

    public void disable(){
        isTouched=false;
    }

    InputListener input;
}
