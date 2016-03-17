package RC2K7.Plugins.RPGAPI.Maps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class MapMenu extends MapRenderer {
	
	private String Title = new String();
	private List<String> Lines = new ArrayList<String>();
	private boolean Wrap = false;
	private int Index = 0;
	
	public MapMenu()
	{
		this.Title = "Test Title";
		this.Lines.add("HelloWorld");
		this.Lines.add("Hello");
		this.Lines.add("World");
		this.Lines.add("Last Item");
		this.Lines.add("Can I See This");
		this.Lines.add("OMG");
		this.Lines.add("Lol");
		this.Lines.add("Too Many?");
		this.Lines.add("What Is The Max Length?");
		this.Lines.add("12345678901234567890123456789012345678901234567890");
	}
	
	public void incrementIndex()
	{
		if(this.Index + 1 >= this.Lines.size())
		{
			if(this.Wrap) this.Index = 0;
		}
		else
		{
			this.Index++;
		}
	}
	
	public void decrementIndex()
	{
		if(this.Index - 1 < 0)
		{
			if(this.Wrap) this.Index = this.Lines.size() - 1;
		}
		else
		{
			this.Index--;
		}
	}
	
	public String getSelectedLine()
	{
		return this.Lines.get(this.Index);
	}
	
	private List<String> getLinesToDraw()
	{
		List<String> tmp = new ArrayList<String>();
		for(int x = -4; x <= 4; x++)
		{
			if(this.Index + x < 0 || this.Index + x >= this.Lines.size())
			{
				tmp.add("");
				continue;
			}
			tmp.add(this.Lines.get(this.Index + x));
		}
		return tmp;
	}

	@Override
	public void render(MapView view, MapCanvas canvas, Player player)
	{
		if(ScrollMap.isMapDirty(view.getId()) || ScrollMap.isPlayerDirty(player.getName()))
		{
			if(ScrollMap.isMapDirty(view.getId())) ScrollMap.removeDirtyMap(view.getId());
			if(ScrollMap.isPlayerDirty(player.getName())) ScrollMap.removeDirtyPlayer(player.getName());
			
			canvas.drawText(10, 10, MinecraftFont.Font, this.Title);
			int PosX = 10;
			int PosY = 30;
			for(String line : getLinesToDraw())
			{
				canvas.drawText(PosX, PosY, MinecraftFont.Font, line);
				PosY += 10;
			}
			canvas.drawText(0, 70, MinecraftFont.Font, ">>");
			player.sendMap(view);
		}
	}

}
