package de.slux.mcoc.admin.mmi.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.slux.mcoc.admin.mmi.McocAdminMmiGuiPlugin;
import de.slux.mcoc.admin.ui.util.PluginUtil;

public class AboutDialog extends Dialog
{
    public static final String VERSION = "1.2.0";
    private static final Logger log = Logger.getLogger(AboutDialog.class.getName());

    private StyledText textDescription;
    private Label lblVersion;
    private Image logoImg;

    /**
     * Create the dialog.
     * 
     * @param parentShell
     */
    public AboutDialog(Shell parentShell) {
        super(parentShell);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.
     * Shell)
     */
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        shell.setText("About...");
    }

    private void dispose()
    {
        if (this.logoImg != null)
            this.logoImg.dispose();
    }

    /**
     * Create contents of the dialog.
     * 
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent)
    {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout gl_container = new GridLayout(2, false);
        gl_container.horizontalSpacing = 10;
        container.setLayout(gl_container);

        Label labelLogo = new Label(container, SWT.NONE);
        labelLogo.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 3));
        File f = PluginUtil.getPluginResource(McocAdminMmiGuiPlugin.PLUGIN_ID, "images/logo.bmp");

        try
        {
            labelLogo.setImage(this.logoImg = new Image(Display.getCurrent(), new FileInputStream(f)));
        }
        catch (FileNotFoundException e)
        {
            labelLogo.setText("Logo image not available.");
            log.log(Level.SEVERE, "createDialogArea() ERROR: " + e, e);
        }

        new Label(container, SWT.NONE);

        textDescription = new StyledText(container, SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
        textDescription.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        textDescription.setEditable(false);
        textDescription.addFocusListener(new FocusListener()
        {

            @Override
            public void focusLost(FocusEvent e)
            {
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                lblVersion.setFocus();
            }
        });

        textDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        String text;
        textDescription.setText(text = getPmrAbout());

        FontData data = textDescription.getFont().getFontData()[0];
        Font titleFont = new Font(Display.getCurrent(), data.getName(), (int) (data.getHeight() * 1.4), SWT.BOLD);

        textDescription.setLineAlignment(0, 1, SWT.CENTER);
        StyleRange titleStyle = new StyleRange();
        titleStyle.start = 0;
        titleStyle.length = text.indexOf('\n'); // the first line
        titleStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
        titleStyle.font = titleFont;

        this.textDescription.setStyleRange(titleStyle);

        lblVersion = new Label(container, SWT.NONE);
        lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblVersion.setText("version " + VERSION);

        return container;
    }

    /**
     * Create contents of the button bar.
     * 
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent)
    {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize()
    {
        return new Point(680, 500);
    }

    /**
     * @return the String with the ABOUT of the PMR software
     */
    public static String getPmrAbout()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Marvel Contest of Champinos - Administration Tool - MMI");
        builder.append("\n\n");
        builder.append("Long description here");

        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#close()
     */
    @Override
    public boolean close()
    {
        boolean retVal = super.close();

        dispose();

        return retVal;
    }
}
