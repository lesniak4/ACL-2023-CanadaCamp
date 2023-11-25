package model.components.characters.player;

import model.GameObject;
import model.components.characters.StatsComponent;
import model.components.physics.MonsterMovementComponent;
import model.components.world.CoinComponent;
import model.components.Component;
import model.components.world.KeyComponent;
import model.components.world.TeleportationTileComponent;
import model.components.world.WorldExitComponent;

public class PlayerInteractionComponent extends Component {

    private StatsComponent stats;

    public PlayerInteractionComponent(GameObject obj, StatsComponent stats) {

        super(obj);
        this.stats = stats;
    }

    public void interactWith(GameObject colliderObj){

        WorldExitComponent exit = colliderObj.getComponent(WorldExitComponent.class);
        if(exit != null && colliderObj.getGame().playerOwnsKey()){
            getGameObject().getGame().setPlayerWin(true);
        }

        // Check collision avec une pièce
        CoinComponent coin = colliderObj.getComponent(CoinComponent.class);
        if(coin != null){
            colliderObj.destroyGameObject();
            colliderObj.getGame().incrScore(coin.getValue());
        }

        // Check collision avec une hache
        KeyComponent key = colliderObj.getComponent(KeyComponent.class);
        if(key != null){
            colliderObj.destroyGameObject();
            colliderObj.getGame().setHasKey(true);
        }

        // Check collision avec un ennemi
        MonsterMovementComponent monster = colliderObj.getComponent(MonsterMovementComponent.class);
        if (monster != null && !stats.isInvisible()) {
            //colliderObj.getGame().setPlayerLose(true);
        }

        // Check collision avec une case de téléportation
        TeleportationTileComponent tpTile = colliderObj.getComponent(TeleportationTileComponent.class);
        if (tpTile != null) {
            this.getGameObject().setPosition(tpTile.getLinkedTile().getTeleportationPos());
        }
    }

    @Override
    public void update() {

    }
}