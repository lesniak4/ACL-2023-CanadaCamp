package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import engine.IGamePainter;
import model.components.rendering.GraphicsComponent;
import utils.Vector2;
import model.components.rendering.HexRendererComponent;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class CanadaPainter implements IGamePainter {

	/**
	 * la taille de la fenêtre
	 */
	protected static final int WIDTH = 700;
	protected static final int HEIGHT = (int)(Math.sqrt(3f) * WIDTH * 0.5f );

	protected List<GraphicsComponent> drawQueue;

	protected Vector2 cameraPosition;

	/**
	 * appelle constructeur parent
	 */
	public CanadaPainter() {

		this.drawQueue = new ArrayList<>();
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		drawQueue.sort(Comparator.comparing(GraphicsComponent::getDepth));
		for(GraphicsComponent g : drawQueue) {
			Vector2 pos = g.getScreenPos();
			if(g.getSprite() != null) {
				int x = (int) pos.X() - (g.getSprite().getWidth(null) - WIDTH) / 2;
				int y = (int) pos.Y() - (g.getSprite().getHeight(null) - 64) + HEIGHT / 2;
				crayon.drawImage(g.getSprite(), x, y, null);
			}else{
				crayon.setColor(g.getColor());
				crayon.fill(g.getShape());
			}
		}

		// Pour afficher les position des hex

		/*
		for(GraphicsComponent g : drawQueue) {
			crayon.setColor(Color.BLACK);
			HexRendererComponent h =  (g instanceof HexRendererComponent ? (HexRendererComponent)g : null);
			if(h != null)
				crayon.drawString(h.getHex().getQ() + ";" + h.getHex().getR(), g.getShape().getBounds().x+ 26, g.getShape().getBounds().y+41);
		}
		*/

		// Pour afficher les hitboxes
		/*
		for(GraphicsComponent g : drawQueue) {
			ColliderComponent collider = g.getGameObject().getComponent(ColliderComponent.class);
			if(collider != null){
				crayon.setColor(Color.red);
				crayon.draw(new Ellipse2D.Double(g.getGameObject().getX()-collider.getRadius(), g.getGameObject().getY()-collider.getRadius(),collider.getRadius()*2, collider.getRadius()*2));
				crayon.draw(g.getShape());
			}
		}
		*/
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	public void addToDrawQueue(GraphicsComponent graphics){

		drawQueue.add(graphics);
	}

	public void clearDrawQueue(){
		drawQueue.clear();
	}

	public void setCameraPosition(Vector2 cameraPosition){
		this.cameraPosition = cameraPosition;
	}

}
