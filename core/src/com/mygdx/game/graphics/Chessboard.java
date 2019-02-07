package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public abstract class Chessboard {

    public static TiledMap createChessboard(int sizeInPixels){
        int indicatorThickness = 64;
        int tileSize = (sizeInPixels-(indicatorThickness/8))/8;
        Pixmap pixmap = new Pixmap(tileSize*2, tileSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BROWN);
        pixmap.fillRectangle(32,32,tileSize, tileSize);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fillRectangle(32+tileSize,32,tileSize, tileSize);

        Texture t = new Texture(pixmap);
        TextureRegion region_black = new TextureRegion(t,32,32,tileSize, tileSize);
        TextureRegion region_white = new TextureRegion(t, 32+tileSize, 32, tileSize, tileSize);

        Pixmap indicator_horizontal = new Pixmap(tileSize*8, indicatorThickness/2, Pixmap.Format.RGBA8888);
        indicator_horizontal.setColor(Color.GRAY);
        indicator_horizontal.fillRectangle(0,0,tileSize, indicatorThickness/2);
        indicator_horizontal.setColor(Color.BLACK);
        indicator_horizontal.fillRectangle(tileSize,0,tileSize, indicatorThickness/2);

        Texture indicator_h = new Texture(indicator_horizontal);
        TextureRegion indicator_horizontal_white = new TextureRegion(indicator_h, 0,0, tileSize, indicatorThickness/2);
        TextureRegion indicator_horizontal_black = new TextureRegion(indicator_h, tileSize, 0, tileSize, indicatorThickness/2);

        TiledMap map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer chessboardLayer = new TiledMapTileLayer(sizeInPixels,sizeInPixels, tileSize, tileSize);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile( (j%2 != 0) ? ((i%2 != 0) ? region_black : region_white) : ((i%2 != 0) ? region_white : region_black) ));
                chessboardLayer.setCell(i, j, cell);
            }
        }
        layers.add(chessboardLayer);
        TiledMapTileLayer indicatorsLayer_horizontal = new TiledMapTileLayer(tileSize*8, indicatorThickness/2, tileSize,indicatorThickness/2);
        for(int i=0; i<8;i++) {
            TiledMapTileLayer.Cell cell_down = new TiledMapTileLayer.Cell();
            cell_down.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_black : indicator_horizontal_white ));
            TiledMapTileLayer.Cell cell_up = new TiledMapTileLayer.Cell();
            cell_up.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_white : indicator_horizontal_black ));
            indicatorsLayer_horizontal.setCell(i, -32, cell_down);
            indicatorsLayer_horizontal.setCell(i,sizeInPixels-(indicatorThickness/2), cell_up);
        }
        layers.add(indicatorsLayer_horizontal);
        return map;
    }
}












