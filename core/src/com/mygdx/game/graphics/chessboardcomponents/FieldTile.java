package com.mygdx.game.graphics.chessboardcomponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.graphics.FIELD_COLOR;

import java.awt.*;

public class FieldTile {
    public enum COLOR_TYPE {
        LIGHT, DARK
    }

    protected int size;
    protected COLOR_TYPE colorType;
    protected Color colorForLight = FIELD_COLOR.FIELD_LIGHT,
                    colorForDark  = FIELD_COLOR.FIELD_DARK;
    protected TextureRegion texture;

    public FieldTile(int size, COLOR_TYPE colorType) {
        this.size = size;
        this.colorType = colorType;
    }

    public TextureRegion getTexture(){
        if(texture==null){
            texture = calculateTexture();
        }
        return texture;
    }

    private TextureRegion calculateTexture(){
        Pixmap p = new Pixmap(getSize().width, getSize().height, Pixmap.Format.RGBA8888);
        p.setColor(getColor());
        p.fillRectangle(0, 0, p.getWidth(), p.getHeight());
        TextureRegion region = new TextureRegion(new Texture(p), 0, 0, p.getWidth(), p.getHeight());
        return region;
    }

    public Dimension getSize() {
        return new Dimension(size, size);
    }

    public COLOR_TYPE getColorType() {
        return colorType;
    }

    public Color getColor(){
        Color returnValue;
        switch (colorType){
            case DARK:
                returnValue = colorForDark;
                break;
            case LIGHT:
                returnValue = colorForLight;
                break;
            default:
                returnValue = Color.RED;
        }
        return returnValue;
    }
}
