package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.sprite.Logo;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;

    private Vector2 touch;
    private Vector2 buf;

    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("textures/bg.png");

        touch = new Vector2();
        buf = new Vector2();
        background =new Background(bg);
        logo =new Logo(img);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        buf.set(touch);
        if (buf.sub(logo.pos).len() > 0.01f) {
            logo.pos.add(logo.v);
        } else {
            logo.pos.set(touch);
        }

        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);
        this.touch.set(touch);
        logo.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }
}
