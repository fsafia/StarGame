package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.mygdx.game.screen.MenuScreen;


public class MyGdxGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
