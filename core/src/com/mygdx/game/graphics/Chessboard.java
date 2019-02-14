package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Piece;
import com.sun.glass.ui.Size;

import java.awt.*;

public class Chessboard {
    private int xDim=8, yDim=8;
    private int sizeInPixels;
    private int segmentSize=0;
    private Field[][] fields = new Field[xDim][yDim];
    private TiledMap createdMap;
    private Stage stage;

    public Size getSizeInPixels(){
        int w=segmentSize*(xDim+2);
        int h=segmentSize*(yDim+2);
        return new Size(w, h);
    }

    public Size getSize(){
        return new Size(xDim, yDim);
    }

    public TiledMap getMap(){
        return createdMap;
    }

    public Chessboard(int sizeInPixels){
        this.sizeInPixels = sizeInPixels;

        /********************   DRAWING CHESSBOARD    *****************/
        int tileSize = (sizeInPixels)/9;
        segmentSize=tileSize;

        Pixmap pixmap = new Pixmap(tileSize*2, tileSize, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.BROWN);
        pixmap.fillRectangle(0,0,tileSize, tileSize);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fillRectangle(0+tileSize,0,tileSize, tileSize);

        Texture t = new Texture(pixmap);
        TextureRegion region_black = new TextureRegion(t,0,0,tileSize, tileSize);
        TextureRegion region_white = new TextureRegion(t, 0+tileSize, 0, tileSize, tileSize);
        TiledMap map = new TiledMap();
        MapLayers layers = map.getLayers();



        TiledMapTileLayer chessboardLayer = new TiledMapTileLayer(sizeInPixels-tileSize/2,sizeInPixels-tileSize/2, tileSize, tileSize);
        for(int i=1; i<=yDim; i++){
            for(int j=1; j<=xDim; j++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile( (j%2 != 0) ? ((i%2 != 0) ? region_black : region_white) : ((i%2 != 0) ? region_white : region_black) ));
                chessboardLayer.setCell(i, j, cell);
                fields[j-1][i-1] = new Field((j%2 != 0) ? ((i%2 != 0) ? Field.COLOR.BLACK : Field.COLOR.WHITE) : ((i%2 != 0) ? Field.COLOR.WHITE : Field.COLOR.BLACK) );
            }
        }
        layers.add(chessboardLayer);



        /********************   UP AND DOWN INDICATORS    *****************/



        Pixmap indicator_horizontal = new Pixmap(tileSize*8, tileSize/2, Pixmap.Format.RGBA8888);
        indicator_horizontal.setColor(Color.GRAY);
        indicator_horizontal.fillRectangle(0,0,tileSize, tileSize/2);
        indicator_horizontal.setColor(Color.BLACK);
        indicator_horizontal.fillRectangle(tileSize,0,tileSize, tileSize/2);

        Texture indicator_h = new Texture(indicator_horizontal);
        TextureRegion indicator_horizontal_white = new TextureRegion(indicator_h, 0,0, tileSize, tileSize/2);
        TextureRegion indicator_horizontal_black = new TextureRegion(indicator_h, tileSize, 0, tileSize, tileSize/2);
        TiledMapTileLayer indicatorsLayer_horizontal = new TiledMapTileLayer(tileSize*8, tileSize/2, tileSize,tileSize/2);
        for(int i=1; i<=yDim;i++) {
            //Label label = new Label("1", new Label.LabelStyle());

            TiledMapTileLayer.Cell cell_down = new TiledMapTileLayer.Cell();
            cell_down.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_black : indicator_horizontal_white ));
            TiledMapTileLayer.Cell cell_up = new TiledMapTileLayer.Cell();
            cell_up.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_white : indicator_horizontal_black ));
            indicatorsLayer_horizontal.setCell(i, (xDim+1)*2, cell_down);
            indicatorsLayer_horizontal.setCell(i,1, cell_up);
        }
        layers.add(indicatorsLayer_horizontal);


        /********************   LEFT AND RIGHT INDICATORS    *****************/

        Pixmap indicator_vertical = new Pixmap(tileSize/2, tileSize*8, Pixmap.Format.RGBA8888);
        indicator_vertical.setColor(Color.GRAY);
        indicator_vertical.fillRectangle(0,0,tileSize/2, tileSize);
        indicator_vertical.setColor(Color.BLACK);
        indicator_vertical.fillRectangle(0,tileSize,tileSize/2, tileSize);

        Texture indicator_v = new Texture(indicator_vertical);
        TextureRegion indicator_vertical_white = new TextureRegion(indicator_v, 0,0, tileSize/2, tileSize);
        TextureRegion indicator_vertical_black = new TextureRegion(indicator_v, 0, tileSize, tileSize/2, tileSize);
        TiledMapTileLayer indicatorsLayer_vertical = new TiledMapTileLayer(tileSize/2, tileSize*8, tileSize/2,tileSize);
        for(int i=1; i<=xDim;i++) {
            TiledMapTileLayer.Cell cell_right = new TiledMapTileLayer.Cell();
            cell_right.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_vertical_black : indicator_vertical_white ));
            TiledMapTileLayer.Cell cell_left = new TiledMapTileLayer.Cell();
            cell_left.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_vertical_white : indicator_vertical_black ));
            indicatorsLayer_vertical.setCell(1, i, cell_left);
            indicatorsLayer_vertical.setCell((yDim+1)*2,i, cell_right);
        }
        layers.add(indicatorsLayer_vertical);

        createdMap = map;
    }

    public int getSegmentSize(){
        return segmentSize;
    }

    private Point getStartingPoint(){
        return new Point(0+segmentSize, 0+segmentSize);
    }

    public Point getPosition(int xAxis, int yAxis) throws IndexOutOfBoundsException {
        ++xAxis; ++yAxis;
        if(xAxis>=0 && xAxis<8 && yAxis>=0 && yAxis<8){
            return new Point(getStartingPoint().x*xAxis, getStartingPoint().y*yAxis);
        }
        else throw new IndexOutOfBoundsException("Field coordinates out of bound: xAxis: "+xAxis+", yAxis: "+yAxis);
    }

    public Field.STATE getFieldState(int xAxis, int yAxis) throws IndexOutOfBoundsException{
        if(xAxis<0 || xAxis>=8 || yAxis<0 || yAxis>=8)
            throw new IndexOutOfBoundsException("Field coordinates out of bound: xAxis: "+xAxis+", yAxis: "+yAxis);
        return fields[xAxis][yAxis].getCurrentState();
    }

    public void setFieldState(int xAxis, int yAxis, Piece piece){
        fields[xAxis][yAxis].setCurrentState((piece.getPieceColor()== Piece.COLOR.BLACK) ? Field.STATE.ISBLACK : Field.STATE.ISWHITE);
    }

    public Chessboard getChessboard(){
        return this;
    }

    public Field getField(int xAxis, int yAxis){
        return fields[xAxis][yAxis];
    }

    public void assignStage(Stage stage){
        this.stage=stage;
    }

    public Stage getStage(){
        return stage;
    }
}












