package com.mygdx.game.gameplay.objects.chessboardcomponents.tiles;


import com.mygdx.game.graphics.FieldTile;

import java.awt.*;

public final class IndicatorImpl extends FieldTileImpl implements FieldTile {
    public enum ORIENTATION{
        NOT_DEFINED, HORIZONTAL, VERTICAL
    }

    protected final int secondSizeValue = size/2;
    protected ORIENTATION orientation = ORIENTATION.NOT_DEFINED;

    public IndicatorImpl(int size, COLOR_TYPE type, ORIENTATION orientation) {
        this(size, type);
        this.orientation=orientation;
    }
    public IndicatorImpl(int size, COLOR_TYPE type) {
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
}
