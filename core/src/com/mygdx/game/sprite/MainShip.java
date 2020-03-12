package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Ship;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;

public class MainShip extends Ship {

    private boolean pressedLeft;
    private boolean pressedRight;

    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, ExplosionPool explosionPool, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.v = new Vector2();
        this.v0 = new Vector2(0.5f, 0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0,0.5f);
        this.bulletPos = new Vector2();
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.hp = 10;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.reloadInterval = 0.2f;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public void update (float delta) {
        bulletPos.set(pos.x, getTop());
        super.update(delta);
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

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()           //пуля слева true! = false
                || bullet.getLeft() > getRight()          //пуля справа true! = false
                || bullet.getBottom() > pos.y          // низ пули больше до середины корабля true! =false
                || bullet.getTop() < getBottom());          //верх пули меньше низа корабля
    }
}
