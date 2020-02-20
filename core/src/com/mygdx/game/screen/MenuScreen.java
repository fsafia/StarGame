package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 pos;
    private Vector2 n;
    private volatile float xCurrent;
    private float yCurrent;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        v = new Vector2();
        pos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (((pos.y + img.getHeight()) <= Gdx.graphics.getHeight()) && (pos.x + img.getWidth()) <= Gdx.graphics.getWidth() && pos.y >= 0 && pos.x >= 0) {

            if (pos.x < xCurrent){
                v = n;
                pos.add(v.nor());
                System.out.println("pos.x = " + pos.x + "pos.y = " + pos.y);
                System.out.println(" xCurrent = " + xCurrent + "yCurrent " + yCurrent);

            } else {
                v.set(0f,0f);
            }
             if(pos.x > xCurrent) {
                 v = n;
                pos.add(v.nor());
                System.out.println("tX = " + touch.x + "tY = " + touch.y);
                System.out.println("pX = " + pos.x + "pY = " + pos.y);
                System.out.println(" xCurrent = " + xCurrent + "yCurrent " + yCurrent);
            }

        }

        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch.x = " + touch.x + " touch.y " + touch.y);
        xCurrent = touch.x;
        yCurrent = touch.y;
        n = touch.sub(pos);
        return false;
    }

}
