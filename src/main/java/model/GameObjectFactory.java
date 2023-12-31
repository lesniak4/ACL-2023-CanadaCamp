package model;

import data.ItemDataFactory;
import data.ItemType;
import engine.IGameController;
import model.components.ai.AIComponent;
import model.components.ai.PathNodeComponent;
import model.components.ai.PathfindingComponent;
import model.components.attacks.*;
import model.components.characters.CharacterAnimationComponent;
import model.components.characters.StatsComponent;
import model.components.characters.SwimComponent;
import model.components.characters.player.PlayerInputComponent;
import model.components.characters.player.PlayerPauseComponent;
import model.components.characters.player.skills.PlayerSkillsShopComponent;
import model.components.physics.ColliderComponent;
import model.components.physics.DamageAreaMovementComponent;
import model.components.physics.MonsterMovementComponent;
import model.components.physics.PlayerMovementComponent;
import model.components.rendering.AnimatedSpriteRendererComponent;
import model.components.rendering.BitmaskedSpriteRendererComponent;
import model.components.rendering.CameraComponent;
import model.components.rendering.SpriteRendererComponent;
import model.components.world.*;
import model.fsm.states.game.PlayingState;
import model.items.Inventory;
import model.items.ResourceData;
import model.items.WeaponData;
import model.world.Hex;
import model.world.HexLayout;
import model.world.WorldGraph;
import utils.GameConfig;
import utils.SpriteLoader;
import utils.Vector2;
import views.HealthBarView;

import java.awt.*;

public class GameObjectFactory {

    private static GameObjectFactory instance = new GameObjectFactory();

    public static GameObjectFactory getInstance() {
        return instance;
    }

    private GameObjectFactory(){

    }

    public GameObject createWallTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject wallTile = new GameObject(pos.X(), pos.Y(), "Wall_"+hex.getQ()+"_"+hex.getR(), game);
        wallTile.addComponent(new BitmaskedSpriteRendererComponent(wallTile, painter, Color.WHITE, 1, true, SpriteLoader.getInstance().getWallSprite()));
        //wallTile.addComponent(new HexRendererComponent(wallTile, painter, Color.GREEN, hex, layout, true));
        wallTile.addComponent(new ColliderComponent(wallTile, physics, layout.getSize().X(), false));

