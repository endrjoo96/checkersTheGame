package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.graphics.*;

public class CheckersGame extends Game {
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer render;
    private Viewport viewport;
    private int chessboardDims;

    public CheckersGame(){
    }

    @Override
    public void create(){
        chessboardDims=Gdx.graphics.getHeight();
        map = Chessboard.createChessboard(chessboardDims);

        GameScreen gs = new GameScreen(map);
        setScreen(gs);


        /*render = new OrthogonalTiledMapRenderer(map);
        render.setView(camera);
        float aspectratio = Gdx.graphics.getWidth()/ Gdx.graphics.getHeight();
        viewport = new FitViewport(aspectratio*8, 8, camera);
        viewport.setCamera(camera);
        camera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);*/

    }

    /*@Override
    public void render(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        render.setView(camera);
        render.render();
    }*/

    @Override
    public void dispose(){
        //render.dispose();
        map.dispose();
        getScreen().dispose();
        super.dispose();
    }
}










