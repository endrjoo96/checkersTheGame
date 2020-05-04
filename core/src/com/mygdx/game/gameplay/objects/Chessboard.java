package com.mygdx.game.gameplay.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.CheckersGame;
import com.mygdx.game.files.FILES;
import com.mygdx.game.gameplay.GameplayDriver;
import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.BlackPiece;
import com.mygdx.game.gameplay.objects.chessboardcomponents.pieces.WhitePiece;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.FieldTileImpl;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.IndicatorImpl;
import com.mygdx.game.gameplay.objects.chessboardcomponents.tiles.TilesGrid;
import com.mygdx.game.graphics.FieldTile;
import com.mygdx.game.graphics.GameScreen;
import com.mygdx.game.objects.old_Field;
import com.mygdx.game.objects.Marker;
import com.mygdx.game.objects.old_Piece;
import com.mygdx.game.objects.movementmethods.Queen;

import java.awt.*;

public class Chessboard extends InputAdapter {
    private int xDim=8, yDim=12;
    private int segmentSize=0;
    private old_Field[][] oldFields = new old_Field[xDim][yDim];
    private TiledMap tiledMap;
    private MapLayers layers;
    private Stage stage;
    boolean whiteMove=true;

    private TilesGrid tilesGrid;

    private static Chessboard instance = null;

    public static Chessboard getInstance(){
        if(instance==null)
            instance = new Chessboard();
        return instance;
    }

    public static Chessboard resetInstance(){
        instance=null;
        return getInstance();
    }

    private Chessboard(){
        tilesGrid = new TilesGrid(new Dimension(xDim, yDim));
        draw();


        /********************   UP AND DOWN INDICATORS    *****************/



        /*Pixmap indicator_horizontal = new Pixmap(segmentSize*8, segmentSize/2, Pixmap.Format.RGBA8888);
        indicator_horizontal.setColor(Color.GRAY);
        indicator_horizontal.fillRectangle(0,0,segmentSize, segmentSize/2);
        indicator_horizontal.setColor(Color.BLACK);
        indicator_horizontal.fillRectangle(segmentSize,0,segmentSize, segmentSize/2);

        Texture indicator_h = new Texture(indicator_horizontal);
        TextureRegion indicator_horizontal_white = new TextureRegion(indicator_h, 0,0, segmentSize, segmentSize/2);
        TextureRegion indicator_horizontal_black = new TextureRegion(indicator_h, segmentSize, 0, segmentSize, segmentSize/2);
        TiledMapTileLayer indicatorsLayer_horizontal = new TiledMapTileLayer(segmentSize*8, segmentSize/2, segmentSize,segmentSize/2);
        for(int i=1; i<=xDim;i++) {
            //Label label = new Label("1", new Label.LabelStyle());

            TiledMapTileLayer.Cell cell_down = new TiledMapTileLayer.Cell();
            cell_down.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_black : indicator_horizontal_white ));
            TiledMapTileLayer.Cell cell_up = new TiledMapTileLayer.Cell();
            cell_up.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_horizontal_white : indicator_horizontal_black ));
            indicatorsLayer_horizontal.setCell(i, (yDim+1)*2, cell_down);
            indicatorsLayer_horizontal.setCell(i,1, cell_up);
        }
        layers.add(indicatorsLayer_horizontal);



        /********************   LEFT AND RIGHT INDICATORS    *****************/



        /*Pixmap indicator_vertical = new Pixmap(segmentSize/2, segmentSize*8, Pixmap.Format.RGBA8888);
        indicator_vertical.setColor(Color.GRAY);
        indicator_vertical.fillRectangle(0,0,segmentSize/2, segmentSize);
        indicator_vertical.setColor(Color.BLACK);
        indicator_vertical.fillRectangle(0,segmentSize,segmentSize/2, segmentSize);

        Texture indicator_v = new Texture(indicator_vertical);
        TextureRegion indicator_vertical_white = new TextureRegion(indicator_v, 0,0, segmentSize/2, segmentSize);
        TextureRegion indicator_vertical_black = new TextureRegion(indicator_v, 0, segmentSize, segmentSize/2, segmentSize);
        TiledMapTileLayer indicatorsLayer_vertical = new TiledMapTileLayer(segmentSize/2, segmentSize*8, segmentSize/2,segmentSize);
        for(int i=1; i<=yDim;i++) {
            TiledMapTileLayer.Cell cell_right = new TiledMapTileLayer.Cell();
            cell_right.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_vertical_black : indicator_vertical_white ));
            TiledMapTileLayer.Cell cell_left = new TiledMapTileLayer.Cell();
            cell_left.setTile(new StaticTiledMapTile( (i%2!=0) ? indicator_vertical_white : indicator_vertical_black ));
            indicatorsLayer_vertical.setCell(1, i, cell_left);
            indicatorsLayer_vertical.setCell((xDim+1)*2,i, cell_right);
        }
        layers.add(indicatorsLayer_vertical);*/


    }

