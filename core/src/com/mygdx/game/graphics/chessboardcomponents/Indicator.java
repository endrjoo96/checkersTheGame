package com.mygdx.game.graphics.chessboardcomponents;

import com.mygdx.game.graphics.FIELD_COLOR;

import java.awt.*;

public final class Indicator extends FieldTile {
    public enum ORIENTATION{
        NOT_DEFINED, HORIZONTAL, VERTICAL
    }

    protected final int secondSizeValue = size/2;
    protected ORIENTATION orientation = ORIENTATION.NOT_DEFINED;

    public Indicator(int size, COLOR_TYPE type, ORIENTATION orientation) {
        this(size, type);
        this.orientation=orientation;
    }
    public Indicator(int size, COLOR_TYPE type) {
        super(size, type);
        colorForLight = FIELD_COLOR.INDICATOR_LIGHT;
        colorForDark = FIELD_COLOR.INDICATOR_DARK;
    }

    public Dimension getSize(ORIENTATION orientation){
        this.orientation = orientation;
        return getSize();
    }
    @Override
    public Dimension getSize() {
        Dimension returnValue = super.getSize();
        switch (orientation){
            case VERTICAL:
                returnValue.width = secondSizeValue;
                break;
            case HORIZONTAL:
                returnValue.height = secondSizeValue;
                break;
        }
        return returnValue;
    }

    /*@Override
    public TextureRegion getTexture() {
        super.getTexture();
        texture.setRegion(0,0, getSize().width, getSize().height);
        return texture;
    }*/
}
