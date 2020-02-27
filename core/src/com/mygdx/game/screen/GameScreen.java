package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Ship;
import com.mygdx.game.sprite.Star;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;

    private Texture bg;
    private Background background;

    private Star[] stars;
    private final int STRAR_COUNT = 130;
    private Ship ship;
    private TextureAtlas mainAtlas;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background =new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[STRAR_COUNT];
        for (int i = 0; i < STRAR_COUNT ; i++) {
            stars[i] = new Star(atlas);
        }
        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        ship = new Ship(mainAtlas);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        mainAtlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);
        ship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }
    public void update (float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        ship.update(delta);
    }

    public void draw () {
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        batch.end();
    }
}
