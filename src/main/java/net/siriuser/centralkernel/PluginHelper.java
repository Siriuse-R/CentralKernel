/**
 * CentralKernel - PluginHelper.java
 *
 * Package: net.siriuser.centralkernel
 * Created: 2013/12/10 16:49:48
 */
package net.siriuser.centralkernel;

import net.siriuser.centralkernel.feature.GeoIP;
import net.siriuser.centralkernel.permission.PermissionManager;
import net.siriuser.centralkernel.storage.ConfigurationManager;
import net.siriuser.centralkernel.storage.DeathMessageManager;
import net.siriuser.centralkernel.storage.I18n;
import net.syamn.utils.LogUtil;
import net.syamn.utils.queue.ConfirmQueue;

public class PluginHelper {
    private static long mainThreadID;
    private static long pluginStarted;
    private static PluginHelper instance = new PluginHelper();

    public static PluginHelper getInstance(){
        return instance;
    }
    public static void dispose(){
        instance = null;
    }

    private CentralKernelCore plugin;
    private ConfigurationManager config;
    private DeathMessageManager deathmsg;

    private int afkTaskID = -1;
    private int flymodeTaskID = -1;
    private boolean isEnableEcon = false;

    private boolean enabledMCB = false;
    private static boolean enabledMCBlistener = false;

    /**
     * プラスグインの初期化時と有効化時に呼ばれる
     */
    private void init(final boolean startup){
        // loadconfig
        try {
            config.loadConfig(true);
        } catch (Exception ex) {
            LogUtil.warning("an error occured while trying to load the config file.");
            ex.printStackTrace();
        }

        // Setup language
        LogUtil.info("Loading language file: " + config.getLanguage());
        if (startup){
            I18n.init(config.getLanguage());
        }else{
            try {
                I18n.setCurrentLanguage(config.getLanguage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //Load deathmessage
        try {
            deathmsg.loadMessage(true);
        } catch (Exception ex) {
            LogUtil.warning("an error occured while trying to load the deathmessage file.");
            ex.printStackTrace();
        }

        //GeoIP init..
        if (config.getUseGeoIP()){
            new GeoIP(plugin).init();
        }

        PermissionManager.setupPermissions(plugin);
        ConfirmQueue.getInstance();
    }

    public void setMainPlugin(final CentralKernelCore plugin){
        mainThreadID = Thread.currentThread().getId();
        this.plugin = plugin;
        this.config = new ConfigurationManager(plugin);
        this.deathmsg = new DeathMessageManager(plugin);

        init(true);
    }

    public void disableAll(){

        if (afkTaskID != -1){
            plugin.getServer().getScheduler().cancelTask(afkTaskID);
            afkTaskID = -1;
        }
        if (flymodeTaskID != -1){
            plugin.getServer().getScheduler().cancelTask(flymodeTaskID);
            flymodeTaskID = -1;
        }

        ConfirmQueue.dispose();
        GeoIP.dispose();
    }

    /**
     * プラグインをリロードする
     */
    public synchronized void reload(){
        disableAll();
        System.gc();
        init(false);

        try {
            I18n.setCurrentLanguage(config.getLanguage());
        } catch (Exception ex) {
            LogUtil.warning("An error occured while trying to load the language file!");
            ex.printStackTrace();
        }
    }

    // Economy getter/setter
    public void setEnableEcon(final boolean enable){
        this.isEnableEcon = enable;
    }
    public boolean isEnableEcon(){
        return this.isEnableEcon;
    }

    /**
     * 設定マネージャを返す
     *
     * @return ConfigurationManager
     */
    public ConfigurationManager getConfig() {
        return config;
    }

    public DeathMessageManager getDeathMsg() {
        return deathmsg;
    }
}
