package de.throwstnt.developing.labymod.cvc.api.data;

import java.util.UUID;

public class UUIDCache extends AbstractCache<String, UUID> {

    public UUIDCache() {
        super(32);
    }
}
