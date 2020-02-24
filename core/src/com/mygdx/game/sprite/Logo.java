package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class Logo extends Sprite {

    public Vector2 v = new Vector2();

    public Logo(Texture region) {
        super(new TextureRegion(region));
    }

    public void draw (SpriteBatch batch) {
        batch.draw(
                regions[frame],      // текстура
                getLeft(), getBottom(),  //точка отрисовки Ставится в центр, методы из Rect -cмещают текстуру на половину высоты, половину ширины.
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

    public  void touchDown(Vector2 touch, int pointer, int button) {

        v.set(touch.cpy().sub(pos));
        v.setLength(0.01f);
//        if (touch.cpy().sub(pos).len() > 0.01f) {
//            pos.add(v);
//        } else {
//            pos.set(touch);
//        }

    }
}
