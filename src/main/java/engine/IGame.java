package engine;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         un jeu qui peut evoluer (avant de se terminer) sur un plateau width x
 *         height
 */
public interface IGame {

	/**
	 * methode qui contient l'evolution du jeu
	 * @param dt
	 */
	public void evolve(double dt);

	public boolean hasPlayerWon();

	public int getScore();
	/**
	 * @return true si et seulement si le jeu est fini
	 */
	public boolean isFinished();

}
