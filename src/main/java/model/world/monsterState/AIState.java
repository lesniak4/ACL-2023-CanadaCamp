package model.world.monsterState;

import model.components.AIComponent;
import model.fsm.State;

public abstract class AIState extends State {

    protected AIComponent aiComponent;

    public AIState(AIComponent aiComponent) {
        super();

        this.aiComponent = aiComponent;
    }
}
