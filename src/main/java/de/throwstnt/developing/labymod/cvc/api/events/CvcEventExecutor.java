package de.throwstnt.developing.labymod.cvc.api.events;

/**
 * The predefined executor for one specific listener and event
 */
public interface CvcEventExecutor {

    /**
     * Executes the listener with the given event
     * 
     * @param listener the listener
     * @param event the event
     * @throws Exception any exception that might occure
     */
    void execute(CvcEventListener listener, CvcEvent event) throws Exception;
}
