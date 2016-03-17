package rc2k7.plugins.rpgstats.effects;

import java.lang.reflect.Method;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireWorkEffect {
	
	public static void playFireWork(World world, Location loc, FireworkEffect fwe)
	{
		try
		{
		Firework fw = (Firework)world.spawn(loc, Firework.class);
		
		Object objWorld = null;
		Object objFireWork = null;
		
		Method worldHandle = world.getClass().getDeclaredMethod("getHandle");
		Method fireworkHandle = fw.getClass().getDeclaredMethod("getHandle");
		
		objWorld = worldHandle.invoke(world, (Object[])null);
		objFireWork = fireworkHandle.invoke(fw, (Object[])null);
		
		Method broadcastEntityEffect = objWorld.getClass().getDeclaredMethod("broadcastEntityEffect");
		
		FireworkMeta fwm = (FireworkMeta)fw.getFireworkMeta();
		
		fwm.clearEffects();
		
		fwm.setPower(1);
		fwm.addEffect(fwe);
		fw.setFireworkMeta(fwm);
		
		broadcastEntityEffect.invoke(objWorld, new Object[]{objFireWork, (byte)17});
		fw.remove();
		}
		catch (Exception e)
		{
			
		}
		
	}

}
