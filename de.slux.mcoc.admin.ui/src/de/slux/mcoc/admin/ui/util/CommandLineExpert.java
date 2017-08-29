package de.slux.mcoc.admin.ui.util;

public class CommandLineExpert
{
	private CommandLineExpert() {}
	
	/**
	 * Get argument
	 * @param opt
	 * @param args
	 * @return null if an error occurs
	 * @throws Exception 
	 */
	public static String getOptArg(String opt, String[] args) throws Exception
	{
		for(int i = 0; i < args.length; i++)
			if(args[i].equals(opt) && i + 1 < args.length)
				return args[i + 1];
		
		if(haveOption(opt, args))
			throw new Exception("value for option '" + opt + "' missing.");
		
		return null;
	}

	/**
	 * Find the option
	 * @param opt
	 * @param args
	 * @return true or false
	 */
	public static boolean haveOption(String opt, String [] args)
	{
		for(String o : args)
			if(opt.equals(o))
				return true;
		
		return false;
	}
}
