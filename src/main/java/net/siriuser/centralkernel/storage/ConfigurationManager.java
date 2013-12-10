/**
 * CentralKernel - CentralKernelCore.java
 * 
 * Package: net.siriuser.centralkernel.storage
 * Created: 2013/12/10 05:17:45
 */
package net.siriuser.centralkernel.storage;

import java.io.File;

import net.siriuser.centralkernel.CentralKernelCore;
import net.syamn.utils.LogUtil;
import net.syamn.utils.file.FileStructure;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationManager {
    private final int latestVersion = 1;
    
    private final CentralKernelCore plugin;
    
    private FileConfiguration config;
    private File pluginDir;
    
    /**
     * Constructor
     * 
     * @author SiriuseR
     * @param plugin
     */
    public ConfigurationManager(final CentralKernelCore plugin) {
        this.plugin = plugin;
        this.pluginDir = plugin.getDataFolder();
    }

    public void loadConfig(final boolean initiaLoad) throws Exception {
        FileStructure.createDir(pluginDir);
        
        File file = new File(pluginDir, "config.yml");
        if (!file.exists()) {
            FileStructure.extractResource("/config.yml", pluginDir, false, false, plugin);
            LogUtil.info("config.yml is not found! Created default config.yml!");
        }
        
        plugin.reloadConfig();
        config = plugin.getConfig();
        
        checkver(config.getInt("ConfigVersion", 1));
    }
    
    /**
     * Checkver
     * 
     * @author SiriuseR
     * @param ver
     */
    private void checkver(final int ver){
        // compare configuration file version
        if (ver < latestVersion) {
            // first, rename old configuration
            final String destName = "oldconfig-v" + ver + ".yml";
            String srcPath = new File(pluginDir, "config.yml").getPath();
            String destPath = new File(pluginDir, destName).getPath();
            try {
                FileStructure.copyTransfer(srcPath, destPath);
                LogUtil.info("Copied old config.yml to " + destName + "!");
            } catch (Exception ex) {
                LogUtil.warning("Failed to copy old config.yml!");
            }

            // force copy config.yml and languages
            FileStructure.extractResource("/config.yml", pluginDir, true, false, plugin);
            // Language.extractLanguageFile(true);

            plugin.reloadConfig();
            config = plugin.getConfig();

            LogUtil.info("Deleted existing configuration file and generate a new one!");
        }
    }
    
    /**
     * MySQL Config
     */
    public String getMySqlAddress(){
        return config.getString("MySQL.Server.Address", "localhost");
    }
    public int getMySqlPort(){
        return config.getInt("MySQL.Server.Port", 3306);
    }
    public String getMySqlDB(){
        return config.getString("MySQL.Database.Name", "DatabaseName");
    }
    public String getMySqlUser(){
        return config.getString("MySQL.Database.User", "UserName");
    }
    public String getMySqlPass(){
        return config.getString("MySQL.Database.Pass", "UserPassword");
    }
}
