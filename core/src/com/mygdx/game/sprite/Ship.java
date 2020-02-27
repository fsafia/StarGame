package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class Ship extends Sprite {
    public Vector2 v = new Vector2();
    public Vector2 touch = new Vector2();
    public Vector2 buf = new Vector2();
    private static final float V_LEN = 0.01f;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(390/2, 287)[0][0]);//название текстуры в атласе
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        pos.set(0,-0.35f);
    }
    @Override
    public  void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN);

    }

    public void update (float delta) {
        buf.set(touch);
        if (buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
    }

    public void keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        if (keycode == 21){
            this.touch.set(pos.x - V_LEN, pos.y);
        }
        if (keycode == 22){
            this.touch.set(pos.x + V_LEN, pos.y);
        }
    }
}
