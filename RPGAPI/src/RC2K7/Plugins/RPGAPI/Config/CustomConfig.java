package RC2K7.Plugins.RPGAPI.Config;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig {
	
	JavaPlugin Plugin;
	String FileName;
	FileConfiguration customConfig = null;
	File customConfigFile = null;
	
	public CustomConfig(JavaPlugin plugin, String filename)
	{
		this.FileName = filename;
		this.Plugin = plugin;
		this.customConfigFile = new File(this.Plugin.getDataFolder(), filename);
	}
	
	public void reloadCustomConfig()
	{
		if(this.customConfigFile == null)
		{
			this.customConfigFile = new File(this.Plugin.getDataFolder(), this.FileName);
		}
		this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
		
		InputStream defConfigStream = this.Plugin.getResource(this.FileName);
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.customConfig.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getCustomConfig()
	{
		if(this.customConfig == null)
		{
			this.reloadCustomConfig();
		}
		return this.customConfig;
	}
	
	public void saveCustomConfig()
	{
		if(this.customConfig == null || this.customConfigFile == null)
		{
			return;
		}
		try
		{
			this.getCustomConfig().save(this.customConfigFile);
		}catch(Exception e)
		{
			this.Plugin.getLogger().log(Level.SEVERE, "Could Not Save Config To " + this.customConfigFile);
		}
	}
	
	public void saveDefaultConfig()
	{
		if(customConfigFile == null)
		{
			this.customConfigFile = new File(this.Plugin.getDataFolder(), this.FileName);
		}
		if(!this.customConfigFile.exists())
		{
			this.Plugin.saveResource(this.FileName, false);
		}
	}
	
	public void loadDefault()
	{
		if(this.customConfigFile == null)
		{
			this.customConfigFile = new File(this.Plugin.getDataFolder(), this.FileName);
		}
		InputStream defConfigStream = this.Plugin.getResource(this.FileName);
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.customConfig.setDefaults(defConfig);
		}
		this.customConfig.options().copyDefaults();
	}
	
}
