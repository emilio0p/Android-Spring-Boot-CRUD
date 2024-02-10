package com.vedruna.ordunapenaev2;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

public class RoundRectTransformation implements Transformation {

    private final int radius;
    private final int margin;

    RoundRectTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        // Aplicar esquinas redondeadas a la imagen
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final RectF rectF = new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return "rounded_corners";
    }
}