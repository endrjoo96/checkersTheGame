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
import com.mygdx.game.objects.Piece;

public class GameScreen extends ScreenAdapter {
    Stage stage;
    MapRenderer mapRenderer;

    public GameScreen(Chessboard board){
        //float w=Gdx.graphics.getWidth();
        //float h=Gdx.graphics.getHeight();
        //double aspectRatio = w/h;
        Viewport viewport = new FitViewport((float) (board.getSizeInPixels().width), board.getSizeInPixels().height);
        //viewport.getCamera().rotate(180, 1,0,0);
        this.stage = new Stage(viewport);
        this.mapRenderer = new OrthogonalTiledMapRenderer(board.getMap());



        Piece pieceb = new Piece(Piece.COLOR.BLACK, 1, 3, board);
        Piece piecew = new Piece(Piece.COLOR.WHITE, 2, 4, board);
        piecew.disable();
        stage.addActor(pieceb);
        stage.addActor(piecew);

        board.assignStage(stage);
        Gdx.input.setInputProcessor(stage);
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











