package de.throwstnt.developing.labymod.cvc.api.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The event manager handling listener registration and event firing
 */
public class CvcEventManager {

    private static CvcEventManager instance;

    public static CvcEventManager getInstance() {
        if (instance == null)
            instance = new CvcEventManager();
        return instance;
    }

    private Set<CvcEventRegisteredListener> listeners = new HashSet<>();

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Fire the given event to all appropriate listeners
     * 
     * @param event the event
     */
    public void fireEvent(CvcEvent event) {
        executor.submit(() -> {
            synchronized (listeners) {
                listeners.forEach(registeredListener -> {
                    Class<? extends CvcEvent> eventClazz =
                            this.getEventClass(registeredListener.getEventClazz());

                    if (eventClazz != null && eventClazz.equals(event.getClass())) {
                        try {
                            registeredListener.getExecutor()
                                    .execute(registeredListener.getListener(), event);
                            // ChatUtil.log("Firing event: " + event.getClass());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private Class<? extends CvcEvent> getEventClass(Class<? extends CvcEvent> clazz) {
        try {
            clazz.getMethod("checkMe");
            return clazz;
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(CvcEvent.class)
                    && CvcEvent.class.isAssignableFrom(clazz.getSuperclass())) {
                return getEventClass(clazz.getSuperclass().asSubclass(CvcEvent.class));
            } else {
                System.err.println(
                        "Couldn't verify this class being an event class: " + clazz.getName());
                return null;
            }
        }
    }

    /**
     * Register all tagged event handlers inside the given listener
     * 
     * @param listener the listener class
     */
    public void registerListeners(CvcEventListener listener) {
        Set<Method> methods = new HashSet<>();

        try {
            Method[] publicMethods = listener.getClass().getMethods();
            methods = new HashSet<Method>(publicMethods.length, Float.MAX_VALUE);

            for (Method method : publicMethods) {
                methods.add(method);
            }

            for (Method method : listener.getClass().getDeclaredMethods()) {
                methods.add(method);
            }
        } catch (NoClassDefFoundError e) {
            System.err.println("Failed to register events for " + listener.getClass() + " because "
                    + e.getMessage() + " does not exist.");
        }

        for (final Method method : methods) {
            final CvcEventHandler eventHandler = method.getAnnotation(CvcEventHandler.class);
            if (eventHandler == null)
                continue;

            final Class<?> checkClass;
            if (method.getParameterTypes().length != 1 || !CvcEvent.class
                    .isAssignableFrom(checkClass = method.getParameterTypes()[0])) {

                System.err.println(
                        " Attempted to register an invalid EventHandler method signature \""
                                + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }

            final Class<? extends CvcEvent> eventClass = checkClass.asSubclass(CvcEvent.class);
            method.setAccessible(true);

            CvcEventExecutor executor = new CvcEventExecutor() {

                @Override
                public void execute(CvcEventListener listener, CvcEvent event) throws Exception {
                    try {
                        if (!eventClass.isAssignableFrom(event.getClass())) {
                            return;
                        }
                        method.invoke(listener, event);
                    } catch (InvocationTargetException ex) {
                        throw new Exception(ex.getCause());
                    } catch (Throwable t) {
                        throw new Exception(t);
                    }
                }
            };

            CvcEventRegisteredListener registeredListener =
                    new CvcEventRegisteredListener(eventClass, listener, executor);

            synchronized (this.listeners) {
                this.listeners.add(registeredListener);
            }
        }
    }
}
