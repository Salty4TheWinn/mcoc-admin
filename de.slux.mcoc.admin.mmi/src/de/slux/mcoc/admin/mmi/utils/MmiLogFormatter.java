
package de.slux.mcoc.admin.mmi.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author slux
 *
 */
public class MmiLogFormatter extends Formatter
{
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd' 'HH:mm:ss.SSS";
    public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT);

    /**
     * Constructor
     */
    public MmiLogFormatter() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(LogRecord record)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(DATE_TIME_FORMATTER.format(new Date(record.getMillis())));
        sb.append("] [");
        sb.append(String.format("%1$-8s", record.getLevel().getName()));
        sb.append("] ");

        if (record.getSourceClassName() != null)
            sb.append(record.getSourceClassName());

        if (record.getSourceMethodName() != null)
        {
            sb.append(".");
            sb.append(record.getSourceMethodName());
        }

        sb.append(": ");
        sb.append(record.getMessage());
        sb.append(System.getProperty("line.separator"));

        // Log exception
        if (record.getThrown() != null)
        {
            sb.append("Throwable stacktrace: ");
            Throwable t = record.getThrown();
            PrintWriter pw = null;
            try
            {
                StringWriter sw = new StringWriter();
                pw = new PrintWriter(sw);
                t.printStackTrace(pw);
                sb.append(sw.toString());
            }
            catch (Exception e)
            {
                sb.append("Stack trace not available for exception: ");
                sb.append(t);
            }
            finally
            {
                if (pw != null)
                {
                    try
                    {
                        pw.close();
                    }
                    catch (Exception e)
                    {
                        /* ignore */ }
                }
            }
        }

        return sb.toString();
    }

}
