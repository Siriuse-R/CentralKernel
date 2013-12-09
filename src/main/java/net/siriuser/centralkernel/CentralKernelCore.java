/**
 * CentralKernel - CentralKernelCore.java
 * 
 * Package: net.siriuser.centralkernel
 * Created: 2013/12/09 22:23:47
 */
package net.siriuser.centralkernel;

import net.syamn.utils.LogUtil;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CentralKernelCore extends JavaPlugin {
    
    private static CentralKernelCore instance;
    
    /**
     * @author SiriuseR
     */
    @Override
    public void onEnable() {
        LogUtil.init(this);
        
        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }
    
    /**
     * @author SiriuseR
     */
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        
        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }
    
    /**
     * @author SiriuseR
     * @return CentralKernelCore Instance
     */
    public static CentralKernelCore getInstance() {
        return instance;
    }
}
