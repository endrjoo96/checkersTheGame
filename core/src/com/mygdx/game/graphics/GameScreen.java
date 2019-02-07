package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {
    Stage stage;
    MapRenderer mapRenderer;

    public GameScreen(TiledMap map){
        double aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        Viewport viewport = new FitViewport((float) (Gdx.graphics.getHeight()* aspectRatio)+64, Gdx.graphics.getHeight()+64);
        this.stage = new Stage(viewport);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void show(){
        stage.getViewport().apply(false);
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











