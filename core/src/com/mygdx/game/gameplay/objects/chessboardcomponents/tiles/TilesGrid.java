package com.mygdx.game.gameplay.objects.chessboardcomponents.tiles;

import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.Piece;

import java.awt.*;

public final class TilesGrid {
    private FieldTileImpl[][] tilesGrid;
    private Piece[][] piecesGrid;

    public TilesGrid(Dimension size){
        tilesGrid = new FieldTileImpl[size.width][size.height];
        piecesGrid = new Piece[size.width][size.height];
    }

    public void setTile(Point position, FieldTileImpl tile){
        if(isPositionValid(position)){
            tilesGrid[position.x][position.y] = tile;
        }
    }

    public void setPiece(Piece piece){
        Point position = piece.getPositionOnChessboard();
        if(isPositionValid(position))
            piecesGrid[position.x][position.y] = piece;
    }

    private void removePiece(Piece piece){
        removePiece(piece.getPositionOnChessboard());
    }

    private void removePiece(Point position){
        piecesGrid[position.x][position.y] = null;
    }

    public FieldTileImpl getTileAt(Point position){
        return tilesGrid[position.x][position.y];
    }

    public Piece getPieceAt(Point position){
        return piecesGrid[position.x][position.y];
    }

    private boolean isPositionValid(Point position){
        if((position.x>=0 && position.x< tilesGrid.length)
            && (position.y>=0 && position.y< tilesGrid[0].length)){
            return true;
        }
        return false;
    }

    public void movePiece(Point from, Point destination){
        if(isPositionValid(destination)){
            Piece targetPiece = getPieceAt(from);
            targetPiece.movePiece(destination);
            setPiece(targetPiece);
            removePiece(from);
        }
    }
}
