package RC2K7.Plugins.RPGQuest.Serialization;

import org.bukkit.Bukkit;

import org.bukkit.Chunk;

public class SerializeChunk {
	
	Chunk Chunk;
	
	public SerializeChunk(Chunk chunk2)
	{
		this.Chunk = chunk2;
	}
	
	@SuppressWarnings("unused")
	public SerializeChunk(String chunk)
	{
		Chunk c;
		String w = "";
		int x = 0;
		int z = 0;
		for(String str : chunk.split(":"))
		{
			String[] block = str.split("@");
			if(block[0].equals("W")) w = block[1];
			if(block[0].equals("X")) x = Integer.parseInt(block[1]);
			if(block[0].equals("Z")) z = Integer.parseInt(block[1]);
		}
		c = Bukkit.getWorld(w).getChunkAt(x, z);
	}
	
	public String getString()
	{
            return "W@" + this.Chunk.getWorld().getName() + ":X@" + String.valueOf(this.Chunk.getX()) + ":Z@" + String.valueOf(this.Chunk.getZ());
	}
        
        @Override
        public String toString()
        {
            return "W@" + this.Chunk.getWorld().getName() + ":X@" + String.valueOf(this.Chunk.getX()) + ":Z@" + String.valueOf(this.Chunk.getZ());
        }
	
	public Chunk getChunk(){ return this.Chunk; }

}
