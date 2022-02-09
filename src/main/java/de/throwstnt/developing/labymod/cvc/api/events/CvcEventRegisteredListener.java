package de.throwstnt.developing.labymod.cvc.api.events;

/**
 * A registered listener. Stores data about the event and listener for quick execution
 */
public class CvcEventRegisteredListener {

    private Class<? extends CvcEvent> eventClazz;
    private CvcEventListener listener;
    private CvcEventExecutor executor;

    public CvcEventRegisteredListener(Class<? extends CvcEvent> eventClazz,
            CvcEventListener listener, CvcEventExecutor executor) {
        this.eventClazz = eventClazz;
        this.listener = listener;
        this.executor = executor;
    }

    public Class<? extends CvcEvent> getEventClazz() {
        return eventClazz;
    }

    public CvcEventListener getListener() {
        return listener;
    }

    public CvcEventExecutor getExecutor() {
        return executor;
    }
}
