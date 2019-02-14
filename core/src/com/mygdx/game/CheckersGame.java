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
    private int chessboardDims;
    private Chessboard board;

    public CheckersGame(){
    }

    @Override
    public void create(){
        chessboardDims=Gdx.graphics.getHeight();
        board = new Chessboard(chessboardDims);
        map = board.getMap();

        GameScreen gs = new GameScreen(board);
        setScreen(gs);
    }

    @Override
    public void dispose(){
        map.dispose();
        getScreen().dispose();
        super.dispose();
    }
}










