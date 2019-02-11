package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.graphics.Chessboard;
import com.mygdx.game.graphics.DIR;

import java.awt.*;

public class Piece extends Actor{
    enum COLOR {
        WHITE, BLACK
    }

    enum TYPE{
        REGULAR, MASTER
    }

    COLOR color;
    Texture img;
    Sprite sprite;
    private Point startingPoint;
    public Piece(COLOR pieceColor, int xPosition, int yPosition){

        color = pieceColor;


        Pixmap tempPixmap;
        switch (pieceColor){
            case BLACK: {
                tempPixmap = new Pixmap(Gdx.files.internal(DIR.FILE.CHECKER_BLACK));
                break;
            }
            case WHITE:{
                tempPixmap = new Pixmap(Gdx.files.internal(DIR.FILE.CHECKER_WHITE));
                break;
            }
            default: {
                tempPixmap=new Pixmap(1,1, Pixmap.Format.RGB888);
                break;
            }
        }
        Pixmap scaledPixmap= new Pixmap(Chessboard.getSegmentSize(), Chessboard.getSegmentSize(), tempPixmap.getFormat());
        scaledPixmap.drawPixmap(tempPixmap, 0, 0, tempPixmap.getWidth(), tempPixmap.getHeight(),
                                            0, 0, scaledPixmap.getWidth(), scaledPixmap.getHeight());
        img = new Texture(scaledPixmap);
        tempPixmap.dispose();
        scaledPixmap.dispose();

        sprite = new Sprite(img);

        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.setTouchable(Touchable.enabled);

        startingPoint = new Point(Chessboard.getPosition(xPosition,yPosition));

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(button== Input.Buttons.LEFT){
                    return true;
                }
                return true;
            }
        });

    }


}
