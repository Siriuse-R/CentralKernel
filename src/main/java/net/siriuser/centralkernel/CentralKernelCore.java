/**
 * CentralKernel - CentralKernelCore.java
 *
 * Package: net.siriuser.centralkernel
 * Created: 2013/12/09 22:23:47
 */
package net.siriuser.centralkernel;

import net.siriuser.centralkernel.listeners.PlayerDeathMessageListener;
import net.siriuser.centralkernel.listeners.PlayerListener;
import net.syamn.utils.LogUtil;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CentralKernelCore extends JavaPlugin {

    private static CentralKernelCore instance;

    private PluginHelper worker;

    /**
     * @author SiriuseR
     */
    @Override
    public void onEnable() {
        instance = this;
        LogUtil.init(this);

        worker = PluginHelper.getInstance();
        worker.setMainPlugin(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new PlayerDeathMessageListener(this), this);

        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    /**
     * @author SiriuseR
     */
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        worker.disableAll();

        PluginHelper.dispose();

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
