package model.components.player.skills;

import engine.Cmd;
import model.GameObject;
import model.components.Component;
import model.components.player.PlayerInputComponent;
import model.components.player.PlayerStatsComponent;
import model.skills.PlayerSkill;
import model.skills.PlayerSkillInvisible;
import model.skills.PlayerSkillSpeed;
import utils.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerSkillsShopComponent extends Component {

    private PlayerInputComponent playerInputComponent;
    private PlayerStatsComponent stats;

    private HashMap<Cmd, PlayerSkill> skills;
    private HashMap<Cmd, Long> lastTimesUsed;

    public PlayerSkillsShopComponent(GameObject obj, PlayerInputComponent playerInputComponent, PlayerStatsComponent stats) {
        super(obj);

        this.playerInputComponent = playerInputComponent;
        this.stats = stats;

        this.skills = new HashMap<>(2);
        skills.put(Cmd.SKILL_1, new PlayerSkillSpeed(Cmd.SKILL_1, "Vitesse", 15, 30000));
        skills.put(Cmd.SKILL_2, new PlayerSkillInvisible(Cmd.SKILL_2, "Invisibilité", 25, 40000));

        this.lastTimesUsed = new HashMap<>(2);
        for(Cmd cmd : skills.keySet()){
            lastTimesUsed.put(cmd, 0L);
        }
    }

    @Override
    public void update() {

        if(this.gameObject != null){
            Set<Cmd> commands = new HashSet<>(playerInputComponent.getCommands());

            if(!commands.isEmpty()) {
                for (Cmd command : commands) {
                    if(skills.containsKey(command)){

                        PlayerSkill skill = skills.get(command);
                        if(isSkillAvailable(command) && this.getGameObject().getGame().getScore() >= skill.getCost()) {
                            this.getGameObject().getGame().decrScore(skill.getCost());
                            lastTimesUsed.put(command, System.currentTimeMillis());
                            this.getGameObject().addComponent(skill.getNewModifierComponent(this.getGameObject(), stats));
                        }
                    }
                }
            }
        }
    }

    public boolean isSkillAvailable(Cmd cmd){
        PlayerSkill skill = skills.get(cmd);

        long currentTime = System.currentTimeMillis();
        return currentTime - lastTimesUsed.get(cmd) >= skill.getCooldown();
    }

    public PlayerSkill getSkill(Cmd cmd){
        return this.skills.get(cmd);
    }

    public float getCooldown(Cmd cmd){
        return (skills.get(cmd).getCooldown() - (System.currentTimeMillis() - lastTimesUsed.get(cmd))) / 1000f;
    }
}
