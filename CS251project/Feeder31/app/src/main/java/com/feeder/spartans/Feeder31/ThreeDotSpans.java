package com.feeder.spartans.Feeder31;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class ThreeDotSpans implements LineBackgroundSpan {

    int color;
    int radius;

    public ThreeDotSpans(int color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int oldColor = p.getColor();
        if (color != 0) {
            p.setColor(color);
        }
        if (color == Color.BLUE){
            c.drawCircle((left + right) / 2 - 20, bottom + radius, radius, p);
        }else if(color == Color.GRAY){
            c.drawCircle((left + right) / 2, bottom + radius, radius, p);
        }else if(color == Color.YELLOW){
            c.drawCircle((left + right) / 2 + 20, bottom + radius, radius, p);
        }

        p.setColor(oldColor);

    }
}
