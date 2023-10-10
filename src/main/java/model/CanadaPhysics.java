package model;

import engine.IGamePhysics;
import model.components.physics.ColliderComponent;
import model.components.physics.MovementComponent;

import java.util.*;

public class CanadaPhysics implements IGamePhysics {

    private Collection<ColliderComponent> colliders;
    private Collection<MovementComponent> toUpdate;

    public CanadaPhysics(){

        colliders = new ArrayList<>();
        toUpdate = new ArrayList<>();
    }

    @Override
    public void updatePhysics(double dt) {

        for (MovementComponent m : toUpdate){
            // On effectue les opérations à faire (collisions, deplacement, ...)
            GameObject gameObject = m.getGameObject();

            double lastX = gameObject.getX();
            double lastY = gameObject.getY();

            double newX = lastX + m.getVelocityX() * dt;
            double newY = lastY + m.getVelocityY() * dt;

            ColliderComponent collider = gameObject.getComponent(ColliderComponent.class);
            if(collider != null){

                LinkedList<Pair<Double,Double>> positions = new LinkedList<>();
                positions.addAll(Arrays.asList(new Pair<>(newX, newY), new Pair<>(lastX, newY), new Pair<>(newX, lastY), new Pair<>(lastX, lastY)));
                boolean collided;

                for(Pair p : positions) {
                    collided = false;
                    gameObject.setPosition((double)p.left, (double)p.right);

                    for (ColliderComponent c : colliders) {
                        if (c != collider && isColliding(collider, c)) { // Il y a collision, on essaye de déplacer au mieux le joueur
                            collided = true;
                            break;
                        }
                    }

                    if(!collided)
                        break;
                }
            }
            m.resetVelocity();
        }

        toUpdate.clear();
    }

    public void addCollider(ColliderComponent collider){

        this.colliders.add(collider);
    }

    public void addToUpdate(MovementComponent movementComponent){

        this.toUpdate.add(movementComponent);
    }

    private boolean isColliding(ColliderComponent c1, ColliderComponent c2){

        GameObject go1 = c1.getGameObject();
        GameObject go2 = c2.getGameObject();

        double x1 = go1.getX();// + c1.getRadius();
        double x2 = go2.getX();// + c2.getRadius();

        double y1 = go1.getY();// + c1.getRadius();
        double y2 = go2.getY();// + c2.getRadius();

        double r1 = c1.getRadius();
        double r2 = c2.getRadius();

        return (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) <= (r1+r2)*(r1+r2);
    }


    private class Pair<L,R> {

        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

}
