package com.mygdx.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.math.Rect;

public class Logo extends Sprite{
    public Logo(TextureRegion region) {
        super(new TextureRegion(region));
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
