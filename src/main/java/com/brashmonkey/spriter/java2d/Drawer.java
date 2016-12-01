package com.brashmonkey.spriter.java2d;

import com.brashmonkey.spriter.Timeline;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Trixt0r on 01.12.2016.
 */
public class Drawer extends com.brashmonkey.spriter.Drawer<BufferedImage> {

    private static final AffineTransform identity = new AffineTransform();
    public Graphics2D graphics;
    private JFrame frame;

    public Drawer(JFrame frame, Loader loader) {
        super(loader);
        this.frame = frame;
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        graphics.setColor(new Color(r, g, b, a));
    }

    @Override
    public void rectangle(float x, float y, float width, float height) {
        graphics.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void draw(Timeline.Key.Object object) {
        graphics.setTransform(identity);
        graphics.scale(1, -1);
        graphics.translate(0, -frame.getContentPane().getHeight());

        BufferedImage sprite = loader.get(object.ref);
        float newPivotX = sprite.getWidth() * object.pivot.x;
        float newX = object.position.x - newPivotX * object.scale.x;
        float newPivotY = sprite.getHeight() * object.pivot.y;
        float newY = object.position.y - newPivotY * object.scale.y;
        graphics.rotate(Math.toRadians(object.angle), object.position.x, object.position.y);
        int height = -(int) (sprite.getHeight() * object.scale.y);
        graphics.drawImage(sprite, (int) newX, (int) newY - height, (int) (sprite.getWidth() * object.scale.x), height, null);
    }

    @Override
    public void circle(float x, float y, float radius) {
        graphics.drawOval((int) (x - radius), (int) (y - radius), (int) radius, (int) radius);
    }
}
