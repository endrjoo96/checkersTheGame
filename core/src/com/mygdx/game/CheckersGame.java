package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.gameplay.GameplayDriver;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.graphics.*;

public class CheckersGame extends Game {
    public static int screenWidth, screenHeight;
    private Chessboard board;   //TODO wywalic

    public CheckersGame(){
    }

    @Override
    public void create(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        GameScreen gs = new GameScreen();
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










