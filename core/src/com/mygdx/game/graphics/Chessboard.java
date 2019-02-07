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
        int tileSize = sizeInPixels/8;
        Pixmap pixmap = new Pixmap(tileSize*2, tileSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BROWN);
        pixmap.fillRectangle(0,0,tileSize, tileSize);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fillRectangle(90,0,tileSize, tileSize);

        Texture t = new Texture(pixmap);
        TextureRegion reg1 = new TextureRegion(t,0,0,tileSize, tileSize);
        TextureRegion reg2 = new TextureRegion(t, tileSize, 0, tileSize, tileSize);

        TiledMap map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(sizeInPixels,sizeInPixels, tileSize, tileSize);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile( (j%2 != 0) ? ((i%2 != 0) ? reg1 : reg2) : ((i%2 != 0) ? reg2 : reg1) ));
                layer.setCell(i, j, cell);
            }
        }
        layers.add(layer);
        return map;
    }
}
