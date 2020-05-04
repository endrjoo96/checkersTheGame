package com.mygdx.game.gameplay.mechanics.gamerules;

import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.TilesGrid;

import java.awt.*;

public interface Rules {
    //boolean isPieceCanBePlacedOnPosition(TilesGrid grid, Point position, Piece piece);
    boolean argumentsMeetsPiecesPlacingRules(TilesGrid grid, Piece piece);
}