        return wallTile;
    }

    public GameObject createBorderTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject wallTile = new GameObject(pos.X(), pos.Y(), "Border_"+hex.getQ()+"_"+hex.getR(), game);
        wallTile.addComponent(new BitmaskedSpriteRendererComponent(wallTile, painter, Color.WHITE, 1, true, SpriteLoader.getInstance().getWallSprite()));
        //wallTile.addComponent(new HexRendererComponent(wallTile, painter, Color.GREEN, hex, layout, true));

        return wallTile;
    }

    public GameObject createPathTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject pathTile = new GameObject(pos.X(), pos.Y(), "Path_"+hex.getQ()+"_"+hex.getR(), game);
        pathTile.addComponent(new BitmaskedSpriteRendererComponent(pathTile, painter, Color.WHITE, 0, false, SpriteLoader.getInstance().getPathSprite()));
        pathTile.addComponent(new PathNodeComponent(pathTile));
        //pathTile.addComponent(new HexRendererComponent(pathTile, painter, Color.WHITE, hex, layout, true));

        return pathTile;
    }

    public GameObject createWaterTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject waterTile = new GameObject(pos.X(), pos.Y(), "Water_"+hex.getQ()+"_"+hex.getR(), game);
        waterTile.addComponent(new SpriteRendererComponent(waterTile, painter, Color.WHITE, 0, false, SpriteLoader.getInstance().getWaterSprite()));
        waterTile.addComponent(new PathNodeComponent(waterTile));

        ColliderComponent collider = new ColliderComponent(waterTile,  physics, layout.getSize().X(),true);
        waterTile.addComponent(collider);

        WaterComponent water = new WaterComponent(waterTile);
        water.subscribeToCollider(collider);
        waterTile.addComponent(water);

        //waterTile.addComponent(new HexRendererComponent(pathTile, painter, Color.WHITE, hex, layout, true));

        return waterTile;
    }

    public GameObject createSwimmingLessonTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject swimmingLesson = new GameObject(pos.X(), pos.Y(), "SwimmingLesson_"+hex.getQ()+"_"+hex.getR(), game);
        swimmingLesson.addComponent(new SpriteRendererComponent(swimmingLesson, painter, Color.WHITE, 0, false, SpriteLoader.getInstance().getSwimmingLessonSprite()));
        swimmingLesson.addComponent(new PathNodeComponent(swimmingLesson));

        ColliderComponent collider = new ColliderComponent(swimmingLesson,  physics, layout.getSize().X(),true);
        swimmingLesson.addComponent(collider);

        SwimmingLessonComponent lesson = new SwimmingLessonComponent(swimmingLesson, GameConfig.getInstance().getFrameCountToLearnSwimming());
        lesson.subscribeToCollider(collider);
        swimmingLesson.addComponent(lesson);

        //swimmingLesson.addComponent(new HexRendererComponent(pathTile, painter, Color.WHITE, hex, layout, true));

        return swimmingLesson;
    }

    public GameObject createResourceObject(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics, ResourceData data, int amount){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject resourceObj = new GameObject(pos.X(), pos.Y(), data.toString()+"_"+hex.getQ()+"_"+hex.getR(), game);
        resourceObj.addComponent(new SpriteRendererComponent(resourceObj, painter, Color.ORANGE, 1, false, data.getSprite()));
        ColliderComponent collider = new ColliderComponent(resourceObj,  physics, data.getColliderRadius(),true);
        resourceObj.addComponent(collider);
        ResourceComponent resource = new ResourceComponent(resourceObj, data, amount);
        resource.subscribeToCollider(collider);
        resourceObj.addComponent(resource);

        return resourceObj;
    }

    public GameObject createWeaponObject(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics, WeaponData data){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject weaponObj = new GameObject(pos.X(), pos.Y(), data.toString()+"_"+hex.getQ()+"_"+hex.getR(), game);
        weaponObj.addComponent(new SpriteRendererComponent(weaponObj, painter, Color.ORANGE, 1, false, data.getSprite()));
        ColliderComponent collider = new ColliderComponent(weaponObj,  physics, data.getColliderRadius(),true);
        weaponObj.addComponent(collider);
        WeaponComponent weapon = new WeaponComponent(weaponObj, data);
        weapon.subscribeToCollider(collider);
        weaponObj.addComponent(weapon);

        return weaponObj;
    }

    public GameObject createPlayerObject(CanadaGame game, double posX, double posY, CanadaPainter painter, IGameController controller, CanadaPhysics physics, PlayingState playingState, Inventory inventory){

        GameConfig gc = GameConfig.getInstance();
        SpriteLoader sl = SpriteLoader.getInstance();

        GameObject player = new GameObject(posX, posY, "Player", game);
        player.addComponent(new CameraComponent(player));
        AnimatedSpriteRendererComponent renderer = new AnimatedSpriteRendererComponent(player, painter, Color.WHITE, 1, false, SpriteLoader.getInstance().getPlayerIdleSprite(), 0.6d);
        player.addComponent(renderer);
        //player.addComponent(new SpriteRendererComponent(player, painter, Color.WHITE, 1, SpriteLoader.getInstance().getPlayerSprite()));
        //player.addComponent(new CircleRendererComponent(player, painter, Color.RED,1,1, true));

        PlayerInputComponent playerInputComponent = new PlayerInputComponent(player, controller, inventory);
        StatsComponent stats = new StatsComponent(player, gc.getPlayerBaseMS(), gc.getPlayerBaseMeleeDMG(), gc.getPlayerBaseRangedDMG(), gc.getPlayerMeleeAttackDistance(), gc.getPlayerRangedAttackSpeed());
        PlayerMovementComponent movement = new PlayerMovementComponent(player, gc.getPlayerBaseMS(), physics, playerInputComponent, stats);
        MeleeAttackComponent meleeAttackComponent = new MeleeAttackComponent(player, stats, movement, physics, gc.getPlayerMeleeAttackRadius(), gc.getPlayerMeleeStunFrameCount(), gc.getPlayerMeleeAttackLifetimeFrameCount(), gc.getPlayerMeleeAttackCooldownFrameCount(), ItemDataFactory.getWeaponData(ItemType.SWORD));
        RangedAttackComponent rangedAttackComponent = new RangedAttackComponent(player, stats, movement, physics, gc.getPlayerRangedAttackRadius(), gc.getPlayerRangedStunFrameCount(),gc.getPlayerRangedAttackFrameCount(), gc.getPlayerRangedAttackLifetimeFrameCount(),gc.getPlayerRangedAttackCooldownFrameCount(), ItemDataFactory.getWeaponData(ItemType.SLINGSHOT));
        playerInputComponent.setMeleeAttackComponent(meleeAttackComponent);
        playerInputComponent.setRangedAttackComponent(rangedAttackComponent);

        StunComponent stun = new StunComponent(player, renderer);
        playerInputComponent.setStunComponent(stun);

        player.addComponent(playerInputComponent);
        PlayerPauseComponent playerPauseComponent  = new PlayerPauseComponent(player, playerInputComponent);
        player.addComponent(playerPauseComponent);

        player.addComponent(stats);
        //player.addComponent(new PlayerSpeedModifierComponent(player, stats, 10000, 2d));
        //player.addComponent(new PlayerInvisibleModifierComponent(player, stats, 10000));

        HealthComponent health = new HealthComponent(player, gc.getPlayerBaseHealth());
        player.addComponent(health);
        HealthBarView healthBar = new HealthBarView(game, player, health);
        playingState.addView(healthBar);

        player.addComponent(movement);

        SwimComponent swim = new SwimComponent(player, false);
        playerInputComponent.setSwimComponent(swim);
        player.addComponent(swim);

        player.addComponent(new CharacterAnimationComponent(player, movement, meleeAttackComponent, rangedAttackComponent, swim, renderer, sl.getPlayerIdleSprite(), sl.getPlayerWalkingSprite(), sl.getPlayerFightingSprite(), sl.getPlayerSlingshotSprite(), sl.getPlayerLearningSwimSprite(), sl.getPlayerSwimmingSprite()));
        player.addComponent(new ColliderComponent(player, physics, 12.45d, false));

        player.addComponent(new PlayerSkillsShopComponent(player, playerInputComponent, stats, inventory, ItemDataFactory.getResourceData(ItemType.GOLD_COINS)));
        player.addComponent(meleeAttackComponent);
        player.addComponent(rangedAttackComponent);
        player.addComponent(stun);

        return player;
    }

    public GameObject createMonsterObject(CanadaGame game, double posX, double posY, CanadaPainter painter, WorldGraph worldGraph, CanadaPhysics physics, GameObject target, GameObject player, PlayingState playingState){

        GameConfig gc = GameConfig.getInstance();
        SpriteLoader sl = SpriteLoader.getInstance();

        GameObject monster = new GameObject(posX, posY, "Monster", game);
        PathfindingComponent pathfindingComponent = new PathfindingComponent(monster, worldGraph);
        pathfindingComponent.setTarget(target.getPosition());

        //monster.addComponent(new CircleRendererComponent(monster, painter, Color.RED,1, 8, true));
        AnimatedSpriteRendererComponent renderer = new AnimatedSpriteRendererComponent(monster, painter, Color.WHITE, 1, false, SpriteLoader.getInstance().getMonsterIdleSprite(), 0.6d);
        monster.addComponent(renderer);

        StatsComponent stats = new StatsComponent(monster, gc.getMonsterBaseMS(), gc.getMonsterBaseMeleeDMG(), gc.getMonsterBaseRangedDMG(), gc.getMonsterMeleeAttackDistance(), gc.getMonsterRangedAttackSpeed());


        HealthComponent health = new HealthComponent(monster, gc.getMonsterBaseHealth());
        monster.addComponent(health);
        HealthBarView healthBar = new HealthBarView(game, monster, health);
        playingState.addView(healthBar);

        MonsterMovementComponent movement = new MonsterMovementComponent(monster, gc.getMonsterBaseMS(), physics, pathfindingComponent);
        MeleeAttackComponent meleeAttack = new MeleeAttackComponent(monster, stats, movement, physics, gc.getMonsterMeleeAttackRadius(), gc.getMonsterMeleeStunFrameCount(),gc.getMonsterMeleeAttackLifetimeFrameCount(), gc.getMonsterMeleeAttackCooldownFrameCount(), ItemDataFactory.getWeaponData(ItemType.SWORD));
        StunComponent stun = new StunComponent(monster, renderer);
        SwimComponent swim = new SwimComponent(monster, true);

        monster.addComponent(new AIComponent(monster,pathfindingComponent, player, stun, meleeAttack, swim));

        monster.addComponent(movement);
        monster.addComponent(meleeAttack);
        monster.addComponent(stun);
        monster.addComponent(swim);

        monster.addComponent(new CharacterAnimationComponent(monster, movement, meleeAttack, null, swim, renderer, sl.getMonsterIdleSprite(), sl.getMonsterWalkingSprite(), sl.getMonsterFightingSprite(), null, null, sl.getMonsterSwimmingSprite()));
        monster.addComponent(new ColliderComponent(monster, physics, 8d, true));

        return monster;
    }

    public GameObject createWorldSpawnTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject exitTile = new GameObject(pos.X(), pos.Y(), "WorldSpawn", game);
        exitTile.addComponent(new BitmaskedSpriteRendererComponent(exitTile, painter, Color.WHITE, 0, false, SpriteLoader.getInstance().getPathSprite()));
        exitTile.addComponent(new ColliderComponent(exitTile, physics, layout.getSize().X(), false));
        exitTile.addComponent(new WorldSpawnComponent(exitTile));

        return exitTile;
    }

    public GameObject createWorldExitTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject exitTile = new GameObject(pos.X(), pos.Y(), "WorldExit", game);
        exitTile.addComponent(new BitmaskedSpriteRendererComponent(exitTile, painter, Color.WHITE, 0, false, SpriteLoader.getInstance().getPathSprite()));
        exitTile.addComponent(new SpriteRendererComponent(exitTile, painter, Color.ORANGE, 1, false, SpriteLoader.getInstance().getExitSprite()));

        ColliderComponent collider = new ColliderComponent(exitTile, physics, layout.getSize().X(), false);
        exitTile.addComponent(collider);

        WorldExitComponent exit = new WorldExitComponent(exitTile);
        exit.subscribeToCollider(collider);
        exitTile.addComponent(exit);

        return exitTile;
    }

    public GameObject createTeleportationTile(CanadaGame game, Hex hex, HexLayout layout, CanadaPainter painter, CanadaPhysics physics, TeleportationTileOrientation orientation){

        Vector2 pos = layout.hexToWorldPos(hex);
        GameObject tpTile = new GameObject(pos.X(), pos.Y(), "Mine_"+hex.getQ()+"_"+hex.getR(), game);
        tpTile.addComponent(new SpriteRendererComponent(tpTile, painter, Color.WHITE, 1, false, orientation == TeleportationTileOrientation.LEFT ? SpriteLoader.getInstance().getMineLeftSprite() : SpriteLoader.getInstance().getMineRightSprite()));

        ColliderComponent collider = new ColliderComponent(tpTile, physics, layout.getSize().X(), true);
        tpTile.addComponent(collider);

        TeleportationTileComponent tp = new TeleportationTileComponent(tpTile, orientation);
        tp.subscribeToCollider(collider);
        tpTile.addComponent(tp);

        return tpTile;
    }

    public GameObject createDamageArea(CanadaGame game, Vector2 position, AttackComponent owner, CanadaPhysics physics, double radius, int damage, int stunDuration, int lifetime, boolean destroyOnHit){

        GameObject damageArea = new GameObject(position.X(), position.Y(), "DamageArea_"+owner.getGameObject().toString(), game);
        damageArea.addComponent(new DamageAreaMovementComponent(damageArea, 0, new Vector2(0,0), physics));

        ColliderComponent collider = new ColliderComponent(damageArea, physics, radius, true);
        damageArea.addComponent(collider);

        DamageAreaComponent area = new DamageAreaComponent(damageArea, damage, stunDuration, lifetime, owner, destroyOnHit);
        area.subscribeToCollider(collider);
        damageArea.addComponent(area);

        return damageArea;
    }

    public GameObject createDamageArea(CanadaGame game, Vector2 position, AttackComponent owner, CanadaPhysics physics, double radius, int damage, int stunDuration, int lifetime, double movementSpeed, Vector2 direction, boolean destroyOnHit){

        GameObject damageArea = new GameObject(position.X(), position.Y(), "DamageArea_"+owner.getGameObject().toString(), game);
        damageArea.addComponent(new SpriteRendererComponent(damageArea, game.getPainter(), Color.ORANGE, 1, false, SpriteLoader.getInstance().getStoneSprite()));
        damageArea.addComponent(new DamageAreaMovementComponent(damageArea, movementSpeed, direction, physics));

        ColliderComponent collider = new ColliderComponent(damageArea, physics, radius, true);
        damageArea.addComponent(collider);

        DamageAreaComponent area = new DamageAreaComponent(damageArea, damage, stunDuration, lifetime, owner, destroyOnHit);
        area.subscribeToCollider(collider);
        damageArea.addComponent(area);

        return damageArea;
    }
}
