package de.slux.mcoc.admin.mmi.handler;

import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PlatformUI;

import de.slux.mcoc.admin.mmi.widget.AboutDialog;

public class OpenAboutHandler extends AbstractHandler implements IHandler
{
    private static final java.util.logging.Logger log = Logger.getLogger(OpenAboutHandler.class.getName());

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException
    {
        log.finer("execute() called.");

        new AboutDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()).open();

        return null;
    }

}
