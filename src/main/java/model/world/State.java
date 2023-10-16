package model.world;

import model.components.AIComponent;

public abstract class State implements IState{

    AIComponent aiComponent;

    public State(AIComponent aiComponent){
        this.aiComponent = aiComponent;
    }
}
