package com.example.mmue_lm3.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class for creating Bitmaps and also for helping us with the resource management. It enables us
 * to load and unload a bitmap and simultaneously clear the resources.
 *
 * @author Mathias Schwengerer
 */
public class DynamicBitmap {

    private Bitmap bitmap;
    private final Resources resources;
    private final int resourceID;

    public DynamicBitmap(Resources res, int id) {
        resources = res;
        resourceID = id;
        load();
    }

    public void load() {
        if (isLoaded()) return;

        bitmap = BitmapFactory.decodeResource(resources, resourceID);
    }

    public void unload() {
        if (!isLoaded()) return;

        bitmap.recycle();
        bitmap = null;
    }

    public Bitmap get() {
        return bitmap;
    }

    public boolean isLoaded() {
        return bitmap != null;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }
}
