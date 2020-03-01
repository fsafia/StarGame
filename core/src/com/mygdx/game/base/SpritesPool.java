package com.mygdx.game.base;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    private final List<T> activeObjects = new ArrayList<>();

    private final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty())  {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }

        activeObjects.add(object);
        return object;
    }
}
