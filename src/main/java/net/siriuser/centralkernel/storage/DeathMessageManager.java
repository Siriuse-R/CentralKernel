package net.siriuser.centralkernel.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import net.siriuser.centralkernel.CentralKernelCore;
import net.syamn.utils.LogUtil;
import net.syamn.utils.file.FileStructure;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DeathMessageManager {
    private final CentralKernelCore plugin;

    private FileConfiguration config;
    private File pluginDir;

    /**
     * Constructor
     *
     * @author SiriuseR
     * @param plugin
     */
    public DeathMessageManager(final CentralKernelCore plugin) {
        this.plugin = plugin;
        this.pluginDir = plugin.getDataFolder();
    }

    public void loadMessage(final boolean initiaLoad) throws Exception {
        FileStructure.createDir(pluginDir);

        File file = new File(pluginDir, "deathmsg.yml");
        if (!file.exists()) {
            FileStructure.extractResource("/deathmsg.yml", pluginDir, false, true, plugin);
            LogUtil.info("deathmsg.yml is not found! Created default deathmsg.yml!");
        }
        config = getDeathMessageFile(file);
    }

    /**
     * @author SiriuseR
     * @param file
     * @return YamlConfiguration
     */
    public YamlConfiguration getDeathMessageFile(File file) {
        YamlConfiguration conf = new YamlConfiguration();

        try {
            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            while ( (line = reader.readLine()) != null ) {
                if ( line.contains(":") && !line.startsWith("#") ) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1).trim();
                    if ( value.startsWith("'") && value.endsWith("'") )
                        value = value.substring(1, value.length()-1);
                    conf.set(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return conf;
    }

    public String getBlaze() {
        return config.getString("Blaze", "ブレイズ");
    }
    public String getCaveSpider() {
        return config.getString("CaveSpider", "毒グモ");
    }
    public String getCreeper() {
        return config.getString("Creeper", "クリーパー");
    }
    public String getEnderDragon() {
        return config.getString("EnderDragon", "エンダードラゴン");
    }
    public String getEnderman() {
        return config.getString("Enderman", "エンダーマン");
    }
    public String getGhast() {
        return config.getString("Ghast", "ガスト");
    }
    public String getGiant() {
        return config.getString("Giant", "巨人");
    }
    public String getIronGolem() {
        return config.getString("IronGolem", "ゴーレム");
    }
    public String getMagmaCube() {
        return config.getString("MagmaCube", "マグマキューブ");
    }
    public String getPlayer() {
        return config.getString("Player", "プレイヤー");
    }
    public String getSilverfish() {
        return config.getString("Silverfish", "シルバーフィッシュ");
    }
    public String getSkeleton() {
        return config.getString("Skeleton", "スケルトン");
    }
    public String getWitherSkeleton() {
        return config.getString("WitherSkeleton", "ウィザースケルトン");
    }
    public String getSlime() {
        return config.getString("Slime", "スライム");
    }
    public String getSpider() {
        return config.getString("Spider", "クモ");
    }
    public String getTNT() {
        return config.getString("TNT", "TNT");
    }
    public String getWitch() {
        return config.getString("Witch", "うぃっち");
    }
    public String getWither() {
        return config.getString("Wither", "ウィザー");
    }
    public String getWolf() {
        return config.getString("Wolf", "野生の狼");
    }
    public String getTeamedWolf() {
        return config.getString("TeamedWolf", "狼");
    }
    public String getZombie() {
        return config.getString("Zombie", "ゾンビ");
    }
    public String getZombiePig() {
        return config.getString("ZombiePig", "ゾンビピッグ");
    }



    public String getSkeletonArrow() {
        return config.getString("SkeletonArrow", "スケルトンの矢");
    }
    public String getPlayerArrow() {
        return config.getString("PlayerArrow", "プレイヤーの矢");
    }

    public String getAnvilFall() {
        return config.getString("AnvilFall", "金床が落下してきた！");
    }


    public String getFall() {
        return config.getString("Fall", "落下死");
    }
    public String getFire() {
        return config.getString("Fire", "Fire");
    }
    public String getFireTick() {
        return config.getString("FireTick", "FireTick");
    }
    public String getSuffocation() {
        return config.getString("Suffocation", "窒息死");
    }
    public String getDrowning() {
        return config.getString("Drowning", "溺死");
    }
    public String getExplosion() {
        return config.getString("Explosion", "爆死");
    }
    public String getVoid() {
        return config.getString("Void", "void");
    }
    public String getLightning() {
        return config.getString("Lightning", "雷");
    }
    public String getSuicide() {
        return config.getString("Suicide", "自殺");
    }
    public String getStarvation() {
        return config.getString("Starvation", "餓死");
    }
    public String getLava() {
        return config.getString("Lava", "lava");
    }
    public String getWitherEffect() {
        return config.getString("WitherEffect", "ウィザーエフェクト");
    }
    public String getPoison() {
        return config.getString("Poison", "毒");
    }


    public String getUnknow() {
        return config.getString("Unknow", "Unknow");
    }
}
