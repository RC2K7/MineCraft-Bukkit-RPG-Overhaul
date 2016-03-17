package RC2K7.Plugins.RPGAPI.Util;

import java.lang.reflect.Field;

public class ReflectionUtil
{
	
	public static Object getPrivateField(Object obj, String fieldname)
	{
		try
		{
			Field field = obj.getClass().getDeclaredField(fieldname);
			field.setAccessible(true);
			return field.get(obj);
		}
		catch(Exception e)
		{
			return null;
		}
	}

}
