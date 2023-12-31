package model.components.characters.player.skills;

import model.GameObject;
import model.components.characters.StatsComponent;

public class PlayerDamageModifierComponent extends PlayerStatsModifierComponent {

    private float damageFactor;

    public PlayerDamageModifierComponent(GameObject obj, StatsComponent stats, int durationInMS, float damageFactor) {
        super(obj, stats, durationInMS);
        this.damageFactor = damageFactor;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void applyModifier() {

        stats.setActualMeleeDamage((int)(stats.getDefaultMeleeDamage() * damageFactor));
        stats.setActualRangedDamage((int)(stats.getDefaultRangedDamage() * damageFactor));
    }

    @Override
    public void resetModifier() {
        stats.resetActualDamage();
    }
}
