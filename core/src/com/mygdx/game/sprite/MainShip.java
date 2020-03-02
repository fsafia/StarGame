package com.mygdx.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;

public class MainShip extends Sprite {

    public final Vector2 v = new Vector2();
    public final Vector2 v0 = new Vector2(0.5f,0);  //постоянная скорость - движение вдоль Х

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;
    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private final Vector2 bulletV;
    private final Vector2 bulletPos;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0,0.5f);
        this.bulletPos = new Vector2();
    }
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public void update (float delta) {
        pos.mulAdd(v,delta);
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if(getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
        }
    }

    public  void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
        }
    }

    public void moveRight() {
        v.set(v0);
    }

    public void moveLeft() {
        v.set(v0).rotate(180);
    }
    public void stop() {
        v.setZero();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if(pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if( leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }

    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, getTop());
        bullet.set(this, bulletRegion, this.bulletPos, bulletV, 0.01f, worldBounds,1);
    }
}
