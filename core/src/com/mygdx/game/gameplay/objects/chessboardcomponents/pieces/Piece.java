package com.mygdx.game.gameplay.objects.chessboardcomponents.pieces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.gameplay.GameplayDriver;
import com.mygdx.game.gameplay.mechanics.movement.MovementBehavior;
import com.mygdx.game.gameplay.mechanics.movement.RegularMovement;
import com.mygdx.game.gameplay.objects.Chessboard;
import com.mygdx.game.graphics.PieceGraphics;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public abstract class Piece extends Actor {
    public enum COLOR_VARIANT {
        BLACK, WHITE
    }

    private COLOR_VARIANT variant;
    private PieceGraphics graphics = new PieceGraphics();
    private MovementBehavior movementBehavior;
    private Chessboard chessboard = Chessboard.getInstance();
    private Point positionOnChessboard;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Point positionInPixels = chessboard.getPositionInPixels(new Point(
                positionOnChessboard.x, positionOnChessboard.y));
        graphics.sprite.setPosition(positionInPixels.x, positionInPixels.y);
        graphics.sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Piece(Point positionOnChessboard, Pixmap piecePixmap, COLOR_VARIANT variant) {
        this.positionOnChessboard = positionOnChessboard;
        graphics.pixmap = piecePixmap;
        this.variant = variant;
        movementBehavior = new RegularMovement(this);

        createTextureAndSprite();
        setSpriteNecessaryParameters();
    }

    private void createTextureAndSprite() {
        graphics.texture = new Texture(graphics.pixmap);
        graphics.sprite = new Sprite(graphics.texture);
    }

    private void setSpriteNecessaryParameters() {
        Sprite temp = graphics.sprite;
        this.setBounds(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
        this.setTouchable(Touchable.enabled);
    }

    protected void changePixmap(Pixmap piecePixmap) {
        graphics.pixmap = piecePixmap;

        createTextureAndSprite();
        setSpriteNecessaryParameters();
    }

    public Point getPositionOnChessboard() {
        return new Point(positionOnChessboard);
    }

    public void movePiece(Point destination) {
        if (isPositionValid()) {
            movementBehavior.movePiece();
        }
    }

    private boolean isPositionValid() {
        if (isPositionInChessboardBounds() &&
                isPositionEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isPositionEmpty() {
        throw new NotImplementedException();
    }

    private boolean isPositionInChessboardBounds() {
        throw new NotImplementedException();
    }

    public COLOR_VARIANT getVariant() {
        return variant;
    }

    protected void setMovementBehavior(Class behaviorClass) {
        try {
            movementBehavior = (MovementBehavior)
                    behaviorClass.getDeclaredConstructor(Piece.class)
                            .newInstance(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
