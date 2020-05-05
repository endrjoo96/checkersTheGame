package com.mygdx.game.gameplay.mechanics.gamerules;

import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.TilesGrid;
import com.mygdx.game.graphics.FieldTile;

import java.awt.*;

public class CheckersStandardRules implements Rules {
    private Chessboard chessboard = Chessboard.getInstance();

    private TilesGrid grid;
    private Point position;
    private Piece piece;

    @Override
    public boolean piecePlacementFollowsTheRules(TilesGrid grid, Piece piece) {
        this.grid=grid;
        this.position=piece.getPositionOnChessboard();
        this.piece=piece;

        if( pieceMustBePlacedOnBlackTiles() && (
            blackPiecesMustFillAllBottomHalfExceptMiddleRow() ||
            whitePiecesMustFillAllTopHalfExceptMiddleRow())){
            return true;
        }
        return false;
    }

    private boolean pieceMustBePlacedOnBlackTiles() {
        if(grid.getTileAt(position).getColorType() == FieldTile.COLOR_TYPE.DARK)
            return true;
        return false;
    }

    private boolean blackPiecesMustFillAllBottomHalfExceptMiddleRow() {
        if(piece.getVariant() == Piece.COLOR_VARIANT.WHITE)
            return false;

        int chessboardHeight = chessboard.getSize().height;
        if (chessboardHeight % 2 != 0)
            chessboardHeight--;
        int maxRow = (chessboardHeight/2) - 1;

        if(position.y>=0 && position.y < maxRow){
            return true;
        }
        return false;
    }

    private boolean whitePiecesMustFillAllTopHalfExceptMiddleRow() {
        if(piece.getVariant() == Piece.COLOR_VARIANT.BLACK)
            return false;

        int chessboardHeight = chessboard.getSize().height;
        if (chessboardHeight % 2 != 0)
            chessboardHeight--;
        int maxRow = chessboard.getSize().height - ((chessboardHeight/2) - 1);

        if(position.y>=maxRow && position.y < chessboard.getSize().height){
            return true;
        }
        return false;
    }
}
