/**
 * CentralKernel - I18n.java
 * 
 * Package: net.siriuser.centralkernel.storage
 * Created: 2013/12/10 04:10:23
 */
package net.siriuser.centralkernel.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.siriuser.centralkernel.CentralKernelCore;
import net.syamn.utils.LogUtil;
import net.syamn.utils.StrUtil;
import net.syamn.utils.Util;
import net.syamn.utils.file.FileStructure;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class I18n {
    private static final String languageDir = "languages";
    private static final String defaultLang = "ja-jp";
    
    private static Configuration fallbackMessages;
    private static Configuration messages;
    
    /**
     * Init I18n
     */
    public static void init(final String locale) {
        // extract languages
        extractLanguageFiles(false);

        // load default (fallback) language
        try{
            fallbackMessages = loadLanguageFile(defaultLang);
        }catch (Exception ex){
            LogUtil.warning("Could not load default(" + defaultLang + ".yml) messages file!");
        }

        // load custom language
        try{
            setCurrentLanguage(locale);
        }catch (Exception ex){
            LogUtil.warning("Could not load messages for " + locale + ": using default file (" + defaultLang + ".yml)");
            messages = fallbackMessages;
        }
    }
    
    private static void extractLanguageFiles(final boolean force){
        final File langDir = getLanguagesDir();
        FileStructure.createDir(langDir);

        // extract resources
        List<String> locales = new ArrayList<String>();

        locales.add("ja-jp");

        for (String locale : locales){
            FileStructure.extractResource("/languages/" + locale + ".yml", langDir, force, true, CentralKernelCore.getInstance());
        }
    }
    
    public static void setCurrentLanguage(final String locale) throws Exception{
        messages = loadLanguageFile(locale);
    }

    private static Configuration loadLanguageFile(final String locale) throws Exception{
        final File langDir = getLanguagesDir();
        File file = new File(langDir, locale + ".yml");

        // check file available
        if (file == null || !file.isFile() || !file.canRead()){
            LogUtil.warning("Unknown language file: " + locale);
            return null;
        }

        YamlConfiguration conf =  YamlConfiguration.loadConfiguration(file);

        // check all messages available
        if (fallbackMessages != null && conf.getKeys(true).size() != fallbackMessages.getKeys(true).size()){
            // collect missing message keys
            for (String key : fallbackMessages.getKeys(true)){
                if (!conf.contains(key) && !fallbackMessages.isConfigurationSection(key)){
                    conf.set(key, fallbackMessages.get(key));
                    LogUtil.warning("Missing message key on " + locale + ".yml: " + key);
                }
            }
        }
        return conf;
    }

    /* ***** Begin replace words ***** */
    public static final String PLAYER  = "%PLAYER%";
    /* ***** End replace words ******* */

    public static String _(final String key, final Object... args){
        // message file not proper loaded
        if (messages == null){
            LogUtil.warning("Localized messages file is NOT loaded..");
            return "!" + key + "!";
        }

        String msg = getString(messages, key);

        // missing key
        if (msg == null || msg.length() == 0){
            if (msg == null) LogUtil.warning("Missing message key '" + key + "'");
            msg = getString(fallbackMessages, key);
            if (msg == null || msg.length() == 0){
                return "!" + key + "!";
            }
        }

        // coloring
        msg = Util.coloring(msg);

        // build replaces
        Map<String, Object> binds = buildBinds(args);
        for (String bindKey : binds.keySet()){
            if (bindKey == null) continue;
            final Object obj = binds.get(bindKey);
            msg = msg.replace(bindKey, ((obj != null) ? obj.toString() : ""));
        }

        return msg;
    }

    private static Map<String, Object> buildBinds(final Object... args){
        Map<String, Object> bind = new HashMap<String, Object>(args.length / 2);

        if (args == null || args.length < 2){
            return bind;
        }

        for(int i = 0; i < args.length; i += 2){
            if ((i + 2) > args.length){
                break;
            }

            bind.put(args[i].toString(), args[i + 1]);
        }

        return bind;
    }

    private static String getString(final Configuration conf, final String key){
        String s = null;
        Object o = conf.get(key);

        if (o instanceof String){
            s = o.toString();
        }
        else if (o instanceof List<?>){
            @SuppressWarnings("unchecked")
            List<String> l = (List<String>) o;
            s = StrUtil.join(l, "\n");
        }

        return s;
    }
    
    /**
     * Get languages directory
     * @return File
     */
    private static File getLanguagesDir(){
        return new File(CentralKernelCore.getInstance().getDataFolder(), languageDir);
    }
}
