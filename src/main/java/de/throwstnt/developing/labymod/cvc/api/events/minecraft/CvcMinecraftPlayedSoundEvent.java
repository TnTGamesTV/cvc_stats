package de.throwstnt.developing.labymod.cvc.api.events.minecraft;

/**
 * Gets triggered when a sound packet is played
 */
public class CvcMinecraftPlayedSoundEvent extends CvcMinecraftEvent {

    private String name;
    private int x;
    private int y;
    private int z;
    private float volume;
    private float pitch;

    public CvcMinecraftPlayedSoundEvent(String name, int x, int y, int z, float volume,
            float pitch) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
