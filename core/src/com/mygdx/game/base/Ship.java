package com.mygdx.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.Explosion;

public class Ship extends Sprite {

    protected final  float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected  Vector2 v;
    protected  Vector2 v0;  //постоянная скорость - движение вдоль Х

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected  Vector2 bulletV;
    protected  Vector2 bulletPos;
    protected  float bulletHeight;
    protected  int damage;


    protected float reloadTimer;                    //для автоматической
    protected float reloadInterval;            //стрельбы

    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected Sound shootSound;



    protected int hp;   //количество жизней

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval){
            shoot();
            reloadTimer = 0f;
        }
        damageAnimateTimer += delta;                           //мерцание корабля в момент попадания
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void dispose(){
        shootSound.dispose();
    }

    protected void shoot() {
        shootSound.play(0.051f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, this.bulletPos, bulletV, bulletHeight, worldBounds,damage);
    }

    public void damage(int damage) {
        this.hp -= damage;
        if(hp <= 0) {
            destroy();
        }
        damageAnimateTimer = 0f;   //при попадании смена фрейма
        frame = 1;
    }

    protected void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.hp = 0;
        boom();
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getV() {
        return v;
    }
}
