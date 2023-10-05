package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.Cmd;
import java.awt.*;
import engine.IGame;
import engine.IGameController;
import model.components.physics.PlayerComponent;
import model.components.rendering.RectangleComponent;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class CanadaGame implements IGame {

	private CanadaPainter painter;

	private List<GameObject> gameObjects;

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public CanadaGame(String source, CanadaPainter painter, CanadaPhysics physics, IGameController controller) {
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}

		this.painter = painter;

		this.gameObjects = new ArrayList<>();

		World world = new World(this.painter);
		gameObjects.addAll(world.buildWorld("/map.txt"));

		GameObject playerTile = GameObjectFactory.getInstance().createPlayerTile(20,20,20,20,Color.BLACK, painter);
		PlayerComponent playerComponent = new PlayerComponent(playerTile, physics, 1f, controller);
		playerTile.addComponent(playerComponent);
		gameObjects.add(playerTile);
	}

	/**
	 * faire evoluer le jeu
	 *
	 */
	@Override
	public void evolve(double dt) {

		painter.clearDrawQueue();
		for(GameObject obj : gameObjects){
			obj.update(dt);
		}
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

}
