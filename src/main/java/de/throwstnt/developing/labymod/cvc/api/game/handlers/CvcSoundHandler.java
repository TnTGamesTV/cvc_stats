package de.throwstnt.developing.labymod.cvc.api.game.handlers;

import java.util.Timer;
import java.util.TimerTask;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcSwitchingSidesEvent;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftPlayedSoundEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcGameManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;
import de.throwstnt.developing.labymod.cvc.api.util.SoundUtil;

public class CvcSoundHandler implements CvcEventListener {

    @CvcEventHandler
    public void onMinecraftPlayedSound(CvcMinecraftPlayedSoundEvent event) {
        switch (event.getName()) {
            case SoundUtil.SOUND_COPS_WON_GAME:
            case SoundUtil.SOUND_CRIMS_WON_GAME:
                new Thread(() -> CvcGameManager.getInstance().stopGame()).start();
                break;

            case SoundUtil.SOUND_COPS_WON_ROUND:
            case SoundUtil.SOUND_CRIMS_WON_ROUND:
                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        new Thread(() -> CvcRoundManager.getInstance().stopRound()).start();
                    }
                }, 7000);
                break;

            case SoundUtil.SOUND_ROUND_STARTED:
                new Thread(() -> CvcRoundManager.getInstance().createRound()).start();
                break;

            case SoundUtil.SOUND_SWAPPING_SIDES:
                new Thread(
                        () -> CvcEventManager.getInstance().fireEvent(new CvcSwitchingSidesEvent()))
                                .start();
                break;
        }


    }
}
