package RC2K7.Plugins.RPGAPI.CommandAPI;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.TreeMap;

public class MultiCommandFinder
{
	
	protected PrintStream Log = System.err;
	private MultiCommand Base;
	
	public MultiCommandFinder(MultiCommand base)
	{
		this.Base = base;
	}
	
	public void registerMethods(Object handler)
	{
		for(Method method : sortedMethods(handler))
		{
			MultiCmd cmd = method.getAnnotation(MultiCmd.class);
			if(cmd == null) continue;
			Class<?> paramTypes[] = method.getParameterTypes();
			if(paramTypes.length != 1 || !paramTypes[0].equals(SubInfo.class))
			{
				this.Log.println(String.format("MultiCommand: @MultiCMD Marked On '%s' Fromm Class %s, Must Recieve Only One Argument Of Type SubInfo.", method.getName(), handler.getClass().getName()));
				continue;
			}
			registerMethod(handler, method, cmd);
		}
	}
	
	public void registerMethod(Object handler, Method method, MultiCmd cmd)
	{
		String name = cmd.name();
		if(name.equals(""))
		{
			name = method.getName();
		}
		String permission = cmd.permission();
		if(permission.equals(""))
		{
			permission = null;
		}
		SubCommand sub = this.Base.addSub(name, permission)
				.setMinArgs(cmd.minArgs())
				.setDescription(cmd.description())
				.setUsage(cmd.usage());
		if(cmd.allowConsole())
		{
			sub = sub.allowConsole();
		}
		sub.setHandler(buildHandler(handler, method));
	}
	
	private MultiHandler buildHandler(final Object handler, final Method method)
	{
		return new MultiHandler() {
			
			@Override
			public void handle(SubInfo info) throws MultiException {
				try
				{
					method.invoke(handler, info);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
	}
	
	private ArrayList<Method> sortedMethods(Object handler)
	{
		TreeMap<String, Method> methodMap = new TreeMap<String, Method>();
		for(Method method : handler.getClass().getDeclaredMethods())
		{
			methodMap.put(method.getName(), method);
		}
		return new ArrayList<Method>(methodMap.values());
	}

}
