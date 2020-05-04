package com.mygdx.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public interface FieldTile {

    final class FIELD_COLOR {
        public static final Color
                INDICATOR_DARK  = Color.BLACK,
                INDICATOR_LIGHT = Color.GRAY,
                FIELD_DARK      = Color.BROWN,
                FIELD_LIGHT     = Color.LIGHT_GRAY,
                FONT_DARK       = Color.BLACK,
                FONT_LIGHT      = Color.WHITE;
    }
    enum COLOR_TYPE {
        LIGHT, DARK
    }
    TextureRegion getTexture();
    Dimension getSize();
    COLOR_TYPE getColorType();
    Color getColor();
}
