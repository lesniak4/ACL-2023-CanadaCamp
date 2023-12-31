package engine;

import java.awt.event.KeyListener;
import java.util.Set;

/**
 * @author Horatiu Cirstea
 *
 * controleur qui envoie des commandes au jeu
 *
 */
public interface IGameController extends KeyListener {

    /**
     * quand on demande les commandes, le controleur retourne la commande en
     * cours
     *
     * @return commande faite par le joueur
     */
    public Set<Cmd> getCommands();

}