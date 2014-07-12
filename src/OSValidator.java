/*
 * Language: Java
 * Author:   Veck Hsiao @ PLSM, National Chengchi University, Taipei, Taiwan
 * Time:     June/9/2014
 * Usage:    To get the OS information of executing environment
 */

public class OSValidator 
{

	public int getOSType() 
	{
		if (isWindows()) 
			return -1;
		else if (isMac())
			return 1;
		else if (isUnix()) 
			return 0;
		else 
			return 60000;
		
	}

	public static boolean isWindows() 
	{
		return ((System.getProperty("os.name").toLowerCase()).indexOf("win") >= 0);	
	}

	public static boolean isMac() 
	{
		return ((System.getProperty("os.name").toLowerCase()).indexOf("mac") >= 0);
	}

	public static boolean isUnix() 
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
}
