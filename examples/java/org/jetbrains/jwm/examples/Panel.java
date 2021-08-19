package org.jetbrains.jwm.examples;

import java.util.function.*;
import org.jetbrains.jwm.*;
import org.jetbrains.skija.*;

public abstract class Panel implements Consumer<Event> {
    public int lastWidth = 0, lastHeight = 0, lastX = 0, lastY = 0;
    public float lastScale = 1f;

    public abstract void paintImpl(Canvas canvas, int width, int height, float scale);

    @Override
    public void accept(Event e) {}

    public void paint(Canvas canvas, int x, int y, int width, int height, float scale) {
        int count = canvas.save();
        canvas.translate(x, y);
        canvas.clipRect(Rect.makeXYWH(0, 0, width, height));
        try (var paint = new Paint()) {
            paint.setColor(0x20000000);
            canvas.drawRRect(RRect.makeXYWH(0, 0, width, height, 4 * scale), paint);
        }
        paintImpl(canvas, width, height, scale);
        canvas.restoreToCount(count);

        lastWidth = width;
        lastHeight = height;
        lastX = x;
        lastY = y;
        lastScale = scale;
    }
}