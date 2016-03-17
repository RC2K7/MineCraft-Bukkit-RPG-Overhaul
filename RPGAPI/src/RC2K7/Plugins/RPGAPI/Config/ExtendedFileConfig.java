package RC2K7.Plugins.RPGAPI.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class ExtendedFileConfig
{
    
    private Plugin Plugin;
    
    private FileConfiguration YML = new YamlConfiguration();
    
    private File File = null;

    public ExtendedFileConfig(Plugin plugin, final File file) throws FileNotFoundException, IOException, InvalidConfigurationException
    {
        this.Plugin = plugin;
        this.File = file;
        reloadConfig();
    }
    
    public boolean get(final String path, final boolean def)
    {
        boolean tmp = this.YML.getBoolean(path, def);
        return tmp;
    }
    
    public double get(final String path, final double def)
    {
        double tmp = this.YML.getDouble(path, def);
        return tmp;
    }
    
    public int get(final String path, final int def)
    {
        int tmp = this.YML.getInt(path, def);
        return tmp;
    }
    
    public ItemStack get(final String path, final ItemStack def)
    {
        ItemStack tmp = this.YML.getItemStack(path, def);
        return tmp;
    }
    
    public long get(final String path, final long def)
    {
        long tmp = this.YML.getLong(path, def);
        return tmp;
    }
    
    public Object get(final String path, final Object def)
    {
        Object tmp = this.YML.get(path, def);
        return tmp;
    }
    
    public OfflinePlayer get(final String path, final OfflinePlayer def)
    {
        OfflinePlayer tmp = this.YML.getOfflinePlayer(path, def);
        return tmp;
    }
    
    public String get(final String path, final String def)
    {
        String tmp = this.get(path, def);
        return tmp;
    }
    
    public Set<?> get(final String path, final Set<?> def)
    {
        Set<?> tmp = (Set<?>)this.get(path, def);
        return tmp;
    }
    
     public <T> List<T> get(final String path, final List<T> def)
    {
        List<T> tmp = (List<T>)this.YML.getList(path, def);
        return tmp;
    }
    
    public boolean getBoolean(final String path)
    {
        return this.YML.getBoolean(path);
    }
    
    public double getDouble(final String path)
    {
        return this.YML.getDouble(path);
    }
    
    public int getInt(final String path)
    {
        return this.YML.getInt(path);
    }
    
    public long getLong(final String path)
    {
        return this.YML.getLong(path);
    }
    
    public Object getObject(final String path)
    {
        return this.YML.get(path);
    }
    
    public String getString(final String path)
    {
        return this.YML.getString(path);
    }
    
    public <T> Set<T> getSet(final String path)
    {
        return (Set<T>)this.YML.get(path);
    }
    
    public <T> List<T> getList(final String path)
    {
        return (List<T>)this.YML.getList(path);
    }
    
    public ItemStack getItemStack(final String path)
    {
        return this.YML.getItemStack(path);
    }
    
    public OfflinePlayer getOfflinePlayer(final String path)
    {
        return this.YML.getOfflinePlayer(path);
    }
    
    public void set(final String path, final Object obj)
    {
        this.YML.set(path, obj);
    }
    
    public Map<String, Object> getSectionMap(final String path)
    {
        final Map<String, Object> tmp = new HashMap<>();
        if(this.YML.contains(path) && this.YML.isConfigurationSection(path))
        {
            final Set<String> keys = this.YML.getConfigurationSection(path).getKeys(false);
            if(keys.size() > 0)
            {
                final Object[] key = keys.toArray();
                for(final Object element : key)
                {
                    tmp.put(String.format("%s.%s", path, (String)element), this.YML.get(String.format("%s.%s", path, (String)element)));
                }
            }
        }
        return tmp;
    }
    
    public boolean hasPath(final String path)
    {
        return this.YML.contains(path);
    }
    
    public ConfigurationSection createSection(final String path)
    {
        return this.YML.createSection(path);
    }
    
    public ConfigurationSection getSection(final String path)
    {
        return this.YML.getConfigurationSection(path);
    }
    
    public void reloadConfig()
    {
        try 
        {
            this.YML.load(this.File);
            
            InputStream defConfig = this.Plugin.getResource(this.File.getName());
            if(defConfig != null)
            {
                YamlConfiguration def = YamlConfiguration.loadConfiguration(defConfig);
                this.YML.setDefaults(def);
            }
        } catch (Exception e)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, null, e);
        }
    }
    
    public void saveConfig()
    {
        try 
        {
            this.YML.save(this.File);
        } catch (Exception ex) 
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, null, ex);
        }
    }
    
    public FileConfiguration getConfig()
    {
        return this.YML;
    }
    
}
