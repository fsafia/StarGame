package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class Logo extends Sprite {

    public Vector2 v = new Vector2();
    public Vector2 touch = new Vector2();
    public Vector2 buf = new Vector2();
    private static final float V_LEN = 0.01f;

    public Logo(Texture region) {
        super(new TextureRegion(region));
    }

    public void draw (SpriteBatch batch) {
        batch.draw(
                regions[frame],      // текстура
                getPos().x, getPos().y,  //точка отрисовки логотипа в начале Ставится в центр.
                halfWidth, halfHeight,   // точка врацения размещаем по центру половину высоты и ширины
                getWidth(), getHeight(),
                scale, scale, //скалирование
                angle
        );
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.25f);
        this.pos.set(worldBounds.pos);
    }
    @Override
    public  void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN);

    }

    public Vector2 getPos() {
        buf.set(touch);
        if (buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
        return pos;

    }
}
