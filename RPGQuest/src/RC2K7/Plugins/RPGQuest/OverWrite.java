package RC2K7.Plugins.RPGQuest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityVillager;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.MerchantRecipe;
import net.minecraft.server.v1_5_R3.MerchantRecipeList;
import net.minecraft.server.v1_5_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_5_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_5_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_5_R3.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class OverWrite {
    
    @SuppressWarnings("rawtypes")
    public void overwriteLivingEntityAI(LivingEntity entity)
    {
        try
        {
            EntityLiving ev = ((CraftLivingEntity)entity).getHandle();
            
            Field goalsField = EntityLiving.class.getDeclaredField("goalSelector");
            Field targetField = EntityLiving.class.getDeclaredField("targetSelector");
            goalsField.setAccessible(true);
            targetField.setAccessible(true);
            PathfinderGoalSelector goals = (PathfinderGoalSelector) goalsField.get(ev);
            PathfinderGoalSelector targetGoals = (PathfinderGoalSelector) targetField.get(ev);
            
            Field listField = PathfinderGoalSelector.class.getDeclaredField("a");
            Field targetlistField = PathfinderGoalSelector.class.getDeclaredField("a");
            listField.setAccessible(true);
            targetlistField.setAccessible(true);
            List list = (List)listField.get(goals);
            List targetList = (List)targetlistField.get(targetGoals);
            list.clear();
            targetList.clear();
            listField = PathfinderGoalSelector.class.getDeclaredField("b");
            targetlistField = PathfinderGoalSelector.class.getDeclaredField("b");
            listField.setAccessible(true);
            targetlistField.setAccessible(true);
            list = (List)listField.get(goals);
            targetList = (List)targetlistField.get(targetGoals);
            list.clear();
            targetList.clear();
            
            Field speed = EntityLiving.class.getDeclaredField("bI");
            speed.setAccessible(true);
            @SuppressWarnings("unused")
                    Float dSpeed = (Float)speed.get(ev);
            dSpeed = 0.0F;
            speed.set(ev, new Float(0.0F));
            
            goals.a(0, new PathfinderGoalFloat(ev));
            goals.a(1, new PathfinderGoalLookAtPlayer(ev, EntityHuman.class, 12.0F, 1.0F));
            goals.a(2, new PathfinderGoalRandomLookaround(ev));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public boolean openTradeWindow(Player player)
    {
        try
        {
            EntityVillager villager = new EntityVillager(((CraftPlayer)player).getHandle().world, 0);
            villager.setCustomName("Quest Reward");
            
            Field recipeListField = EntityVillager.class.getDeclaredField("i");
            recipeListField.setAccessible(true);
            MerchantRecipeList mrl = (MerchantRecipeList)recipeListField.get(villager);
            if(mrl == null)
            {
                mrl = new MerchantRecipeList();
                recipeListField.set(villager, mrl);
            }
            mrl.clear();
            List<ItemStack[]> tst = new ArrayList<>();
            //ItemStack[] itms = { ((CraftItemStack)(new ItemStack(Material.STICK)).), new ItemStack(Material.APPLE), new ItemStack(Material.SADDLE) };
            //tst.add(itms);
            for(ItemStack[] recipe : tst)
            {
                mrl.add(new MerchantRecipe(recipe[0], recipe[1], recipe[2]));
            }
            villager.a_(((CraftPlayer)player).getHandle());
            return true;
        } catch(Exception e){ return false; }
    }
    
}
