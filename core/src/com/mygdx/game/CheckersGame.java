package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.graphics.*;

public class CheckersGame extends ApplicationAdapter {
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer render;
    private int chessboardDims;

    public CheckersGame(){
    }

    @Override
    public void create(){
        chessboardDims=Gdx.graphics.getHeight();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = Chessboard.createChessboard(chessboardDims);
        render = new OrthogonalTiledMapRenderer(map);
        render.setView(camera);
        //CameraTranslation.centerObject(map.);
        camera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        render.setView(camera);
        render.render();
    }

    @Override
    public void dispose(){
        render.dispose();
        map.dispose();
        super.dispose();
    }
}
