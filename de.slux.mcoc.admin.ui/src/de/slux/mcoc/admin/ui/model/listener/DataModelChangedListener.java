package de.slux.mcoc.admin.ui.model.listener;

/**
 * Listener for changes in the main data model
 * 
 * @author slux
 */
public interface DataModelChangedListener
{
    /**
     * Notify that the data contained in the model identified by the
     * dataModelType parameter, has changed or its entire content has been
     * changed
     */
    void modelChanged();

    void newElement(Object parent, Object child);

}
