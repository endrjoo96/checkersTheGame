package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture[] obstacles;
	private int squareSize=5;
	private static int jmp_val=2;

	private LwjglApplicationConfiguration config;

	public MyGdxGame(LwjglApplicationConfiguration config){
		this.config=config;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture(new Pixmap(squareSize, squareSize, Pixmap.Format.RGB888));
	}

	int offset_x = 0, offset_y = 0, momentum_x = 0, momentum_y = 0;
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, (float)1, (float)1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if(momentum_x <25)
				momentum_x++;
			offset_x += jmp_val;
			if(momentum_x >=25) offset_x += jmp_val;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if(momentum_x <25)
				momentum_x++;
			offset_x -= jmp_val;
			if(momentum_x >=25) offset_x -= jmp_val;
		}
		else{ momentum_x=0; }

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if(momentum_y <25)
				momentum_y++;
			offset_y -= jmp_val;
			if(momentum_y >=25) offset_y -= jmp_val;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if(momentum_y <25)
				momentum_y++;
			offset_y += jmp_val;
			if(momentum_y >=25) offset_y += jmp_val;
		}
		else{ momentum_y=0; }

		batch.draw(img, (float)((config.width/2)-squareSize/2)+offset_y,
						(float)((config.height/2)-squareSize/2)+offset_x);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
