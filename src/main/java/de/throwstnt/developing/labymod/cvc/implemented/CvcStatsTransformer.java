package de.throwstnt.developing.labymod.cvc.implemented;

import net.labymod.addon.AddonTransformer;
import net.labymod.api.TransformerType;

public class CvcStatsTransformer extends AddonTransformer {

    @Override
    public void registerTransformers() {
        this.registerTransformer(TransformerType.VANILLA, "main.mixin.json");
    }
}
