package com.mygdx.game.gameplay.mechanics.gamerules;

import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.TilesGrid;

import java.awt.*;

public interface Rules {

    boolean piecePlacementFollowsTheRules(TilesGrid grid, Piece piece);
}
