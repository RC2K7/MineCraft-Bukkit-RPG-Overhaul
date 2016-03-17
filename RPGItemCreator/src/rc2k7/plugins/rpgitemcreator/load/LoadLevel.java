package rc2k7.plugins.rpgitemcreator.load;

import java.util.List;
import java.util.Map;

public class LoadLevel {
	
	int Level;
	List<String> ItemIDs;
	String Amount;
	Map<String, List<String>> Lores;
	Map<String, List<String>> Prefixes;
	Map<String, List<String>> Suffixes;
	
	public LoadLevel(int level, List<String> itemIDs, String amount, Map<String, List<String>> lores, Map<String, List<String>> prefixes, Map<String, List<String>> suffixes){
		this.Level = level;
		this.ItemIDs = itemIDs;
		this.Amount = amount;
		this.Lores = lores;
		this.Prefixes = prefixes;
		this.Suffixes = suffixes;
	}
	
	public int getLevel(){ return this.Level; }
	
	public List<String> getItemIDs(){ return this.ItemIDs; }
	
	public String getAmount(){ return this.Amount; }
	
	public Map<String, List<String>> getLores(){ return this.Lores; }
	
	public Map<String, List<String>> getPrefixes(){ return this.Prefixes; }
	
	public Map<String, List<String>> getSuffixes(){ return this.Suffixes; }

}
