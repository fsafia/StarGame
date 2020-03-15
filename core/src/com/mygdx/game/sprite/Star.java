package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.base.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;

public class Star extends Sprite {

    protected final  Vector2 v;
    private Rect worldBounds;

    private float animateTimer;                    //для
    private float animateInterval = 1f;            //мерцания звезд
    private static final float STAR_HIGHT = 0.005f; // исходные размеры звезды

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));//название текстуры в атласе
        v = new Vector2();
        v.set(Rnd.nextFloat(-0.005f, 0.005f),Rnd.nextFloat(-0.1f, -0.01f)) ;
        animateTimer = Rnd.nextFloat(0, 1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(STAR_HIGHT);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHabdleBounds();
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0;
            setHeightProportion(STAR_HIGHT);
        } else {
            setHeightProportion(getHeight() + 0.0001f);
        }
    }

    public void checkAndHabdleBounds() {
        if(getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }

        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }

        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }

        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }
    }
}
