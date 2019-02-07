package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Piece extends Actor{
    enum COLOR {
        WHITE, BLACK
    }

    COLOR color;
    Texture img;

    public Piece(COLOR pieceColor){
        color = pieceColor;
    }


}
