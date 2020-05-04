package com.mygdx.game.gameplay;

import com.mygdx.game.gameplay.mechanics.gamerules.CheckersStandardRules;
import com.mygdx.game.gameplay.mechanics.gamerules.Rules;
import com.mygdx.game.gameplay.objects.Chessboard;

public class GameplayDriver {

    private static Chessboard chessboard = Chessboard.getInstance();
    private static Rules gameRules = new CheckersStandardRules();

    public static void prepareActors(){
        prepareChessboard();
    }

    private static void prepareChessboard(){
        chessboard.placePieces();
    }

    public static void runGame(){

    }

    public static Rules getGameRules(){
        return gameRules;
    }
}
