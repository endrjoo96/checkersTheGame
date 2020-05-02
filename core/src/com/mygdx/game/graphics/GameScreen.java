package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.objects.Field;
import com.mygdx.game.objects.Piece;

public class GameScreen extends ScreenAdapter {
    Stage stage;
    MapRenderer mapRenderer;
    static Viewport viewport;

    public static Viewport getViewport(){
        return viewport;
    }

    public GameScreen(Chessboard board){
        viewport = new FitViewport((float) (board.getSizeInPixels().width), board.getSizeInPixels().height);

        this.stage = new Stage(viewport);
        this.mapRenderer = new OrthogonalTiledMapRenderer(board.getMap());

        for(int i=0; i<3;i++){
            for(int j=0;j<8;j++){
                if(board.getField(i,j).getColor()== Field.COLOR.BLACK){
                    Piece piece = new Piece(Piece.COLOR.BLACK, j, i, board);
                    stage.addActor(piece);
                }
            }
        }
        for(int i=5; i<8;i++){
            for(int j=0;j<8;j++){
                if(board.getField(i,j).getColor()== Field.COLOR.BLACK){
                    Piece piece = new Piece(Piece.COLOR.WHITE, j, i, board);
                    stage.addActor(piece);
                }
            }
        }


        board.assignStage(stage);
        Gdx.input.setInputProcessor(board);
    }

    @Override
    public void show(){
        stage.getViewport().apply(false);
        OrthographicCamera c = (OrthographicCamera) stage.getCamera();
    }

    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        mapRenderer.setView((OrthographicCamera) stage.getCamera());
        mapRenderer.render();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose(){
        stage.dispose();
        super.dispose();
    }
}











