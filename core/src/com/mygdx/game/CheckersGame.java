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
import com.mygdx.game.gameplay.Gameplay;
import com.mygdx.game.graphics.*;

public class CheckersGame extends Game {
    public static int screenWidth, screenHeight;
    private Chessboard board;

    public CheckersGame(){
    }

    @Override
    public void create(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        new Gameplay().runGame();

        board = new Chessboard();

        GameScreen gs = new GameScreen(board);
        setScreen(gs);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void dispose(){
        getScreen().dispose();
        super.dispose();
    }
}










