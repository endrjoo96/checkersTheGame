package com.mygdx.game.gameplay;

import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.gameplay.mechanics.gamerules.CheckersStandardRules;
import com.mygdx.game.gameplay.mechanics.gamerules.Rules;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.gameplay.physical_input.DesktopInputAdapter;

public final class GameplayDriver {

    private static Chessboard chessboard = Chessboard.getInstance();
    private static Rules gameRules = new CheckersStandardRules();
    public static InputAdapter inputAdapter = new DesktopInputAdapter();

    public static void prepareActors(){
        prepareChessboard();
    }

    private static void prepareChessboard(){
        chessboard.placePieces();
    }

    public static Rules getGameRules(){
        return gameRules;
    }
}
