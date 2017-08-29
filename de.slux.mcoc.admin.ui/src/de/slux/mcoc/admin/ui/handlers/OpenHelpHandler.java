package de.slux.mcoc.admin.ui.handlers;

import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;


public class OpenHelpHandler extends AbstractHandler implements IHandler
{
	private static final Logger log = Logger.getLogger(OpenHelpHandler.class.getName());

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		log.finer("execute() Help button pressed.");

		return null;
	}

}
