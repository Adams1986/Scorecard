package com.github.xb10.scorecard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

/**
 * Class made to determine some standards for the look and feel of certain aspects of the app
 */
public class LookAndFeel {

    static void setToolbarLogo(Toolbar toolbar, Context context, int iconId, String title){


        Drawable adjustedDrawable = convertToDrawable(context, iconId);
        toolbar.setLogo(adjustedDrawable);
        toolbar.setTitle(title);
    }

    private static Drawable convertToDrawable(Context context, int iconId){

        //Adjusting drawable to fit size in toolbar.
        Drawable dr = ContextCompat.getDrawable(context, iconId);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
    }
}
