package model.components.rendering;

import model.CanadaPainter;
import model.GameObject;
import utils.Vector2;
import model.world.Hex;
import model.world.HexLayout;

import java.awt.*;
import java.util.ArrayList;

public class HexRendererComponent extends GraphicsComponent {

    protected Hex hex;
    protected HexLayout layout;


    public HexRendererComponent(GameObject obj, CanadaPainter painter, Color color, int layer, Hex hex, HexLayout layout, boolean isVisible) {
        super(obj, painter, color, layer, isVisible, false);

        this.hex = hex;
        this.layout = layout;
    }

    public Hex getHex() {
        return hex;
    }

    @Override
    public void update() {

        Polygon p = new Polygon();
        ArrayList<Vector2> corners = layout.polygonCorners(hex);
        for(Vector2 c : corners){
            Vector2 screenPos = Vector2.worldToScreenIso(c);
            p.addPoint((int)screenPos.X(), (int)screenPos.Y());
        }
        this.shape = p;
        super.update();
    }
}
