package model.fsm.states.monsters;

import model.components.ai.AIComponent;
import utils.Vector2;

public class StateMoving extends AIState {
    public StateMoving(AIComponent aiComponent) {
        super(aiComponent);
    }

    @Override
    public void tick() {

    }

    @Override
    public void onEnter() {

        aiComponent.move();
    }

    @Override
    public void onExit() {

        aiComponent.stopMoving();
    }
}