    private void draw() {
        segmentSize = calculateMappedSize(
                new int[] {CheckersGame.screenHeight, CheckersGame.screenWidth},
                new int[] {xDim+2, yDim+2}  // +2 -> margin spaces for indicators
        );

        tiledMap = new TiledMap();
        layers = tiledMap.getLayers();

        createChessboard();
    }

    private void createChessboard(){
        FieldTileImpl lightFieldTile =
                new FieldTileImpl(segmentSize, FieldTileImpl.COLOR_TYPE.LIGHT);
        FieldTileImpl darkFieldTile =
                new FieldTileImpl(segmentSize, FieldTileImpl.COLOR_TYPE.DARK);

        TiledMapTileLayer chessboardLayer = new TiledMapTileLayer(CheckersGame.screenWidth,CheckersGame.screenHeight, segmentSize, segmentSize);
        for(int i=1; i<=xDim; i++){
            for(int j=1; j<=yDim; j++){     // top left corner starts with light tile
                TextureRegion currentRegion;
                if( (i-j)%2 != 0 ){
                    currentRegion = getTextureRegionAndSetTileToGrid(i-1, j-1, lightFieldTile);
                }
                else{
                    currentRegion = getTextureRegionAndSetTileToGrid(i-1, j-1, darkFieldTile);
                }
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(currentRegion));
                chessboardLayer.setCell(i, j, cell);
            }
        }
        layers.add(chessboardLayer);
    }

    private TextureRegion getTextureRegionAndSetTileToGrid
            (int xPosition, int yPosition, FieldTileImpl tile){
        tilesGrid.setTile(new Point(xPosition, yPosition), tile);
        return tile.getTexture();
    }

    private void createIndicators(){
        FieldTileImpl lightIndicator =
                new IndicatorImpl(segmentSize, FieldTileImpl.COLOR_TYPE.LIGHT);
        FieldTileImpl darkIndicator =
                new IndicatorImpl(segmentSize, FieldTileImpl.COLOR_TYPE.DARK);






        //TODO






    }

    private int calculateMappedSize(int[] screenDims, int[] numberOfFragments ){
        return calculateMappedSize(
                Math.max(screenDims[0], screenDims[1]),
                Math.max(numberOfFragments[0], numberOfFragments[1]));
    }

    private int calculateMappedSize(int originalValue, int numberOfFragments){
        return (originalValue)/numberOfFragments;
    }

    public void placePieces(){
        for(int y=0; y<yDim; y++){
            for(int x=0; x<xDim; x++){
                WhitePiece whitePiece = new WhitePiece(new Point(x,y));
                BlackPiece blackPiece = new BlackPiece(new Point(x,y));
                if(GameplayDriver.getGameRules().argumentsMeetsPiecesPlacingRules(
                        tilesGrid, whitePiece)){
                    tilesGrid.setPiece(whitePiece);
                }
                else if(GameplayDriver.getGameRules().argumentsMeetsPiecesPlacingRules(
                        tilesGrid, blackPiece)){
                    tilesGrid.setPiece(blackPiece);
                }
            }
        }
    }

    public TilesGrid getGrid(){
        return tilesGrid;
    }

    public int getSegmentSize(){
        return segmentSize;
    }

    private Point getStartingPoint(){
        return new Point(0+segmentSize, 0+segmentSize);
    }

    public Point getPositionInPixels(Point positionInTilesLength){
        int     xAxis = positionInTilesLength.x + 1,
                yAxis = positionInTilesLength.y + 1;
        // incrementing x and y axis, because 0 and MAX values are
        // used for indicators
        if(xAxis>0 && xAxis<=xDim && yAxis>0 && yAxis<=yDim){
            return new Point(getStartingPoint().x*xAxis, getStartingPoint().y*yAxis);
        }
        else throw new IndexOutOfBoundsException("Field coordinates out of bound: xAxis: "+xAxis+", yAxis: "+yAxis);
    }

    public Point getPositionInPixels(int xAxis, int yAxis) {
        return getPositionInPixels(new Point(xAxis, yAxis));
    }

    public old_Field.STATE getFieldState(int xAxis, int yAxis){
        if(xAxis<0 || xAxis>=8 || yAxis<0 || yAxis>=8)
            throw new IndexOutOfBoundsException("Field coordinates out of bound: xAxis: "+xAxis+", yAxis: "+yAxis);
        return oldFields[xAxis][yAxis].getCurrentState();
    }

    public void setFieldState(int xAxis, int yAxis, old_Piece oldPiece){
        oldFields[xAxis][yAxis].setCurrentState((oldPiece.getPieceColor()== old_Piece.COLOR.BLACK) ? old_Field.STATE.ISBLACK : old_Field.STATE.ISWHITE);
    }

    public old_Field getField(int xAxis, int yAxis) throws ArrayIndexOutOfBoundsException{
        if(xAxis<0 ||xAxis>=xDim || yAxis<0 || yAxis>=yDim)
            throw new ArrayIndexOutOfBoundsException("No such field: ("+xAxis+", "+yAxis+")");
        return oldFields[xAxis][yAxis];
    }

    public void assignStage(Stage stage){
        this.stage=stage;
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean isPiece=true;
        screenY = Gdx.graphics.getHeight()-screenY;
        try {
            old_Piece clickedOldPiece = getPieceFromCoordinates(new Point(screenX, screenY));
            if(clickedOldPiece.getPieceColor()==TurnColor()&& clickedOldPiece.isClickable())
                clickedOldPiece.calculateMovementOptions();
        }catch (Exception ex) {
            isPiece=false;
        }
        if(!isPiece){
            try {
                Marker clickedMarker = getMarkerFromCoordinates(new Point(screenX, screenY));
                clickedMarker.getMarkerOwner().moveTo(calculateClickedTile(new Point(screenX, screenY)));
                if((clickedMarker.getMarkerOwner().getPosition().y == yDim-1 || clickedMarker.getMarkerOwner().getPosition().y==0)
                        && clickedMarker.getMarkerOwner().getPieceType() == old_Piece.TYPE.REGULAR){
                clickedMarker.getMarkerOwner().setMovement(new Queen(instance));
                clickedMarker.getMarkerOwner().setPieceType(old_Piece.TYPE.MASTER);
                clickedMarker.getMarkerOwner().setTexture(new Pixmap(Gdx.files.internal((clickedMarker.getMarkerOwner().getPieceColor()== old_Piece.COLOR.WHITE)?FILES.ASSETS.CHECKER_WHITE_MASTER : FILES.ASSETS.CHECKER_BLACK_MASTER)));
                }
            } catch(Exception ex){
                isPiece=false;
            }
        }
        return super.touchDown(screenX, screenY, pointer, button);

    }

    private Point calculateClickedTile(Point click){
        int viewport_width= GameScreen.getViewport().getScreenX();
        int viewport_height=GameScreen.getViewport().getScreenY();
        Point startingPoint = getStartingPoint();
        int xAxis = (click.x-startingPoint.x-viewport_width)/getSegmentSize();
        int yAxis = (click.y-startingPoint.y-viewport_height)/getSegmentSize();
        return new Point(xAxis, yAxis);
    }

    private Marker getMarkerFromCoordinates(Point click) throws Exception{
        int xAxis = calculateClickedTile(click).x;
        int yAxis = calculateClickedTile(click).y;
        if (xAxis>=getSize().width || yAxis>=getSize().height ||
                xAxis<0 || yAxis<0)
            throw new Exception("Kliknelo sie poza mape co?");
        return oldFields[xAxis][yAxis].getMarker();
    }

    private old_Piece getPieceFromCoordinates(Point click) throws Exception{
        int xAxis = calculateClickedTile(click).x;
        int yAxis = calculateClickedTile(click).y;
        if (xAxis>=getSize().width || yAxis>=getSize().height ||
            xAxis<0 || yAxis<0)
            throw new Exception("Kliknelo sie poza mape co?");
        return oldFields[xAxis][yAxis].getPiece();
    }

    public Dimension getSizeInPixels(){
        int w=segmentSize*(xDim+2);
        int h=segmentSize*(yDim+2);
        return new Dimension(w, h);
    }

    public Dimension getSize(){
        return new Dimension(xDim, yDim);
    }

    public TiledMap getMap(){
        return tiledMap;
    }

    public void swapTurn(){
        whiteMove =! whiteMove;
    }

    public old_Piece.COLOR TurnColor(){
        return (whiteMove) ? old_Piece.COLOR.WHITE : old_Piece.COLOR.BLACK;
    }

}












