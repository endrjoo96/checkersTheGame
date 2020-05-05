package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.files.FILES;
import com.mygdx.game.gameplay.GameplayDriver;
import com.mygdx.game.gameplay.objects.Chessboard;

public class PieceGraphics {
    public static Pixmap blackRegularPixmap =
            generatePixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_BLACK));
    public static Pixmap whiteRegularPixmap =
            generatePixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_WHITE));
    public static Pixmap blackQueenPixmap =
            generatePixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_BLACK_MASTER));
    public static Pixmap whiteQueenPixmap =
            generatePixmap(Gdx.files.internal(FILES.ASSETS.CHECKER_WHITE_MASTER));

    private static Pixmap generatePixmap(FileHandle file) {
        Pixmap temp = new Pixmap(file);
        return scalePixmap(temp);
    }
    private static Pixmap scalePixmap(Pixmap target){
        int size = Chessboard.getInstance().getSegmentSize();
        Pixmap temp = new Pixmap(size, size, target.getFormat());
        temp.drawPixmap(target, 0, 0, target.getWidth(), target.getHeight(),
                0, 0, temp.getWidth(), temp.getHeight());
        return temp;
    }

    public Texture texture;
    public Sprite sprite;
    public Pixmap pixmap;

}
