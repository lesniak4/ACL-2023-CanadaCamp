package model.components.rendering;

import model.CanadaPainter;
import model.GameObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleRendererComponent extends GraphicsComponent{

    protected double radius;

    public CircleRendererComponent(GameObject obj, CanadaPainter painter, Color color, double radius,boolean isVisible) {
        super(obj, painter, color, isVisible);
        this.radius = radius;
    }

    @Override
    public void update(double dt) {

        this.shape = new Ellipse2D.Double(this.gameObject.getX()-radius, this.gameObject.getY()-radius, 2*radius, 2*radius);
        super.update(dt);
    }
}