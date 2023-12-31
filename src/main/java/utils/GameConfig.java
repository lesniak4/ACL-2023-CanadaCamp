package utils;

public class GameConfig {

    private static GameConfig instance = new GameConfig();

    public static GameConfig getInstance() {
        return instance;
    }

    private GameConfig(){
    }

    public int getWinWidth(){ return 1024; } //700;

    public int getWinHeight(){ return 576; } //(int)(Math.sqrt(3f) * getWinWidth * 0.5f );

    public double getTileSize(){ return 92.5d; } //91.84d;

    public int getCoinValue(){ return 5; }

    // Player

    public double getPlayerBaseMS(){ return 1.9d; }

    public int getPlayerBaseMeleeDMG(){ return 2; }
    public int getPlayerBaseRangedDMG(){ return 1; }

    public int getPlayerBaseHealth(){
        return 30;
    }

    public double getPlayerMeleeAttackDistance(){
        return 25d;
    }

    public double getPlayerRangedAttackSpeed(){
        return 5d;
    }

    public double getPlayerMeleeAttackRadius(){ return 15d; }
    public int getPlayerMeleeStunFrameCount(){ return 60; }
    public int getPlayerMeleeAttackLifetimeFrameCount(){ return 10; }
    public int getPlayerMeleeAttackCooldownFrameCount(){ return 60; }

    public double getPlayerRangedAttackRadius(){ return 10d; }
    public int getPlayerRangedStunFrameCount(){ return 120; }
    public int getPlayerRangedAttackFrameCount(){ return 15; }
    public int getPlayerRangedAttackLifetimeFrameCount(){ return 90; }
    public int getPlayerRangedAttackCooldownFrameCount(){ return 130; }

    public int getFrameCountToLearnSwimming(){
        return 300;
    }

    // Monster

    // Facteur du nombre d'hexagone où le monstre repère le joueur
    public double getMonsterVision(){ return getTileSize() * 1.5d; }

    // Facteur du nombre d'hexagone où le monstre perd de vue le joueur
    public double getMonsterLooseVision(){ return getTileSize() * 1.75d; }

    public int getMonsterNb(){ return 5; }

    public double getMonsterBaseMS(){ return 1.55d; }

    public int getMonsterBaseMeleeDMG(){ return 2; }
    public int getMonsterBaseRangedDMG(){ return 1; }

    public int getMonsterBaseHealth(){
        return 15;
    }

    public double getMonsterMeleeAttackDistance(){
        return 25d;
    }

    public double getMonsterRangedAttackSpeed(){
        return 5d;
    }

    public double getMonsterMeleeAttackRadius(){ return 15d; }
    public int getMonsterMeleeStunFrameCount(){ return 30; }
    public int getMonsterMeleeAttackLifetimeFrameCount(){ return 10; }
    public int getMonsterMeleeAttackCooldownFrameCount(){ return 90; }

    public int getStunFlashFrameCount(){ return 8; }

    // Skill 1 Speed

    public String getSkill1Key() { return "A"; }
    public int getSkill1Cost(){ return 15; }
    public int getSkill1Duration(){ return 10000; }
    public int getSkill1Cooldown(){ return 30000; }
    public double getSkill1Modifier(){ return 1.5d; } // Speed factor

    // Skill 2 Invisibility

    public String getSkill2Key() { return "E"; }
    public int getSkill2Cost(){ return 25; }
    public int getSkill2Duration(){ return 7500; }
    public int getSkill2Cooldown(){ return 40000; }


    // Skill 3 Damage

    public String getSkill3Key() { return "R"; }
    public int getSkill3Cost(){ return 25; }
    public int getSkill3Duration(){ return 5000; }
    public int getSkill3Cooldown(){ return 20000; }
    public float getSkill3Modifier(){ return 2f; } // Damage factor

    public String getSaveFileName() { return "CanadaCamp.save"; }


}
