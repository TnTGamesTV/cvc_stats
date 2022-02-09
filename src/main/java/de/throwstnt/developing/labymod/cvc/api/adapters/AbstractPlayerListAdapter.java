package de.throwstnt.developing.labymod.cvc.api.adapters;

import java.util.List;
import java.util.UUID;

/**
 * An adapter for retrieving game profiles from the players currently logged into the server
 */
public abstract class AbstractPlayerListAdapter<GameProfileType> {

    /**
     * A custom holder for a game profile
     */
    public static class Profile {
        private UUID uuid;
        private String name;

        public Profile(UUID uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Returns the name of a player or null
     * 
     * @param uuid the uuid
     * @return the name or null if not online
     */
    public abstract String getName(UUID uuid);

    /**
     * Returns the uuid of a player by his name or null
     * 
     * @param name the name
     * @return the uuid of the player or null
     */
    public abstract UUID getUUID(String name);

    /**
     * Returns all uuids currently on this server
     * 
     * @return all uuids
     */
    public abstract List<UUID> getUuids();

    /**
     * Get the uuid of the current player
     * 
     * @return the uuid of the current player
     */
    public abstract UUID getCurrentUUID();

    /**
     * Returns the name of the current player
     * 
     * @return the name of the current player
     */
    public abstract String getCurrentName();
}
