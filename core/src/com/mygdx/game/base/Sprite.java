package com.mygdx.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.utils.Regions;

public abstract class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion [] regions;
    protected int frame;
    protected boolean destroyed;

    public Sprite() {

    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new RuntimeException(" не задана текстура");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    //конструктор для создания массива текстур TextureRegion [] regions из атласа
     // в нем указывается: атлас, кол-во строк и столбцов и кол-во картинок- frames(т.к. может быть заполнен не весь массив картинками)

     public Sprite(TextureRegion region, int rows, int cols, int frames) {
         if (region == null) {
             throw new RuntimeException(" не задана текстура");
         }
         this.regions = Regions.split(region, rows, cols, frames);
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

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public  void resize (Rect worldBounds){};

    public  void touchDown(Vector2 touch, int pointer, int button){};

    public  void touchUp(Vector2 touch, int pointer, int button){};

    public  void touchDragged(Vector2 touch, int pointer){};

    public  void update(float delta){};

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        this.destroyed = true;
    }
    public void flushDestroy() {
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
