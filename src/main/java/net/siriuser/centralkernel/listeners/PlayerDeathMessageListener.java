package net.siriuser.centralkernel.listeners;

import net.siriuser.centralkernel.CentralKernelCore;
import net.siriuser.centralkernel.PluginHelper;
import net.siriuser.centralkernel.storage.DeathMessageManager;
import net.syamn.utils.Util;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathMessageListener implements Listener {
    private CentralKernelCore plugin;
    private DeathMessageManager msgStr;

    public PlayerDeathMessageListener (final CentralKernelCore plugin) {
        this.plugin = plugin;
        this.msgStr = PluginHelper.getInstance().getDeathMsg();
    }

    @EventHandler
    public void PlayerDeath (final EntityDeathEvent event) {
        if (!(event instanceof PlayerDeathEvent)) return;

        final PlayerDeathEvent e = (PlayerDeathEvent)event;
        final EntityDamageEvent cause = e.getEntity().getLastDamageCause();

        String message = null;
        try {
            if (cause instanceof EntityDamageByEntityEvent) {
                message = getMessage(cause);
            } else {
                switch (cause.getCause()) {
                case SUFFOCATION:
                    message = msgStr.getSuffocation();
                    break;
                case FALL:
                    message = msgStr.getFall();
                    break;
                case FIRE:
                    message = msgStr.getFire();
                    break;
                case FIRE_TICK:
                    message = msgStr.getFireTick();
                    break;
                case LAVA:
                    message = msgStr.getLava();
                    break;
                case DROWNING:
                    message = msgStr.getDrowning();
                    break;
                case BLOCK_EXPLOSION:
                    message = msgStr.getExplosion();
                    break;
                case VOID:
                    message = msgStr.getVoid();
                    break;
                case LIGHTNING:
                    message = msgStr.getLightning();
                    break;
                case SUICIDE:
                    message = msgStr.getSuicide();
                    break;
                case STARVATION:
                    message = msgStr.getStarvation();
                    break;
                case POISON:
                    message = msgStr.getPoison();
                    break;
                case MAGIC:
                    //TODO: Magicとはなにか？
                case WITHER:
                    message = msgStr.getWitherEffect();
                    break;
                case THORNS:
                    //TODO: プレイヤー同士や、Entityの装備する防具でも発生するか？
                    break;
                default:
                    message = msgStr.getUnknow();
                    break;
                }
            }
        } catch (final NullPointerException ex) {
            return;
        }
        e.setDeathMessage(Util.coloring(
                message
                .replace("%PLAYER%", e.getEntity().getName())
                ));
    }

    private String getMessage(final EntityDamageEvent event) {
        final Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
        if (damager instanceof TNTPrimed) {
            return msgStr.getTNT();
        } else if (damager instanceof Blaze) {
            return msgStr.getBlaze();
        } else if (damager instanceof CaveSpider) {
            return msgStr.getCaveSpider();
        } else if (damager instanceof Creeper) {
            return msgStr.getCreeper();
        } else if (damager instanceof EnderDragon) {
            return msgStr.getEnderDragon();
        } else if (damager instanceof Enderman) {
            return msgStr.getEnderman();
        } else if (damager instanceof FallingBlock) {
            switch (((FallingBlock) damager).getMaterial()) {
                case ANVIL:
                    return msgStr.getAnvilFall();
                default:
                    break;
            }
        } else if (damager instanceof Ghast) {
            return msgStr.getGhast();
        } else if (damager instanceof Giant) {
            return msgStr.getGiant();
        } else if (damager instanceof IronGolem) {
            return msgStr.getIronGolem();
        } else if (damager instanceof MagmaCube) {
            return msgStr.getMagmaCube();
        } else if (damager instanceof Player) {
            return msgStr.getPlayer().replace("%KILLER%", ((Player) damager).getName());
        } else if (damager instanceof Projectile) {
            if (damager instanceof Arrow) {
                if (((Arrow) damager).getShooter() == null) {
                    //return ACHelper.getInstance().("arrow");
                } else if (((Arrow) damager).getShooter() instanceof Skeleton) {
                    return msgStr.getSkeletonArrow();
                } else if (((Arrow) damager).getShooter() instanceof Player) {
                    return msgStr.getPlayerArrow().replace("%KILLER%", ((Player)((Projectile) damager).getShooter()).getName());
                }
                //TODO: ディスペンサー対応
            } else if (damager instanceof Fireball) {
                if (((Fireball) damager).getShooter() == null) {
                    //return ACHelper.getInstance().("fireball");
                } else if (((Fireball) damager).getShooter() instanceof Ghast) {
                    //return ACHelper.getInstance().("ghast");
                } else if (((Fireball) damager).getShooter() instanceof Blaze) {
                    //return ACHelper.getInstance().("blaze");
                }
                //TODO: ディスペンサー対応
            } else if (damager instanceof ThrownPotion) {
                if (((ThrownPotion) damager).getShooter() instanceof Player) {
                    //return ACHelper.getInstance().("potion");
                } else if (((ThrownPotion) damager).getShooter() instanceof Witch) {
                    //return ACHelper.getInstance().("witch");
                }
                //TODO: ディスペンサー対応
            }
        } else if (damager instanceof Silverfish) {
            return msgStr.getSilverfish();
        } else if (damager instanceof Skeleton) {
            if (((Skeleton) damager).getSkeletonType() == SkeletonType.WITHER) {
                return msgStr.getWitherSkeleton();
            }
            return msgStr.getSkeleton();
        } else if (damager instanceof Slime) {
            return msgStr.getSlime();
        } else if (damager instanceof Spider) {
            return msgStr.getSpider();
        } else if (damager instanceof Witch) {
            return msgStr.getWitch();
        } else if (damager instanceof Wither) {
            return msgStr.getWither();
        } else if (damager instanceof Wolf) {
            if (((Wolf)damager).isTamed()) {
                return msgStr.getTeamedWolf().replace("%TEAMER%", ((Wolf) damager).getOwner().getName());
            }
            return msgStr.getWolf();
        } else if (damager instanceof Zombie) {
            if (damager instanceof PigZombie) {
                return msgStr.getZombiePig();
            }
            return PluginHelper.getInstance().getDeathMsg().getZombie();
        } else if (damager instanceof LivingEntity) {
            //return PluginHelper.getInstance().getDeathMsg();
        }

        return msgStr.getUnknow();
    }
}
